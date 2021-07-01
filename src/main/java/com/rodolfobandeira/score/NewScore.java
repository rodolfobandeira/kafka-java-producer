package com.rodolfobandeira.score;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewScore {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Exploring some of the "fancy" Java features: "var"
        var producer = new KafkaProducer<String, String>(properties());
        var key = "likeAction";
        var value = String.valueOf((int)(Math.random()*(( 100 - 1)+1))+1);
        var record = new ProducerRecord<String, String>("NEW_SCORE", key, value);

        // send() is Async. If we want to wait the send to finish, we need to call .get();
        producer.send(record, (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }

            System.out.println("Successfully sent to Topic: " + data.topic() + ", Partition: " + data.partition() + ", Offset: " + data.offset() + ", Timestamp: " + data.timestamp());

        }).get();
    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
