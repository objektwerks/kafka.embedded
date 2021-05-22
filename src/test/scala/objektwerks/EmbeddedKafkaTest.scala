package objektwerks

import io.github.embeddedkafka._

import java.util.Collections
import java.time.Duration._

import org.scalatest.concurrent.Eventually._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterAll
import org.scalatest.compatible.Assertion
import org.slf4j.LoggerFactory

import org.apache.kafka.common.serialization._
import org.apache.kafka.clients.producer.ProducerRecord

import scala.concurrent.duration._
import scala.jdk.CollectionConverters._

class EmbeddedKafkaTest extends AnyFunSuite with BeforeAndAfterAll with Matchers with EmbeddedKafka {
  val logger = LoggerFactory.getLogger(getClass)
  implicit val config = EmbeddedKafkaConfig.defaultConfig
  val kafka = EmbeddedKafka.start()
  logger.info("*** embedded kafka started")

  override def afterAll(): Unit = {
    logger.info("*** embedded kafka stopping ...")
    kafka.stop(false)
    logger.info("*** embedded kafka stopped")
  }

  test("producer > consumer") {
    implicit val serializer = new StringSerializer()
    implicit val deserializer = new StringDeserializer()
    val key = "key"
    val value = "value"
    val topic = "test"

    withProducer[String, String, Unit] { producer =>
      producer.send( new ProducerRecord[String, String](topic, key, value) )
      ()
    }

    withConsumer[String, String, Assertion] { consumer =>
      consumer.subscribe( Collections.singletonList(topic) )
      eventually {
        val records = consumer.poll( ofMillis(9.seconds.toMillis) ).asScala
        records.size shouldBe 1
        records.head.key shouldBe key
        records.head.value shouldBe value
      }
    }
  }
}