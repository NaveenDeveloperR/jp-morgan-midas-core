package com.jpmc.midascore.kafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.service.TransactionService;

@Service
public class KafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    
    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "my-group")
    public void consumeMessage(Transaction txn) {
        logger.info("Consumed message from Kafka: {}", txn);
        
        try {
            // Process the transaction through the service
            transactionService.processTransaction(txn);
        } catch (Exception e) {
            logger.error("Failed to process transaction: {}", txn, e);
        }
    }
}
