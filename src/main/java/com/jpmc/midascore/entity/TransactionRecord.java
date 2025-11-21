package com.jpmc.midascore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class TransactionRecord {
    @Id
    @GeneratedValue()
    private long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonIgnore
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    @JsonIgnore
    private UserRecord recipient;

    private float amount;


    protected TransactionRecord() {
    }

    public TransactionRecord(UserRecord sender , UserRecord recipient , float amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public void setRecipient(UserRecord recipient) {
        this.recipient = recipient;
    }

    public UserRecord getSender() {
        return sender;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TransactionRecord{" +
                "id=" + id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", amount=" + amount +
                '}';
    }
}
