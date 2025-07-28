package com.jpmc.midascore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jpmc.midascore.kafka.KafkaProducer;

@RestController
@RequestMapping("/api/v1/kafka")
public class MessageController {

    private final KafkaProducer kafkaProducer;

    public MessageController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    // Example: http://localhost:8080/api/v1/kafka/publish?message=hello
    @GetMapping("/publish")
    public ResponseEntity<String> publish(@RequestParam("message") String message) {
        try {
            kafkaProducer.send(message);
            return ResponseEntity.ok("✅ Message sent to Kafka topic successfully");
        } catch (Exception e) {
            // Log the error (optional - add logging dependency)
            e.printStackTrace();

            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("❌ Failed to send message to Kafka: " + e.getMessage());
        }
    }
}
