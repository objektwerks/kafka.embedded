package objektwerks

import akka.actor.ActorSystem
import com.typesafe.config._
import org.slf4j.LoggerFactory
import scala.io.StdIn

object Simulation {
  def main(args: Array[String]): Unit = {
    val logger = LoggerFactory.getLogger(getClass)
    val conf = ConfigFactory.load("simulation.conf")
    implicit val system = ActorSystem.create("simulation", conf)
    val kafka = Kafka()
    val store = Store(conf)
    println(store)

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