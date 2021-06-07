package objektwerks

import akka.actor.{Actor, ActorLogging}

import scala.concurrent.duration._
import scala.language.postfixOps

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