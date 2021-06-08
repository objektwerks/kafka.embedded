Kafka Embedded
--------------
>EmbeddedKafka feature tests, to include a simulation using Akka, Kafka, uPickle, Quill and H2.

Simulation
----------
>App simulates this model:
* **Model:** Device 1 ---> * DeviceReading
>and process:
1. Simulation --- start ---> Simulator
2. Simulator --- store default device ---> Store
3. Simulator --- * send device reading ---> Producer
4. Producer --- send producer record ---> Kafka
5. Simulator --- * poll device readings ---> Consumer
6. Consumer --- poll consumer records ---> Kafka   
7. Consumer --- store device readings ---> Store   
8. Simulation --- stop ---> Stimulator --- build device report ---> Store

EmbeddedKafka
-------------
>See: https://github.com/embeddedkafka/embedded-kafka

Test
----
1. sbt clean test

Run
---
>Ideally, run the simulation for 1 minute.
1. sbt run

Logs
----
>Search logs using search term *** to view custom simulation and test logger statements.
1. ./target/simulation.log
2. ./targe/test.log