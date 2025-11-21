package com.jpmc.midascore.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.service.MidasService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaListenerConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(KafkaListenerConfig.class);
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MidasService txnService;

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "txn-group")
    public void listenTxnTopic(ConsumerRecord<String, String> record) throws JsonProcessingException {

        Transaction transaction = OBJECT_MAPPER.readValue(record.value(), Transaction.class);
        LOGGER.info("recieved : {}",transaction);
        txnService.saveRecord(transaction);

    }

}
