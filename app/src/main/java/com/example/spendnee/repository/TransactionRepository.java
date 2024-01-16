package com.example.spendnee.repository;

import com.example.spendnee.api.API;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.livedate.RefreshLiveData;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Category;
import com.example.spendnee.model.Transaction;
import com.example.spendnee.service.DataService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionRepository {
    private DataService dataService;

    public TransactionRepository(){
        this.dataService = API.getAccount().create(DataService.class);
    }

    public RefreshLiveData<List<Transaction>> getTransactionList(){
        RefreshLiveData<List<Transaction>> data = new RefreshLiveData<>(callback -> {
            dataService.getTransactionList(DataLocalManager.getUsernameData()).enqueue(new Callback<List<Transaction>>() {
                @Override
                public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                    callback.onDataLoaded((ArrayList<Transaction>) response.body());
                }

                @Override
                public void onFailure(Call<List<Transaction>> call, Throwable t) {

                }
            });
        });
        return data;
    }

    public RefreshLiveData<List<Transaction>> getTransactionFromDate(String currentDate){
        RefreshLiveData<List<Transaction>> data = new RefreshLiveData<>(callback -> {
            dataService.getTransactionFromDate(currentDate, DataLocalManager.getUsernameData()).enqueue(new Callback<List<Transaction>>() {
                @Override
                public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                    callback.onDataLoaded((ArrayList<Transaction>) response.body());
                }

                @Override
                public void onFailure(Call<List<Transaction>> call, Throwable t) {

                }
            });
        });
        return data;
    }

    public Call<BaseResponse> insertTransaction(String username, String typeTransaction,
                                                Double amount, Category category, String date, String account,
                                                String note){
        return dataService.insertTransaction(username, typeTransaction, amount,
                category.getNameCategory(), category.getImageCategory(), date, account, note);

    }

    public Call<BaseResponse> insertTransactionGroup(String username, String typeTransaction,
                                                Double amount, Category category, String date, String account,
                                                String note, int idGroup){
        return dataService.insertTransactionGroup(username, typeTransaction, amount,
                category.getNameCategory(), category.getImageCategory(), date, account, note, idGroup);

    }

    public Call<BaseResponse> updateTransaction(int idTransaction, String typeTransaction,
                                                Double amount, Category category,
                                                String date, String account,
                                                String note){

        return dataService.updateTransaction(idTransaction, typeTransaction,
                                                amount, category.getNameCategory(),
                                                category.getImageCategory(),
                                                date,account,note);
    }

    public Call<BaseResponse> updateGroupTransaction(int idTransaction, String typeTransaction,
                                                Double amount, Category category,
                                                String date, String account,
                                                String note, int idGroup){

        return dataService.updateGroupTransaction(idTransaction, typeTransaction,
                amount, category.getNameCategory(),
                category.getImageCategory(),
                date,account,note,
                idGroup);
    }

    public Call<BaseResponse> deleteTransaction(int idTransaction){
        return dataService.deleteTransaction(DataLocalManager.getUsernameData(), idTransaction);
    }
}
