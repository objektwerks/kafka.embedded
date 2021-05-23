package objektwerks

import com.typesafe.config.Config

import io.getquill._

import scala.annotation.nowarn

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
}