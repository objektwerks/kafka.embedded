package objektwerks

import akka.actor.{Actor, ActorLogging}

sealed trait Message extends Product with Serializable
final case object Publish
final case object Subscribe

class Publisher extends Actor with ActorLogging {
  def receive: Receive = {
    case Publish => log.info(s"*** publisher activated ...")
  }
}

class Subscriber(store: Store) extends Actor with ActorLogging {
  println(store)
  def receive: Receive = {
    case Subscribe => log.info(s"*** subscriber activated ...")
  }
}