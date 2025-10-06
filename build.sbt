name := "kafka.embedded"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.17"
libraryDependencies ++= {
  val akkaVersion = "2.6.21" // Don't upgrade due to BUSL 1.1!
  val quillVersion = "3.10.0" // Don't upgrade, 3.11 contains dev.zio!
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "io.getquill" %% "quill-sql" % quillVersion,
    "io.getquill" %% "quill-jdbc" % quillVersion,
    "com.h2database" % "h2" % "2.4.240",
    "com.lihaoyi" %% "upickle" % "4.3.2",
    "com.typesafe" % "config" % "1.4.3",    
    "io.github.embeddedkafka" %% "embedded-kafka" % "4.1.0",
    "ch.qos.logback" % "logback-classic" % "1.5.19",
    "org.scalatest" %% "scalatest" % "3.2.19"
  )
}
