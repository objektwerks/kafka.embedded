package objektwerks

import akka.actor.{Actor, ActorLogging}

import scala.concurrent.duration._
import scala.language.postfixOps

sealed trait Message extends Product with Serializable
final case object Publish
final case object Subscribe

class Publisher(topic: String) extends Actor with ActorLogging {
  implicit val ec = context.system.dispatcher
  
  context.system.scheduler.scheduleWithFixedDelay(3 seconds, 3 seconds)( sendProducerRecord() )

  def receive: Receive = {
    case Publish => log.info(s"*** publisher activated ...")
  }

  def sendProducerRecord(): Runnable = new Runnable() {
    override def run(): Unit = {
      val reading = DeviceReading.newInstance
      val key = reading.id.toString()
      val value = DeviceReading.deviceReadingToJson(reading)
      Kafka.sendProducerRecord(topic, key, value)
    }
  }
}

class Subscriber(topic: String, store: Store) extends Actor with ActorLogging {
  implicit val ec = context.system.dispatcher
  
  context.system.scheduler.scheduleWithFixedDelay(3 seconds, 3 seconds)( pollConsumerRecords() )

  def receive: Receive = {
    case Subscribe => log.info(s"*** subscriber activated ...")
  }

def pollConsumerRecords(): Runnable = new Runnable() {
    override def run(): Unit = {
      val consumerRecords = Kafka.pollConsumerRecords(topic)
      consumerRecords.foreach { record =>
        store.addDeviceReading( DeviceReading.jsonToDeviceReading( record.value() ) )
      }
      ()
    }
  }
}