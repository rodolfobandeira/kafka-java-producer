# kafka-java-producer
Kafka Java Producer Studies

---

Getting Kafka up and running in Ubuntu 20

```bash
# Downloading the binary files (Check kafka.org) for the most recent links.
mkdir ~/kafka
cd ~/kafka
wget https://www.apache.org/dyn/closer.cgi?path=/kafka/2.8.0/kafka_2.13-2.8.0.tgz
tar -xzvf kafka_2.13-2.8.0.tgz
cd kafka_2.12-2.8.0/


# Start Zookeeper Server
bin/zookeeper-server-start.sh config/zookeeper.properties


# Starting Kafka Server
bin/kafka-server-start.sh config/server.properties 
```

---

List all Kafka topics:

```bash
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

---

What is inside a specific Kafka topic?

```bash
# Consuming in realtime
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic NEW_SCORE

# Listing all items from the beginning
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic NEW_SCORE --from-beginning
```
