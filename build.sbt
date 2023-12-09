name := "kafka.embedded"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.12"
libraryDependencies ++= {
  val akkaVersion = "2.6.21" // Don't upgrade due to BUSL 1.1!
  val quillVersion = "3.10.0" // Don't upgrade since 3.11 contains dev.zio!
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "io.getquill" %% "quill-sql" % quillVersion,
    "io.getquill" %% "quill-jdbc" % quillVersion,
    "com.h2database" % "h2" % "2.2.224",
    "com.lihaoyi" %% "upickle" % "3.1.3",
    "com.typesafe" % "config" % "1.4.2",    
    "io.github.embeddedkafka" %% "embedded-kafka" % "3.6.0",
    "ch.qos.logback" % "logback-classic" % "1.4.14",
    "org.scalatest" %% "scalatest" % "3.2.17
  )
}
