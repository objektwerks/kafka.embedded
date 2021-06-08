package objektwerks

sealed trait Message extends Product with Serializable
case object Start extends Message
case object Stop extends Message
case object SendDeviceReading extends Message
case object PollDeviceReadings extends Message