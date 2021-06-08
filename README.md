Kafka Embedded
--------------
>EmbeddedKafka feature tests, to include a simulation using Akka, Kafka, uPickle, Quill and H2.

Simulation
----------
>App simulates this model:
* **Model:** Device 1 ---> * DeviceReading
>and process:
1. Simulation --- create ---> Akka System
2. Akka System --- create ---> Simulator
3. Akka System --- start ---> Simulator
4. Simulator --- start ---> Kafka
5. Simulator --- create ---> Store | Producer | Consumer | Schedulers
6. Simulator --- store default device ---> Store
7. Simulator --- * send device reading ---> Producer
8. Producer --- send producer record ---> Kafka
9. Simulator --- * poll device readings ---> Consumer
10. Consumer --- poll consumer records ---> Kafka
11. Consumer --- store device readings ---> Store
12. Akka System --- stop ---> Stimulator
13. Simulator --- stop ---> Producer | Consumer | Kafka
14. Simulator --- build device report ---> Store
15. Akka System <--- terminate

EmbeddedKafka
-------------
>See: https://github.com/embeddedkafka/embedded-kafka

Test
----
1. sbt clean test

Run
---
>Ideally, run the simulation for 1 minute plus.
1. sbt run

Logs
----
>Search logs using search term *** to view custom simulation and test logger statements.
1. ./target/simulation.log
2. ./targe/test.log