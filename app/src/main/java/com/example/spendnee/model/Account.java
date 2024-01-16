package com.example.spendnee.model;

public class Account {
    private int idAccount;
    private String amountAccount;
    private String nameAccount;

    public Account() {
    }

    public Account(int idAccount, String amountAccount, String nameAccount) {
        this.idAccount = idAccount;
        this.amountAccount = amountAccount;
        this.nameAccount = nameAccount;
    }

    public Account(String accountAmount, String accountName) {
        this.amountAccount = accountAmount;
        this.nameAccount = accountName;
    }

    public String getAmountAccount() {
        return amountAccount;
    }

    public void setAmountAccount(String amountAccount) {
        this.amountAccount = amountAccount;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }
}
