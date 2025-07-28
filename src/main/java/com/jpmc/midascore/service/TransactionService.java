package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.repository.TransactionRepository;
import com.jpmc.midascore.foundation.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {
    
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IncentiveService incentiveService;
    
    @Transactional
    public void processTransaction(Transaction transaction) {
        logger.info("Processing transaction: {}", transaction);
        
        try {
            // Validate sender and recipient exist
            UserRecord sender = userRepository.findById(transaction.getSenderId()).orElse(null);
            UserRecord recipient = userRepository.findById(transaction.getRecipientId()).orElse(null);
            
            if (sender == null) {
                logger.warn("Transaction rejected: Sender with ID {} not found", transaction.getSenderId());
                saveFailedTransaction(transaction, "SENDER_NOT_FOUND");
                return;
            }
            
            if (recipient == null) {
                logger.warn("Transaction rejected: Recipient with ID {} not found", transaction.getRecipientId());
                saveFailedTransaction(transaction, "RECIPIENT_NOT_FOUND");
                return;
            }
            
            // Validate sender has sufficient balance
            if (sender.getBalance() < transaction.getAmount()) {
                logger.warn("Transaction rejected: Sender {} has insufficient balance. Required: {}, Available: {}", 
                           sender.getName(), transaction.getAmount(), sender.getBalance());
                saveFailedTransaction(transaction, "INSUFFICIENT_BALANCE");
                return;
            }
            
            // Validate transaction amount is positive
            if (transaction.getAmount() <= 0) {
                logger.warn("Transaction rejected: Invalid amount {}", transaction.getAmount());
                saveFailedTransaction(transaction, "INVALID_AMOUNT");
                return;
            }
            
            // Validate sender is not the same as recipient
            if (transaction.getSenderId() == transaction.getRecipientId()) {
                logger.warn("Transaction rejected: Sender and recipient are the same (ID: {})", transaction.getSenderId());
                saveFailedTransaction(transaction, "SAME_SENDER_RECIPIENT");
                return;
            }
            
            Incentive incentive = incentiveService.getIncentive(transaction);
            float incentiveAmount = (incentive != null) ? incentive.getAmount() : 0.0f;
            // Process the transaction atomically
            sender.setBalance(sender.getBalance() - transaction.getAmount());
            recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);
            
            // Save updated balances
            userRepository.save(sender);
            userRepository.save(recipient);
            
            // Save successful transaction record
            TransactionRecord transactionRecord = new TransactionRecord(
                transaction.getSenderId(),
                transaction.getRecipientId(),
                transaction.getAmount(),
                "COMPLETED",
                incentiveAmount
            );
            transactionRepository.save(transactionRecord);
            
            logger.info("Transaction completed successfully: {} -> {} (Amount: {})", 
                       sender.getName(), recipient.getName(), transaction.getAmount());
            logger.info("Updated balances - {}: {}, {}: {}", 
                       sender.getName(), sender.getBalance(), 
                       recipient.getName(), recipient.getBalance());
            
        } catch (Exception e) {
            logger.error("Error processing transaction: {}", transaction, e);
            saveFailedTransaction(transaction, "PROCESSING_ERROR");
            throw e; // Re-throw to trigger transaction rollback
        }
    }
    
    private void saveFailedTransaction(Transaction transaction, String failureReason) {
        try {
            TransactionRecord failedRecord = new TransactionRecord(
                transaction.getSenderId(),
                transaction.getRecipientId(),
                transaction.getAmount(),
                "FAILED_" + failureReason,
                null // No incentive for failed transactions    
            );
            transactionRepository.save(failedRecord);
            logger.info("Failed transaction recorded with reason: {}", failureReason);
        } catch (Exception e) {
            logger.error("Failed to save failed transaction record", e);
        }
    }
    
    public UserRecord getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    
    public Iterable<TransactionRecord> getAllTransactions() {
        return transactionRepository.findAll();
    }
    
    public Iterable<UserRecord> getAllUsers() {
        return userRepository.findAll();
    }
}