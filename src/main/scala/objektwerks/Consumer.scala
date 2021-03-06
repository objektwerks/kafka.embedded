package objektwerks

import akka.actor.Actor

class Consumer(topic: String, kafka: Kafka, store: Store) extends Actor {
  def receive: Receive = {
    case PollDeviceReadings => pollDeviceReadings()
  }

  def pollDeviceReadings(): Unit =
    kafka.pollConsumerRecords(topic).foreach { record =>
      store.addDeviceReading( DeviceReading.jsonToDeviceReading( record.value() ) )
    }
}