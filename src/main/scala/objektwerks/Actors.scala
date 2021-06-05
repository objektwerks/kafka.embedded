package objektwerks

import akka.actor.{Actor, ActorLogging}

import scala.concurrent.duration._
import scala.language.postfixOps

sealed trait Message extends Product with Serializable
case object SendProducerRecords
case object PollConsumerRecords

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

class Consumer(topic: String, kafka: Kafka, store: Store) extends Actor with ActorLogging {
  implicit val ec = context.system.dispatcher

  context.system.scheduler.scheduleWithFixedDelay(6 seconds, 6 seconds)( pollConsumerRecords() )

  def receive: Receive = {
    case PollConsumerRecords => log.info(s"*** consumer activated to poll consumer records...")
  }

def pollConsumerRecords(): Runnable = new Runnable() {
    override def run(): Unit = {
      val consumerRecords = kafka.pollConsumerRecords(topic)
      consumerRecords.foreach { record =>
        store.addDeviceReading( DeviceReading.jsonToDeviceReading( record.value() ) )
      }
      ()
    }
  }
}