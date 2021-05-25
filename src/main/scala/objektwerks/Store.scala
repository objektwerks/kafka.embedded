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

  def listDevices(): Seq[Device] = run( query[Device] )

  def listDeviceReadings(): Seq[DeviceReading] = run( query[DeviceReading] )

  def buildReport: Array[String] = {
    val devices = listDevices()
    val readings = listDeviceReadings()
    var valueAvg = readings.map(reading => reading.value).fold(0.0)(_ + _) / readings.size
    valueAvg = new DecimalFormat("#.##").format(valueAvg).toDouble
    val builder = mutable.ArrayBuilder.make[String]
    builder += s"*** Number of devices: ${devices.size}"
    builder += s"*** Number of device readings: ${readings.size}"
    builder += s"*** Average value(temp) of device readings: $valueAvg"
    builder.result()
  }
}