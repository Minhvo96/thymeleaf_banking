package com.cg.model;

import java.math.BigDecimal;

public class Transfer {
    private Long id;
    private BigDecimal transferAmount;
    private BigDecimal total;
    private BigDecimal fee;
    private Customer sender;
    private Customer recipient;
    public Transfer(BigDecimal fee) {
        this.fee = fee;
    }
    public Transfer() {
    }

    public Transfer(Long id, BigDecimal transferAmount, BigDecimal total, BigDecimal fee, Customer sender, Customer recipient) {
        this.id = id;
        this.transferAmount = transferAmount;
        this.total = total;
        this.fee = fee;
        this.sender = sender;
        this.recipient = recipient;
    }

    public Transfer(Long id, BigDecimal transferAmount, BigDecimal total, BigDecimal fee, Customer recipient) {
        this.id = id;
        this.transferAmount = transferAmount;
        this.total = total;
        this.fee = fee;
        this.recipient = recipient;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Customer getRecipient() {
        return recipient;
    }

    public void setRecipient(Customer recipient) {
        this.recipient = recipient;
    }
}

