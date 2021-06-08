package objektwerks

sealed trait Message extends Product with Serializable
case object Start extends Message
case object Stop extends Message
case object SendProducerRecords extends Message
case object PollConsumerRecords extends Message