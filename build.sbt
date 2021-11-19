name := "kafka.embedded"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.13.7"
libraryDependencies ++= {
  val akkaVersion = "2.6.17"
  val quillVersion = "3.11.0"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "io.getquill" %% "quill-sql" % quillVersion,
    "io.getquill" %% "quill-jdbc" % quillVersion,
    "com.h2database" % "h2" % "1.4.200",
    "com.lihaoyi" %% "upickle" % "1.4.2",
    "com.typesafe" % "config" % "1.4.1",    
    "io.github.embeddedkafka" %% "embedded-kafka" % "3.0.0",
    "ch.qos.logback" % "logback-classic" % "1.2.6",
    "org.scalatest" %% "scalatest" % "3.2.10"
  )
}
