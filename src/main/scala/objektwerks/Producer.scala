package objektwerks

import akka.actor.Actor

class Producer(topic: String, kafka: Kafka) extends Actor {
  def receive: Receive = {
    case SendDeviceReading => sendDeviceReading()
  }

  def sendDeviceReading(): Unit = {
    val reading = DeviceReading.newInstance
    val key = reading.id.toString
    val value = DeviceReading.deviceReadingToJson(reading)
    kafka.sendProducerRecord(topic, key, value)
  }
}