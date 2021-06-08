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

    val system = ActorSystem.create("simulation", conf)
    val simulator = system.actorOf(Props(classOf[Simulator], topic, kafka, Store(conf)), name = "simulator")
    simulator ! Start

    println(s"*** Press return to shutdown simulation.")
    StdIn.readLine()
    println(s"*** Generating report and shutting down simulation...")

    simulator ! Stop
    system.terminate()
    Await.result(system.whenTerminated, 30 seconds)
    logger.info("*** akka system terminated")

    kafka.stop()
    ()
  }
}