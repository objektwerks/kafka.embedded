package objektwerks

import akka.actor.{ActorSystem, Props}

import com.typesafe.config._

import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.StdIn
import scala.language.postfixOps

object Simulation {
  def main(args: Array[String]): Unit = {
    val logger = LoggerFactory.getLogger(getClass)
    val conf = ConfigFactory.load("simulation.conf")

    val topic = conf.getString("kafka.topic")
    val kafka = Kafka()

    val store = Store(conf)
    store.addDevice( Device.defaultDevice )

    val system = ActorSystem.create("simulation", conf)
    val publisher = system.actorOf(Props(classOf[Publisher], topic, kafka), name = "publisher")
    val subscriber = system.actorOf(Props(classOf[Subscriber], topic, kafka, store), name = "subscriber")
    publisher ! Publish
    subscriber ! Subscribe

    println(s"*** Press return to shutdown simulation.")
    StdIn.readLine()
    println(s"*** Generating report and shutting down simulation ...")

    logger.info(s"*** Number of devices: ${store.listDevices().size}")
    logger.info(s"*** Number of device readings: ${store.listDeviceReadings().size}")

    system.terminate()
    Await.result(system.whenTerminated, 30 seconds)
    logger.info("*** akka system terminated")
    kafka.stop()
    logger.info("*** embedded kafka stopped")
    ()
  }
}