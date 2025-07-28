// package com.jpmc.midascore.kafka;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.stereotype.Service;

// @Service
// // This class will handle the logic for producing messages to Kafka topics
// public class KafkaProducer {
//     private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);    
    
//     private KafkaTemplate<String, String> kafkaTemplate;

//     public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
//         this.kafkaTemplate = kafkaTemplate;
//     }



//     public void sendMessage(String message) {
//         // Logic to send the message to the Kafka topic
//         logger.info(String.format("Sending message: %s", message));
//         kafkaTemplate.send("med-topic", message);
        
//     }  
// }
