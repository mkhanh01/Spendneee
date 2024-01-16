package com.example.spendnee.utils;

import com.example.spendnee.R;
import com.example.spendnee.model.Category;

import java.util.ArrayList;

public class Constants {

    public static final String baseURL = "https://spendneee.000webhostapp.com/my%20spendnee/";
//    public static final String baseURL ="https://spendnee.000webhostapp.com/myPHP/";

    public static final String PATTERN = "dd/MM/yyyy";
    public static final String successfully = "1";
    public static final String failed = "0";

    public static ArrayList<Category> categories = new ArrayList<>() ;

    public static String INCOME = "INCOME";
    public static String EXPENSE = "EXPENSE";

    public static String FOOD = "Food";
    public static String EDUCATION = "Education";
    public static String HEALTH = "Health";
    public static String INSURANCE = "Insurance";
    public static String FURNITURE = "Furniture";
    public static String HOUSEWARE = "Houseware";
    public static String PET = "Pet";
    public static String COSMETICS = "Cosmetics";
    public static String ENTERTAINMENT = "Entertainment";
    public static String INVESTMENT = "Investment";
    public static String SALARY = "Salary";
    public static String OTHER = "Other";

    public static int DAILY = 0;
    public static int MONTHLY = 1;

    public static int SELECTED_TAB = 0;
    public static int SELECTED_TAB_STATS = 0;
    public static String SELECTED_STATS_TYPE = Constants.INCOME;

//    public static void setCategories(){
//        categories.add(new Category("Food", R.drawable.ic_food));
//        categories.add(new Category("Education", R.drawable.ic_education));
//        categories.add(new Category("Health", R.drawable.ic_health));
//        categories.add(new Category("Insurance", R.drawable.ic_insurance));
//        categories.add(new Category("Furniture", R.drawable.ic_furniture));
//        categories.add(new Category("Houseware", R.drawable.ic_houseware));
//        categories.add(new Category("Pet", R.drawable.ic_pet));
//        categories.add(new Category("Other", R.drawable.ic_other_costs));
//    }
//
//    public static Category getCategoryDetails(String categoryName) {
//        for (Category cat : categories) {
//            if (cat.getNameCategory().equals(categoryName)) {
//                return cat;
//            }
//        }
//        return null;
//    }
}
