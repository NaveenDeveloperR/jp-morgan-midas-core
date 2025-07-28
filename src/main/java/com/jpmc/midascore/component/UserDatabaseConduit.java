package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.repository.UserRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDatabaseConduit {
    private final UserRepository userRepository;

    public UserDatabaseConduit(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    @Transactional(readOnly = true)
    public UserRecord findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public UserRecord findByName(String name) {
        return userRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return userRepository.existsByName(name);
    }

    @Transactional(readOnly = true)
    public Iterable<UserRecord> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateUserBalance(String name, float newBalance) {
        UserRecord user = userRepository.findByName(name);
        if (user != null) {
            user.setBalance(newBalance);
            userRepository.save(user);
        }
    }

}
