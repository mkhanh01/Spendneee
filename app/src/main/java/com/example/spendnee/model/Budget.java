package com.example.spendnee.model;

public class Budget {
    private String typeBudget, categoryImage,categoryName , account, note;
    private String date;
    private String username;
    private Double totalAmount;
    private Double remainingAmount;
    private int idBudget;

    public Budget(int idBudget,String username, String typeBudget,
                  Double totalAmount, Double remainingAmount, String categoryName,
                  String categoryImage, String date, String account,
                  String note) {
        this.idBudget = idBudget;
        this.username = username;
        this.typeBudget = typeBudget;
        this.totalAmount = totalAmount;
        this.remainingAmount = remainingAmount;
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.date = date;
        this.account = account;
        this.note = note;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getTypeBudget() {
        return typeBudget;
    }

    public void setTypeBudget(String typeBudget) {
        this.typeBudget = typeBudget;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(int idBudget) {
        this.idBudget = idBudget;
    }
}
