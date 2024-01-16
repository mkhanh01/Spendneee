package com.example.spendnee.data;

import android.content.Context;

public class DataLocalManager {
    private static final String KEY_FIRST_INSTALL = "KEY_FIRST_INSTALL";
    private static final String SUM_INCOME = "SUM_INCOME";
    private static final String SUM_EXPENSE = "SUM_EXPENSE";
    private static final String SUM_TOTAL = "SUM_TOTAL";
    private static final String CHECK_FROM_LOGOUT = "CHECK_FROM_LOGOUT";
    private static final String CHECK_LOGIN = "CHECK_LOGIN";
    private static final String CHECK_UPDATE_TRANSACTION = "CHECK_UPDATE_TRANSACTION";

    private static final String CHECK_TYPE_TRANS = "CHECK_TYPE_TRANS";

    private static final String CHECK_INCOME = "CHECK_INCOME";
    private static final String CHECK_EXPENSE = "CHECK_EXPENSE";

    private static final String CHECK_UPDATE_BUDGET = "CHECK_UPDATE_BUDGET";

    private static final String CHECK_BUDGET = "CHECK_BUDGET";
    private static final String CHECK_PAYBOOK = "CHECK_PAYBOOK";


    private static final String ID_GROUP = "ID_GROUP";


    private static final String ID_TRANSACTION = "ID_TRANSACTION";
    private static final String TYPE_TRANSACTION = "TYPE_TRANSACTION";
    private static final String AMOUNT = "AMOUNT";
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final String CATEGORY_IMAGE = "CATEGORY_IMAGE";
    private static final String BUDGET_CATEGORY_IMAGE = "BUDGET_CATEGORY_IMAGE";
    private static final String DATE = "DATE";
    private static final String CURRENT_DATE = "CURRENT_DATE";
    private static final String ACCOUNT = "ACCOUNT";
    private static final String NOTE = "NOTE";

    private static final String TYPE_BUDGET = "TYPE_BUDGET";
    private static final String ID_BUDGET = "ID_BUDGET";
    private static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
    private static final String BUDGET_CATEGORY_NAME = "BUDGET_CATEGORY_NAME";
    private static final String DATE_BUDGET = "DATE_BUDGET";
    private static final String CURRENT_DATE_BUDGET = "CURRENT_DATE_BUDGET";
    private static final String ACCOUNT_BUDGET = "ACCOUNT_BUDGET";
    private static final String NOTE_BUDGET = "NOTE_BUDGET";

    private static final String TYPE_GROUP_TRANSACTION = "TYPE_GROUP_TRANSACTION";


    private static final String FOOD = "FOOD";
    private static final String EDUCATION = "EDUCATION";
    private static final String HEALTH = "HEALTH";
    private static final String INSURANCE = "INSURANCE";
    private static final String FURNITURE = "FURNITURE";
    private static final String HOUSEWARE = "HOUSEWARE";
    private static final String PET = "PET";
    private static final String COSMETICS = "COSMETICS";
    private static final String ENTERTAINMENT = "ENTERTAINMENT";
    private static final String INVESTMENT = "INVESTMENT";
    private static final String SALARY = "SALARY";
    private static final String OTHER = "OTHER";

    private static final String GROUP_INCOME = "GROUP_INCOME";
    private static final String GROUP_EXPENSE = "GROUP_EXPENSE";
    private static final String GROUP_TOTAL = "GROUP_TOTAL";


    private static final String FOOD_INCOME = "FOOD_INCOME";
    private static final String EDUCATION_INCOME = "EDUCATION_INCOME";
    private static final String HEALTH_INCOME = "HEALTH_INCOME";
    private static final String INSURANCE_INCOME = "INSURANCE_INCOME";
    private static final String FURNITURE_INCOME = "FURNITURE_INCOME";
    private static final String HOUSEWARE_INCOME = "HOUSEWARE_INCOME";
    private static final String PET_INCOME = "PET_INCOME";
    private static final String COSMETICS_INCOME = "COSMETICS_INCOME";
    private static final String ENTERTAINMENT_INCOME = "ENTERTAINMENT_INCOME";
    private static final String INVESTMENT_INCOME = "INVESTMENT_INCOME";
    private static final String SALARY_INCOME = "SALARY_INCOME";
    private static final String OTHER_INCOME = "OTHER_INCOME";



    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "PASSWORD";
    private static final String USERNAME = "USERNAME";
    private static final String NAME = "NAME";

    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setFirstInstall(boolean value) {
        DataLocalManager.getInstance().mySharedPreferences.putBoolean(KEY_FIRST_INSTALL, value);
    }

