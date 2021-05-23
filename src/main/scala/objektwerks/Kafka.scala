package objektwerks

import io.github.embeddedkafka._

import java.time.Duration

import java.util.Properties

import org.slf4j.LoggerFactory
import org.apache.kafka.common.serialization._
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.KafkaProducer

import scala.jdk.CollectionConverters._

object Kafka {
  def apply(): Kafka = new Kafka()
}

class Kafka extends EmbeddedKafka {
  val logger = LoggerFactory.getLogger(getClass)
  implicit val serializer = new StringSerializer()
  implicit val deserializer = new StringDeserializer()
  implicit val config = EmbeddedKafkaConfig.defaultConfig

  logger.info("*** embedded kafka starting ...")
  val kafka = EmbeddedKafka.start()
  logger.info("*** embedded kafka started")

  def stop(): Unit = {
    logger.info("*** embedded kafka stopping ...")
    kafka.stop(false)
    logger.info("*** embedded kafka stopped")
    ()
  }

  def createTopic(name: String): Unit = createCustomTopic(name).toOption.get

  def product(topic: String, key: String, value: String): Unit = {
    val producer = new KafkaProducer[String, String](new Properties())
    val record = new ProducerRecord[String, String](topic, key, value)
    producer.send(record)
    ()
  }
 
  def consume(topic: String): List[ConsumerRecord[String, String]] = {
    val consumer = new KafkaConsumer[String, String](new Properties())
    consumer.subscribe( List(topic).asJava )
    consumer.poll( Duration.ofMillis(3000L) ).asScala.toList
  }
}