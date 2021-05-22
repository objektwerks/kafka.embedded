package objektwerks

import io.github.embeddedkafka._

import java.util.Collections
import java.time.Duration._

import org.scalatest.concurrent.Eventually._
import org.scalatest.compatible.Assertion

import org.apache.kafka.common.serialization._
import org.apache.kafka.clients.producer.ProducerRecord

import scala.concurrent.duration._
import scala.jdk.CollectionConverters._

object EmbeddedKafkaApp extends EmbeddedKafka {
  def main(args: Array[String]): Unit = {
    implicit val config = EmbeddedKafkaConfig.defaultConfig
    val kafka = EmbeddedKafka.start()
    println("embedded kafka started")

    implicit val serializer = new StringSerializer()
    implicit val deserializer = new StringDeserializer()

    val key = "key"
    val value = "value"
    val topic = "app"

    createCustomTopic(topic).map { _ =>
      withProducer[String, String, Unit] { producer =>
        producer.send( new ProducerRecord[String, String](topic, key, value) )
        ()
      }

      withConsumer[String, String, Assertion] { consumer =>
        consumer.subscribe( Collections.singletonList(topic) )
        eventually {
          val records = consumer.poll( ofMillis(9.seconds.toMillis) ).asScala
          require( records.size == 1, "Records size != 1" )
          require( records.head.key == key, "Key invalid!" )
          require( records.head.value == value, "Value invalid!" )
          new Assertion{}
        }
      }
      println("test passed")
    }.recover { case error: Throwable => println(s"test failed: $error") }

    println("embedded kafka stopping ...")
    kafka.stop(false)
    println("embedded kafka stopped")

    ()
  }
}