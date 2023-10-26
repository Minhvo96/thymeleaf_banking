package com.cg.model;

import java.math.BigDecimal;

public class Transfer {
    private Long id;
    private String transferAmount;
    private BigDecimal total;
    private BigDecimal fee;
    private Long recipient;

    public Transfer(BigDecimal fee) {
        this.fee = fee;
    }

    public Transfer() {
    }

    public Transfer(Long id, String transferAmount, BigDecimal total, BigDecimal fee, Long recipient) {
        this.id = id;
        this.transferAmount = transferAmount;
        this.total = total;
        this.fee = fee;
        this.recipient = recipient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
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

    public Long getRecipient() {
        return recipient;
    }

    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }
}

