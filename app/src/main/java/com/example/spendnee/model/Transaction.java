package com.example.spendnee.model;

import java.util.Date;

public class Transaction {
    private String typeTransaction, categoryImage,categoryName , account, note;
    private String date;
    private String username;
    private Double amount;
    private int idTransaction;
    private int idGroup;

    public Transaction() {
    }

    public Transaction( int id,String username, String typeTrans,
                        Double amount, String categoryImage,
                        String categoryName, String date, String account,
                        String note) {
        this.idTransaction = id;
        this.username = username;
        this.typeTransaction = typeTrans;
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
        this.account = account;
        this.note = note;
        this.date = date;
        this.amount = amount;
    }

    public Transaction( int idTransaction, String username,
                        String typeTransaction, Double amount,
                        String categoryImage, String categoryName,
                        String date, String account, String note, int idGroup) {
        this.idTransaction = idTransaction;
        this.username = username;
        this.typeTransaction = typeTransaction;
        this.categoryImage = categoryImage;
        this.categoryName = categoryName;
        this.account = account;
        this.note = note;
        this.date = date;
        this.amount = amount;
        this.idGroup = idGroup;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
