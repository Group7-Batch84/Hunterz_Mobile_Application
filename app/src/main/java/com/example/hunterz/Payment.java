package com.example.hunterz;

public class Payment {

    private String paymentId,month,id,paymentDate,amount;

    public Payment() {
    }

    public Payment(String paymentId, String month, String id, String paymentDate, String amount) {
        this.paymentId = paymentId;
        this.month = month;
        this.id = id;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
