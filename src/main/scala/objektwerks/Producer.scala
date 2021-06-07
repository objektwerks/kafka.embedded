package objektwerks

import akka.actor.{Actor, ActorLogging}

import scala.concurrent.duration._
import scala.language.postfixOps

class Producer(topic: String, kafka: Kafka) extends Actor with ActorLogging {
  implicit val ec = context.system.dispatcher

  context.system.scheduler.scheduleWithFixedDelay(3 seconds, 3 seconds)( sendProducerRecord() )

  def receive: Receive = {
    case SendProducerRecords => log.info(s"*** producer activated to send producer records...")
  }

  def sendProducerRecord(): Runnable = new Runnable() {
    override def run(): Unit = {
      val reading = DeviceReading.newInstance
      val key = reading.id.toString
      val value = DeviceReading.deviceReadingToJson(reading)
      kafka.sendProducerRecord(topic, key, value)
    }
  }
}