package com.jpmc.midascore.foundation.repository;

import com.jpmc.midascore.entity.TransactionRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionRecord, Long> {
    
    List<TransactionRecord> findBySenderId(Long senderId);
    
    List<TransactionRecord> findByRecipientId(Long recipientId);
    
    List<TransactionRecord> findByStatus(String status);
    
    List<TransactionRecord> findBySenderIdOrRecipientId(Long senderId, Long recipientId);
}