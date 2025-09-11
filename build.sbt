name := "kafka.embedded"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.16"
libraryDependencies ++= {
  val akkaVersion = "2.6.21" // Don't upgrade due to BUSL 1.1!
  val quillVersion = "3.10.0" // Don't upgrade, 3.11 contains dev.zio!
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "io.getquill" %% "quill-sql" % quillVersion,
    "io.getquill" %% "quill-jdbc" % quillVersion,
    "com.h2database" % "h2" % "2.3.232",
    "com.lihaoyi" %% "upickle" % "4.3.1",
    "com.typesafe" % "config" % "1.4.3",    
    "io.github.embeddedkafka" %% "embedded-kafka" % "4.0.1.1",
    "ch.qos.logback" % "logback-classic" % "1.5.18",
    "org.scalatest" %% "scalatest" % "3.2.19"
  )
}
