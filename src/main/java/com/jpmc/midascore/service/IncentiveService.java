package com.jpmc.midascore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;

@Service
public class IncentiveService {
    private static final Logger logger = LoggerFactory.getLogger(IncentiveService.class);
    private final RestTemplate restTemplate;
    private final String INCENTIVE_API_URL = "http://localhost:8081/incentive"; // Adjust URL as needed
    
    public IncentiveService(RestTemplateBuilder Builder) {
        this.restTemplate = Builder.build();
    }
    public Incentive getIncentive(Transaction transaction) {
        
        try {
            logger.info("Calling Incentive API for transaction: {}", transaction);
            
            // First, let's get the raw response as String to debug the format
            String rawResponse = restTemplate.postForObject(INCENTIVE_API_URL, transaction, String.class);
            logger.info("Raw response from Incentive API: '{}'", rawResponse);
            
            // Now try to get it as Incentive object
            Incentive incentive = restTemplate.postForObject(INCENTIVE_API_URL, transaction, Incentive.class);
            logger.info("Parsed incentive object: {}", incentive);
            logger.info("Incentive amount: {}", incentive != null ? incentive.getAmount() : "null");
            
            return incentive;
        } catch (Exception e) {
            logger.error("Error calling Incentive API for transaction: {}", transaction, e);
            return new Incentive(0.0f); // Handle error appropriately
        }
    }
    
    
}