    public static void setSumIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(SUM_INCOME, value);
    }

    public static void setSumExpense(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(SUM_EXPENSE, value);
    }

    public static void setSumTotal(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(SUM_TOTAL, value);
    }

    public static void setCheckFromLogout(boolean value) {
        DataLocalManager.getInstance().mySharedPreferences.putBoolean(CHECK_FROM_LOGOUT, value);
    }

    public static void setCheckLogin(boolean value) {
        DataLocalManager.getInstance().mySharedPreferences.putBoolean(CHECK_LOGIN, value);
    }

    public static void setCheckIncome(boolean value) {
        DataLocalManager.getInstance().mySharedPreferences.putBoolean(CHECK_INCOME, value);
    }

    public static void setCheckExpense(boolean value) {
        DataLocalManager.getInstance().mySharedPreferences.putBoolean(CHECK_EXPENSE, value);
    }
    public static void setCheckUpdateTransaction(boolean value) {
        DataLocalManager.getInstance().mySharedPreferences.putBoolean(CHECK_UPDATE_TRANSACTION, value);
    }

    public static void setCheckUpdateBudget(boolean value) {
        DataLocalManager.getInstance().mySharedPreferences.putBoolean(CHECK_UPDATE_BUDGET, value);
    }

    public static void setCheckBudget(boolean value) {
        DataLocalManager.getInstance().mySharedPreferences.putBoolean(CHECK_BUDGET, value);
    }

    public static void setCheckTypeTrans(boolean value) {
        DataLocalManager.getInstance().mySharedPreferences.putBoolean(CHECK_TYPE_TRANS, value);
    }

    public static void setCheckPaybook(boolean value) {
        DataLocalManager.getInstance().mySharedPreferences.putBoolean(CHECK_PAYBOOK, value);
    }

    public static void setEmail(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(EMAIL, value);
    }




    public static void setIdGroup(int value) {
        DataLocalManager.getInstance().mySharedPreferences.putIntValue(ID_GROUP, value);
    }




    public static void setIdTransaction(int value) {
        DataLocalManager.getInstance().mySharedPreferences.putIntValue(ID_TRANSACTION, value);
    }

    public static void setTypeTransaction(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(TYPE_TRANSACTION, value);
    }


    public static void setTypeGroupTransaction(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(TYPE_GROUP_TRANSACTION, value);
    }


    public static void setTypeBudget(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(TYPE_BUDGET, value);
    }

    public static void setAmount(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(AMOUNT, value);
    }
    public static void setCategoryName(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(CATEGORY_NAME, value);
    }
    public static void setCategoryImage(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(CATEGORY_IMAGE, value);
    }

    public static void setBudgetCategoryImage(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(BUDGET_CATEGORY_IMAGE, value);
    }


    public static void setDate(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(DATE, value);
    }
    public static void setAccount(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(ACCOUNT, value);
    }
    public static void setNote(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(NOTE, value);
    }

    public static void setCurrentDate(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(CURRENT_DATE, value);
    }





    public static void setFood(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(FOOD, value);
    }

    public static void setEducation(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(EDUCATION, value);
    }

    public static void setHealth(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(HEALTH, value);
    }

    public static void setInsurance(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(INSURANCE, value);
    }

    public static void setFurniture(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(FURNITURE, value);
    }

    public static void setHouseware(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(HOUSEWARE, value);
    }

    public static void setPet(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PET, value);
    }
    public static void setCosmetics(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(COSMETICS, value);
    }

    public static void setEntertainment(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(ENTERTAINMENT, value);
    }
    public static void setInvestment(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(INVESTMENT, value);
    }

    public static void setSalary(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(SALARY, value);
    }
    public static void setOther(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(OTHER, value);
    }




    public static void setGroupIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(GROUP_INCOME, value);
    }

    public static void setGroupExpense(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(GROUP_EXPENSE, value);
    }

    public static void setGroupTotal(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(GROUP_TOTAL, value);
    }

    public static void setFoodIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(FOOD_INCOME, value);
    }

    public static void setEducationIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(EDUCATION_INCOME, value);
    }

    public static void setHealthIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(HEALTH_INCOME, value);
    }

    public static void setInsuranceIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(INSURANCE_INCOME, value);
    }

    public static void setFurnitureIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(FURNITURE_INCOME, value);
    }

    public static void setHousewareIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(HOUSEWARE_INCOME, value);
    }

    public static void setPetIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PET_INCOME, value);
    }
    public static void setCosmeticsIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(COSMETICS_INCOME, value);
    }

    public static void setEntertainmentIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(ENTERTAINMENT_INCOME, value);
    }
    public static void setInvestmentIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(INVESTMENT_INCOME, value);
    }

    public static void setSalaryIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(SALARY_INCOME, value);
    }
    public static void setOtherIncome(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(OTHER_INCOME, value);
    }





    public static void setIdBudget(int value) {
        DataLocalManager.getInstance().mySharedPreferences.putIntValue(ID_BUDGET, value);
    }

    public static void setTotalAmount(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(TOTAL_AMOUNT, value);
    }
    public static void setBudgetCategoryName(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(BUDGET_CATEGORY_NAME, value);
    }

    public static void setDateBudget(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(DATE_BUDGET, value);
    }
    public static void setAccountBudget(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(ACCOUNT_BUDGET, value);
    }
    public static void setNoteBudget(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(NOTE_BUDGET, value);
    }

    public static void setCurrentDateBudget(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(CURRENT_DATE_BUDGET, value);
    }

    public static void setPassword(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PASSWORD, value);
    }

    public static void setUsernameData(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(USERNAME, value);
    }

    public static void setNameData(String value) {
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(NAME, value);
    }

    public static String getEmail() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(EMAIL);
    }

    public static int getIdTransaction() {
        return DataLocalManager.getInstance().mySharedPreferences.getIntValue(ID_TRANSACTION);
    }

    public static String getTypeTransaction() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(TYPE_TRANSACTION);
    }

    public static String getTypeGroupTransaction() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(TYPE_GROUP_TRANSACTION);
    }

    public static String getTypeBudget() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(TYPE_BUDGET);
    }

    public static String getAmount() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(AMOUNT);
    }



    public static int getIdGroup() {
        return DataLocalManager.getInstance().mySharedPreferences.getIntValue(ID_GROUP);
    }

    public static String getFood() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(FOOD);
    }

    public static String getEducation() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(EDUCATION);
    }
    public static String getHealth() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(HEALTH);
    }

    public static String getInsurance() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(INSURANCE);
    }

    public static String getFurniture() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(FURNITURE);
    }

    public static String getHouseware() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(HOUSEWARE);
    }

    public static String getPET() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PET);
    }

    public static String getCosmetics() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(COSMETICS);
    }

    public static String getEntertainment() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(ENTERTAINMENT);
    }

    public static String getInvestment() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(INVESTMENT);
    }

    public static String getSalary() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(SALARY);
    }

    public static String getOther() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(OTHER);
    }




    public static String getGroupIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(GROUP_INCOME);
    }

    public static String getGroupExpense() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(GROUP_EXPENSE);
    }
    public static String getGroupTotal() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(GROUP_TOTAL);
    }



    public static String getFoodIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(FOOD_INCOME);
    }

    public static String getEducationIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(EDUCATION_INCOME);
    }
    public static String getHealthIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(HEALTH_INCOME);
    }

    public static String getInsuranceIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(INSURANCE_INCOME);
    }

    public static String getFurnitureIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(FURNITURE_INCOME);
    }

    public static String getHousewareIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(HOUSEWARE_INCOME);
    }

    public static String getPETIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PET_INCOME);
    }

    public static String getCosmeticsIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(COSMETICS_INCOME);
    }

    public static String getEntertainmentIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(ENTERTAINMENT_INCOME);
    }

    public static String getInvestmentIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(INVESTMENT_INCOME);
    }

    public static String getSalaryIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(SALARY_INCOME);
    }

    public static String getOtherIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(OTHER_INCOME);
    }




    public static String getCategoryName() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(CATEGORY_NAME);
    }

    public static String getCategoryImage() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(CATEGORY_IMAGE);
    }

    public static String getBudgetCategoryImage() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(BUDGET_CATEGORY_IMAGE);
    }

    public static String getDate() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(DATE);
    }

    public static String getAccount() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(ACCOUNT);
    }

    public static String getNote() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(NOTE);
    }

    public static String getCurrentDate() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(CURRENT_DATE);
    }




    public static int getIdBudget() {
        return DataLocalManager.getInstance().mySharedPreferences.getIntValue(ID_BUDGET);
    }


    public static String getTotalAmount() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(TOTAL_AMOUNT);
    }

    public static String getBudgetCategoryName() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(BUDGET_CATEGORY_NAME);
    }

    public static String getDateBudget() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(DATE_BUDGET);
    }

    public static String getAccountBudget() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(ACCOUNT_BUDGET);
    }

    public static String getNoteBudget() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(NOTE_BUDGET);
    }

    public static String getCurrentDateBudget() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(CURRENT_DATE_BUDGET);
    }


    public static String getPassword() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(PASSWORD);
    }

    public static boolean getFirstInstall() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(KEY_FIRST_INSTALL);
    }

    public static boolean getCheckLogin() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(CHECK_LOGIN);
    }

    public static boolean getCheckIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(CHECK_INCOME);
    }

    public static boolean getCheckTypeTrans() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(CHECK_TYPE_TRANS);
    }

    public static boolean getCheckExpense() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(CHECK_EXPENSE);
    }

    public static boolean getCheckUpdateTransaction() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(CHECK_UPDATE_TRANSACTION);
    }

    public static boolean getCheckUpdateBudget() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(CHECK_UPDATE_BUDGET);
    }

    public static boolean getCheckBudget() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(CHECK_BUDGET);
    }

    public static boolean getCheckPaybook() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(CHECK_PAYBOOK);
    }

    public static String getSumIncome() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(SUM_INCOME);
    }

    public static String getSumExpense() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(SUM_EXPENSE);
    }

    public static String getSumTotal() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(SUM_TOTAL);
    }

    public static boolean getCheckFromLogout() {
        return DataLocalManager.getInstance().mySharedPreferences.getBooleanValue(CHECK_FROM_LOGOUT);
    }

    public static String getUsernameData() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(USERNAME);
    }

    public static String getNameData() {
        return DataLocalManager.getInstance().mySharedPreferences.getStringValue(NAME);
    }

    public static void clearDataLocal (){
        DataLocalManager.getInstance().mySharedPreferences.clearShare();
    }
}
