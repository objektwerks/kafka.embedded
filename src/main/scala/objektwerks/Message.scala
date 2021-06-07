package objektwerks

sealed trait Message extends Product with Serializable
case object SendProducerRecords
case object PollConsumerRecords