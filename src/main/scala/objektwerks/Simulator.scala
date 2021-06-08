package objektwerks

import akka.actor.{Actor, ActorLogging, Props}

import java.text.DecimalFormat

import scala.collection.mutable
import scala.concurrent.duration._
import scala.language.postfixOps

class Simulator(topic: String, kafka: Kafka, store: Store) extends Actor with ActorLogging {
  implicit val ec = context.system.dispatcher

  val producer = context.actorOf(Props(classOf[Producer], topic, kafka), name = "producer")
  val consumer = context.actorOf(Props(classOf[Consumer], topic, kafka, store), name = "consumer")

  context.system.scheduler.scheduleWithFixedDelay(3 seconds, 3 seconds)( sendProducerRecord() )
  context.system.scheduler.scheduleWithFixedDelay(6 seconds, 6 seconds)( pollConsumerRecords() )

  def receive: Receive = {
    case Start =>
      store.addDevice( Device.defaultDevice )
      log.info(s"*** simulator started")
    case Stop =>
      context.stop(producer)
      context.stop(consumer)
      buildReport()
      context.stop(self)
      log.info(s"*** simulator stopped")
  }

  def sendProducerRecord(): Runnable = new Runnable() {
    override def run(): Unit = producer ! SendProducerRecords
  }

  def pollConsumerRecords(): Runnable = new Runnable() {
    override def run(): Unit = consumer ! PollConsumerRecords
  }

  def buildReport(): Unit = {
    val devices = store.listDevices
    val builder = mutable.ArrayBuilder.make[String]
    builder += s"*** Device Report [${devices.size}]"
    for( (deviceId, readings) <- store.listDeviceReadings.groupBy(_.deviceId) ) {
      devices.find(device => device.id == deviceId).foreach {
        val formatter = new DecimalFormat("#.##")
        val valueAvg = formatter.format( readings.map(reading => reading.value).fold(0.0)(_ + _) / readings.size )
        device => builder += s"*** Device: ${device.name} Readings: ${readings.size} Average: $valueAvg"
      }
    }
    val report = builder.result()
    report.foreach(line => log.info(line))
  }
}