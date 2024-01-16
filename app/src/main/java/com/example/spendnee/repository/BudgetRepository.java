package com.example.spendnee.repository;

import android.provider.ContactsContract;

import com.example.spendnee.api.API;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.livedate.RefreshLiveData;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Budget;
import com.example.spendnee.model.Category;
import com.example.spendnee.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class BudgetRepository {
    private DataService dataService;

    public BudgetRepository(){
        this.dataService = API.getAccount().create(DataService.class);
    }

    public RefreshLiveData<List<Budget>> getBudgetList(){
        RefreshLiveData<List<Budget>> data = new RefreshLiveData<>(callback -> {
            dataService.getBudgetList(DataLocalManager.getUsernameData()).enqueue(new Callback<List<Budget>>() {
                @Override
                public void onResponse(Call<List<Budget>> call, Response<List<Budget>> response) {
                    callback.onDataLoaded((ArrayList<Budget>) response.body());
                }

                @Override
                public void onFailure(Call<List<Budget>> call, Throwable t) {

                }
            });
        });

        return data;
    }

    public Call<BaseResponse> insertBudget(String username, String typeBudget,
                                                Double totalAmount,Double remainingAmount, Category category, String date, String account,
                                                String note){
        return dataService.insertBudget(username, typeBudget, totalAmount,remainingAmount,
                category.getNameCategory(), category.getImageCategory(), date, account, note);

    }

    public Call<BaseResponse> updateBudget(int idBudget,
                                           String typeBudget,
                                           Double totalAmount,
                                           Category category,
                                           String date,
                                           String account,
                                           String note){

        return dataService.updateBudget(idBudget, typeBudget, totalAmount,
                category.getNameCategory(), category.getImageCategory(),
                date, account, note);
    }

    public Call<BaseResponse> deleteBudget(int idBudget){
        return dataService.deleteBudget(DataLocalManager.getUsernameData(), idBudget);
    }
}
