name := "kafka.embedded"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.11"
libraryDependencies ++= {
  val akkaVersion = "2.6.20" // Don't upgrade due to BUSL 1.1!
  val quillVersion = "3.10.0" // 3.11 contains dev.zio
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "io.getquill" %% "quill-sql" % quillVersion,
    "io.getquill" %% "quill-jdbc" % quillVersion,
    "com.h2database" % "h2" % "2.2.222",
    "com.lihaoyi" %% "upickle" % "3.1.2",
    "com.typesafe" % "config" % "1.4.2",    
    "io.github.embeddedkafka" %% "embedded-kafka" % "3.5.1",
    "ch.qos.logback" % "logback-classic" % "1.4.11",
    "org.scalatest" %% "scalatest" % "3.2.16"
  )
}
