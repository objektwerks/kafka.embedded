package objektwerks

import com.typesafe.config.Config

import io.getquill._

import java.text.DecimalFormat

import scala.annotation.nowarn
import scala.collection.mutable

object Store {
  def apply(conf: Config): Store = new Store(conf)
}

class Store(conf: Config) {  
  implicit val ctx = new H2JdbcContext(SnakeCase, conf.getConfig("quill.ctx"))
  import ctx._

  @nowarn def addDevice(device: Device): Unit = run( query[Device].insert(lift(device)) )

  def addDeviceReading(reading: DeviceReading): Int = run( query[DeviceReading].insert(lift(reading)).returningGenerated(_.id) )

  def listDevices: Seq[Device] = run( query[Device] )

  def listDeviceReadings: Seq[DeviceReading] = run( query[DeviceReading] )

  def buildReport: Array[String] = {
    val devices = listDevices
    val builder = mutable.ArrayBuilder.make[String]
    builder += s"*** Device Report [${devices.size}]"
    for( (deviceId, readings) <- listDeviceReadings.groupBy(_.deviceId) ) {
      devices.find(device => device.id == deviceId).foreach {
        val formatter = new DecimalFormat("#.##")
        val valueAvg = formatter.format( readings.map(reading => reading.value).fold(0.0)(_ + _) / readings.size )
        device => builder += s"*** Device: ${device.name} Readings: ${readings.size} Average: $valueAvg"
      }
    }
    builder.result()
  }
}