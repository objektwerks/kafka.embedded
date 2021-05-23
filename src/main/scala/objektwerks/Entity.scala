package objektwerks

import java.time.Instant
import java.util.UUID

import upickle.default._

sealed trait Entity extends Product with Serializable

final case class Device(id: String,
                        name: String, 
                        created: String) extends Entity

object Device {
  val defaultDevice = Device(id = UUID.randomUUID.toString.toLowerCase,
                             name = "thermostat-1", 
                             created = Instant.now.toString)

  implicit val readWriter: ReadWriter[Device] = macroRW

  def jsonToDevice(json: String): Device = read[Device](json)

  def deviceToJson(device: Device): String = write(device)
}

final case class DeviceReading(id: Int = 0, 
                               deviceId: String, 
                               value: Double, 
                               unit: String, 
                               observed: String, 
                               version: Int) extends Entity

object DeviceReading {
  def newInstance: DeviceReading = DeviceReading(deviceId = Device.defaultDevice.id,
                                                 value = 74.5,
                                                 unit = "Fahrenheit",
                                                 observed = Instant.now.toString,
                                                 version = 1)  

  implicit val readWriter: ReadWriter[DeviceReading] = macroRW

  def jsonToDeviceReading(json: String): DeviceReading = read[DeviceReading](json)

  def deviceReadingToJson(reading: DeviceReading): String = write(reading)
}