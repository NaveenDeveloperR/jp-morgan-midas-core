package com.jpmc.midascore.component;

import com.jpmc.midascore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    
    @Autowired
    private UserService userService;
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("Initializing user data...");
        populateUsers();
        logger.info("User data initialization completed.");
    }
    
    public void populateUsers() {
        try {
            ClassPathResource resource = new ClassPathResource("test_data/lkjhgfdsa.hjkl");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            
            List<String> userLines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                userLines.add(line);
            }
            reader.close();
            
            logger.info("Found {} user records to populate", userLines.size());
            
            for (String userLine : userLines) {
                String[] userData = userLine.split(", ");
                if (userData.length == 2) {
                    String name = userData[0].trim();
                    float balance = Float.parseFloat(userData[1].trim());
                    
                    // Check if user already exists
                    if (!userService.existsByName(name)) {
                        userService.createUser(name, balance);
                        logger.info("Created user: {} with balance: {}", name, balance);
                    } else {
                        logger.info("User {} already exists, skipping", name);
                    }
                } else {
                    logger.warn("Invalid user data format: {}", userLine);
                }
            }
            
            // Log all users for verification
            logger.info("All users in database:");
            userService.findAll().forEach(user -> 
                logger.info("User: ID={}, Name={}, Balance={}", user.getId(), user.getName(), user.getBalance())
            );
            
        } catch (Exception e) {
            logger.error("Error populating users", e);
        }
    }
}