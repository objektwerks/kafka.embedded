package objektwerks

import akka.actor.ActorSystem
import com.typesafe.config._
import io.github.embeddedkafka._
import io.getquill._
import org.slf4j.LoggerFactory
import scala.io.StdIn
import scala.annotation.nowarn

case class Device(id: String, name: String, created: Long)
case class DeviceReading(id: Int = 0, deviceId: String, currentValue: Float, unit: String, datetime: Long, version: Float)

class Store(conf: Config) {  
  implicit val ctx = new H2JdbcContext(SnakeCase, conf.getConfig("quill.ctx"))
  import ctx._

  @nowarn def addDevice(device: Device): Unit = run( query[Device].insert(lift(device)) )

  def addDeviceReading(reading: DeviceReading): Int = run( query[DeviceReading].insert(lift(reading)).returningGenerated(_.id) )

  def listDevices(): Seq[Device] = run( query[Device] )

  def listDeviceReadings(): Seq[DeviceReading] = run( query[DeviceReading] )
}

object DeviceSimulation {
  val logger = LoggerFactory.getLogger(getClass)

  implicit val config = EmbeddedKafkaConfig.defaultConfig
  val kafka = EmbeddedKafka.start()
  logger.info("*** embedded kafka started")

  val conf = ConfigFactory.load("simulation.conf")
  implicit val system = ActorSystem.create("simulation", conf)
  implicit val dispatcher = system.dispatcher
  logger.info("*** akka system started")

  val store = new Store(conf)

  println(s"*** Press return to shutdown simulation.")
  StdIn.readLine()

  system.terminate()
  logger.info("*** akka system terminated")
  kafka.stop(false)
  logger.info("*** embedded kafka stopped")
  ()
}