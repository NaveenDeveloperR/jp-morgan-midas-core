package com.jpmc.midascore;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import com.jpmc.midascore.component.DataInitializer;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.kafka.KafkaProducer;
import com.jpmc.midascore.service.UserService;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class TaskFourTests {
    static final Logger logger = LoggerFactory.getLogger(TaskFourTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    UserService userService;

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private FileLoader fileLoader;

    @Test
    void task_four_verifier() throws InterruptedException {
        dataInitializer.populateUsers();
        String[] transactionLines = fileLoader.loadStrings("/test_data/alskdjfh.fhdjsk");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }
        // Thread.sleep(2000);

        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("----------------------------------------------------------");
        logger.info("use your debugger to find out what wilbur's balance is after all transactions are processed");
        logger.info("kill this test once you find the answer");
        // Find wilbur's user record (wilbur is user ID 9 from the user data)
        UserRecord wilbur = userService.findByName("wilbur");
        if (wilbur != null) {
            logger.info("Wilbur's balance: {}", wilbur.getBalance());
        } else {
            logger.warn("Wilbur's user record not found.");
        }
        // while (true) {
        //     Thread.sleep(20000);
        //     logger.info("...");
        // }
    }
}
