package com.jpmc.midascore.foundation.repository;

import com.jpmc.midascore.entity.UserRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserRecord, Long> {
    UserRecord findByName(String name);
    boolean existsByName(String name);
}
