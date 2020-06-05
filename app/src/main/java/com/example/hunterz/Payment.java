package com.example.hunterz;

public class Payment {

    private String paymentId,month,memberid,paymentDate;
    private double amount;

    public Payment(String paymentId, String month, String memberid, String paymentDate, double amount) {
        this.paymentId = paymentId;
        this.month = month;
        this.memberid = memberid;
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

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
