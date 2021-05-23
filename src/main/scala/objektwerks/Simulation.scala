package objektwerks

import akka.actor.{ActorSystem, Props}
import com.typesafe.config._
import org.slf4j.LoggerFactory
import scala.io.StdIn

object Simulation {
  def main(args: Array[String]): Unit = {
    val logger = LoggerFactory.getLogger(getClass)
    val conf = ConfigFactory.load("simulation.conf")

    val kafka = Kafka()

    val system = ActorSystem.create("simulation", conf)
    val publisher = system.actorOf(Props[Publisher](), name = "publisher")
    val subscriber = system.actorOf(Props(classOf[Subscriber], Store(conf)), name = "subscriber")
    publisher ! Publish
    subscriber ! Subscribe

    println(s"*** Press return to shutdown simulation.")
    StdIn.readLine()
    println(s"*** Shutting down simulation ...")

    system.terminate()
    logger.info("*** akka system terminated")
    kafka.stop()
    logger.info("*** embedded kafka stopped")
    ()
  }
}