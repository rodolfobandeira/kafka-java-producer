package com.rodolfobandeira.score;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewScore {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Exploring some of the "fancy" Java features: "var"
        // This is where we create the KaftkaProducer entry point
        var producer = new KafkaProducer<String, String>(properties());


        // This callback result of a refactor after introducing two different
        // producer.send(). Since the callback was the same, we can just name it as it is
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("Successfully sent to Topic: " + data.topic() + ", Partition: " + data.partition() + ", Offset: " + data.offset() + ", Timestamp: " + data.timestamp());
        };


        // Here we define key and value to be passed to our new producer and record
        // The topic name is just a unique string identifier "NEW_SCORE"
        // send() is Async. If we want to wait the send to finish, we need to call .get();
        var key = "likeAction";
        var value = String.valueOf((int)(Math.random()*(( 100 - 1)+1))+1);
        var newScoreRecord = new ProducerRecord<String, String>("NEW_SCORE", key, value);
        producer.send(newScoreRecord, callback).get();


        // Lets produce another record
        String notificationMessage = "You received %s points!".formatted(value);
        var newScorePushNotificationRecord = new ProducerRecord<>("NEW_SCORE_PUSH_NOTIFICATION", notificationMessage, notificationMessage);
        producer.send(newScorePushNotificationRecord, callback).get();

    }

    private static Properties properties() {
        var properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
