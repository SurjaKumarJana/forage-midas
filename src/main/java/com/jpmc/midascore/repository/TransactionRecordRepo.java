package com.jpmc.midascore.repository;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface TransactionRecordRepo extends CrudRepository<TransactionRecord, Long> {
    TransactionRecord findById(long id);
}