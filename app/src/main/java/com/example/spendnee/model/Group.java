package com.example.spendnee.model;

public class Group {
    private int idGroup;
    private String nameGroup;
    private String  categoryImage;
//    private double amount;categoryName,
//    private int numberOfGroup;
//    private String username;, typeTransactionGroup

    public Group(int idGroup, String nameGroup, String categoryImage) {
        this.idGroup = idGroup;
        this.nameGroup = nameGroup;
        this.categoryImage = categoryImage;
    }

//    public Group(int idGroup, String nameGroup, String typeTransactionGroup, double amount,
//                 String categoryName, String categoryImage, int numberOfGroup, String username) {
//        this.idGroup = idGroup;
//        this.nameGroup = nameGroup;
//        this.typeTransactionGroup = typeTransactionGroup;
//        this.amount = amount;
//        this.categoryName = categoryName;
//        this.categoryImage = categoryImage;
//        this.numberOfGroup = numberOfGroup;
//        this.username = username;
//    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

//    public String getTypeTransactionGroup() {
//        return typeTransactionGroup;
//    }
//
//    public void setTypeTransactionGroup(String typeTransactionGroup) {
//        this.typeTransactionGroup = typeTransactionGroup;
//    }
//
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
//
//    public int getNumberOfGroup() {
//        return numberOfGroup;
//    }
//
//    public void setNumberOfGroup(int numberOfGroup) {
//        this.numberOfGroup = numberOfGroup;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
}
