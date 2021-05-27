Kafka Embedded
--------------
>EmbeddedKafka feature tests, to include a simulation using Akka, Kafka, uPickle, Quill and H2.

Docs
----
>See: https://github.com/embeddedkafka/embedded-kafka

Simulation
----------
>App that simulates the following model and process:
1. **Model:** Device 1 ---> * DeviceReading
2. **Bootstrap:** Simulation --- store device ---> Store
3. **Source:** Actor Publisher --- send device reading ---> Kafka Topic
4. **Flow:** Actor Subscriber <--- poll device readings ---> Kafka Topic
5. **Sink:** Actor Subscriber --- store device readings ---> Store
6. **Report:** Simulation <--- build device report ---> Store

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
