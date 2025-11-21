package com.jpmc.midascore.service;


import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepo;
import com.jpmc.midascore.repository.UserRepository;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MidasService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TransactionRecordRepo txnRepo;
    @Autowired
    private RestTemplate restTemplate;

    private static Logger LOGGER = LoggerFactory.getLogger(MidasService.class);

    @Transactional
    public void saveRecord(Transaction transaction){

        UserRecord sender = userRepo.findById(transaction.getSenderId());
        UserRecord recipient = userRepo.findById(transaction.getRecipientId());

        if(sender.getBalance() >= transaction.getAmount()){

            String url = "http://localhost:8080/incentive";
            float incentive   =0;
            try{
                incentive= restTemplate.postForObject(
                        url,
                        transaction,
                        Balance.class
                ).getAmount();
            }catch (Exception e){

            }

            sender.setBalance(sender.getBalance() - transaction.getAmount());
            recipient.setBalance(recipient.getBalance() + transaction.getAmount()+incentive);

            TransactionRecord record = new TransactionRecord(
                    sender,
                    recipient,
                    transaction.getAmount()
            );
            txnRepo.save(record);
            LOGGER.info("sender:'{}' balance:'{}'",sender.getName(),sender.getBalance() );
            LOGGER.info("recipent:'{}' balance:'{}'",recipient.getName(),recipient.getBalance() );
        }



    }

    public Balance getBalance(Long userId){
        try{
            UserRecord user = userRepo.findById(userId).orElseThrow();
            return new Balance(user.getBalance());
        }catch (Exception e){
            return new Balance(0);
        }
    }
}
