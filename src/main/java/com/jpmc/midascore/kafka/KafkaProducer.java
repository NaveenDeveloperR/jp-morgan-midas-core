package com.jpmc.midascore.kafka;

import com.jpmc.midascore.foundation.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final String topic;
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public KafkaProducer(@Value("${general.kafka-topic}") String topic, KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String transactionLine) {
        String[] transactionData = transactionLine.split(", ");
        Transaction txn = new Transaction(
            Long.parseLong(transactionData[0]),
            Long.parseLong(transactionData[1]),
            Float.parseFloat(transactionData[2])
        );

        kafkaTemplate.send(topic, txn);
        logger.info("🚀 Sent message to Kafka topic [{}]: {}", topic, txn);
    }
}
