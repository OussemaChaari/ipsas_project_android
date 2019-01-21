package com.ouss.cheque.chequesclients;

public class ChequeEntity {

    String id;
    String amount;
    String date;
    String state;
    String UserEmail;

    public ChequeEntity(String id, String amount, String date, String state, String userEmail) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.state = state;
        UserEmail = userEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }
}
