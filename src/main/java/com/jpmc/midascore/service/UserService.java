package com.jpmc.midascore.service;

import com.jpmc.midascore.component.UserDatabaseConduit;
import com.jpmc.midascore.entity.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserDatabaseConduit databaseConduit;
    
    @Transactional
    public UserRecord saveUser(UserRecord user) {
        logger.info("Saving user: {}", user);
        databaseConduit.save(user);
        logger.info("User saved successfully: {}", user);
        return user;
    }
    
    @Transactional
    public UserRecord createUser(String name, float balance) {
        logger.info("Creating new user: name={}, balance={}", name, balance);
        UserRecord user = new UserRecord(name, balance);
        return saveUser(user);
    }
    
    @Transactional(readOnly = true)
    public UserRecord findById(Long id) {
        return databaseConduit.findById(id);
    }
    
    @Transactional(readOnly = true)
    public UserRecord findByName(String name) {
        return databaseConduit.findByName(name);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return databaseConduit.existsByName(name);
    }
    
    @Transactional(readOnly = true)
    public Iterable<UserRecord> findAll() {
        return databaseConduit.findAll();
    }
    
    @Transactional
    public void updateUserBalance(String name, float newBalance) {
        databaseConduit.updateUserBalance(name, newBalance);
    }
}