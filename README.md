Kafka Embedded
--------------
>EmbeddedKafka feature tests, to include a simulation using Akka, Kafka, uPickle, Quill and H2.

Docs
----
>See: https://github.com/embeddedkafka/embedded-kafka

Simulation
----------
>App that simulates the following source-flow-sink-report process:
1. **Source:** Actor Publisher --- produces message ---> Kafka Topic
2. **Flow:** Actor Subscriber <--- consumes messages ---> Kafka Topic
3. **Sink:** Actor Subscriber --- store messages ---> Store
4. **Report:** Simulation <--- build report ---> Store

Test
----
1. sbt clean test

Run
---
1. sbt run

Logs
----
>Search logs using search term *** to view custom simulation and test logger statements.
1. ./target/simulation.log
2. ./targe/test.log
