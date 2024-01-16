package com.example.spendnee.repository;

import com.example.spendnee.api.API;
import com.example.spendnee.livedate.RefreshLiveData;
import com.example.spendnee.model.Account;
import com.example.spendnee.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepository {

    private DataService dataService;

    public AccountRepository(){
        this.dataService = API.getAccount().create(DataService.class);
    }

    public RefreshLiveData<List<Account>> getAccount(){
        RefreshLiveData<List<Account>> data = new RefreshLiveData<>(callback -> {
            dataService.getAccount().enqueue(new Callback<List<Account>>() {
                @Override
                public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                    callback.onDataLoaded((ArrayList<Account>) response.body());
                }

                @Override
                public void onFailure(Call<List<Account>> call, Throwable t) {

                }
            });
        });

        return data;
    }

}
