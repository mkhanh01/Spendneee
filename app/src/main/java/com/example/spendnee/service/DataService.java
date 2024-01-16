package com.example.spendnee.service;

import com.example.spendnee.model.Account;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Budget;
import com.example.spendnee.model.Category;
import com.example.spendnee.model.Group;
import com.example.spendnee.model.GroupMember;
import com.example.spendnee.model.Transaction;
import com.example.spendnee.model.User;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataService {
    @GET("getCategoryList.php")
    Call<List<Category>> getCategory();

    @GET("getAccountList.php")
    Call<List<Account>> getAccount();

    @GET("getAllUser.php")
    Call<List<User>> getAllUser(@Query("idGroup") int idGroup);

    @GET("getTransactionList.php")
    Call<List<Transaction>> getTransactionList(@Query("username") String username);

    @GET("getGroupList.php")
    Call<List<Group>> getGroupList(@Query("username") String username);

    @GET("getMemberGroup.php")
    Call<List<Group>> getMemberGroup(@Query("username") String username);

    @GET("getBudgetList.php")
    Call<List<Budget>> getBudgetList(@Query("username") String username);

    @GET("getTransactionGroup.php")
    Call<List<Transaction>> getTransactionGroup(@Query("idGroup") int idGroup);

    @GET("getGroupMember.php")
    Call<List<GroupMember>> getGroupMember(@Query("idGroup") int idGroup);

    @GET("getTransactionFromDate.php")
    Call<List<Transaction>> getTransactionFromDate(@Query("username") String username,
                                                   @Query("currentDate") String currentDate);

    @GET("insert_transaction.php")
    Call<BaseResponse> insertTransaction (@Query("username") String username,
                                          @Query("typeTransaction") String typeTransaction,
                                          @Query("amount") Double amount,
                                          @Query("categoryName") String categoryName,
                                          @Query("categoryImage") String categoryImage,
                                          @Query("date") String date,
                                          @Query("account") String account,
                                          @Query("note") String note);

    @GET("insertTransactionGroup.php")
    Call<BaseResponse> insertTransactionGroup (@Query("username") String username,
                                          @Query("typeTransaction") String typeTransaction,
                                          @Query("amount") Double amount,
                                          @Query("categoryName") String categoryName,
                                          @Query("categoryImage") String categoryImage,
                                          @Query("date") String date,
                                          @Query("account") String account,
                                          @Query("note") String note,
                                          @Query("idGroup") int idGroup);

    @GET("insert_budget.php")
    Call<BaseResponse> insertBudget (@Query("username") String username,
                                          @Query("typeBudget") String typeBudget,
                                          @Query("totalAmount") Double totalAmount,
                                          @Query("remainingAmount") Double remainingAmount,
                                          @Query("categoryName") String categoryName,
                                          @Query("categoryImage") String categoryImage,
                                          @Query("date") String date,
                                          @Query("account") String account,
                                          @Query("note") String note);

    @GET("updateTransaction.php")
    Call<BaseResponse> updateTransaction(@Query("idTransaction") int idTransaction,
                                         @Query("typeTransaction") String typeTransaction,
                                         @Query("amount") Double amount,
                                         @Query("categoryName") String categoryName,
                                         @Query("categoryImage") String categoryImage,
                                         @Query("date") String date,
                                         @Query("account") String account,
                                         @Query("note") String note);

    @GET("updateGroupTransaction.php")
    Call<BaseResponse> updateGroupTransaction(@Query("idTransaction") int idTransaction,
                                         @Query("typeTransaction") String typeTransaction,
                                         @Query("amount") Double amount,
                                         @Query("categoryName") String categoryName,
                                         @Query("categoryImage") String categoryImage,
                                         @Query("date") String date,
                                         @Query("account") String account,
                                         @Query("note") String note,
                                         @Query("idGroup") int idGroup);



    @GET("insertGroupMember.php")
    Call<BaseResponse> insertGroupMember(@Query("idGroup") int idGroup,
                                         @Query("username") String username);

    @GET("insertGroup.php")
    Call<BaseResponse> insertGroup(@Query("nameGroup") String nameGroup,
                                         @Query("username") String username);

    @GET("updateBudget.php")
    Call<BaseResponse> updateBudget(@Query("idBudget") int idBudget,
                                    @Query("typeBudget") String typeBudget,
                                    @Query("totalAmount") Double totalAmount,
                                    @Query("categoryName") String categoryName,
                                    @Query("categoryImage") String categoryImage,
                                    @Query("date") String date,
                                    @Query("account") String account,
                                    @Query("note") String note);

    @GET("updateGroupName.php")
    Call<BaseResponse> updateGroupName(@Query("nameGroup") String nameGroup,
                                    @Query("newNameGroup") String newNameGroup,
                                    @Query("username") String username);


    @GET("deleteTransaction.php")
    Call<BaseResponse> deleteTransaction(@Query("username") String username,
                                         @Query("idTransaction") int idTransaction);

    @GET("deleteTransactionGroup.php")
    Call<BaseResponse> deleteTransactionGroup(@Query("idTransaction") int idTransaction,
                                              @Query("idGroup") int idGroup);


    @GET("deleteBudget.php")
    Call<BaseResponse> deleteBudget(@Query("username") String username,
                                         @Query("idBudget") int idBudget);

    @GET("deleteGroupName.php")
    Call<BaseResponse> deleteGroupName(@Query("idGroup") int idGroup);
}
