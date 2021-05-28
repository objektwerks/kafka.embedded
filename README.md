Kafka Embedded
--------------
>EmbeddedKafka feature tests, to include a simulation using Akka, Kafka, uPickle, Quill and H2.

Simulation
----------
>App that simulates the following model and process:
* **Model:** Device 1 ---> * DeviceReading
1. **Bootstrap:** Simulation --- store device ---> Store
2. **Source:** Actor Publisher --- send device reading ---> Kafka Topic
3. **Flow:** Actor Subscriber <--- poll device readings ---> Kafka Topic
4. **Sink:** Actor Subscriber --- store device readings ---> Store
5. **Report:** Simulation <--- build device report ---> Store

EmbeddedKafka
-------------
>See: https://github.com/embeddedkafka/embedded-kafka

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