package com.example.spendnee.repository;

import android.provider.ContactsContract;

import com.example.spendnee.api.API;
import com.example.spendnee.livedate.RefreshLiveData;
import com.example.spendnee.model.Category;
import com.example.spendnee.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {

    private DataService dataService;

    public CategoryRepository(){
        this.dataService = API.getAccount().create(DataService.class);
    }

    public RefreshLiveData<List<Category>> getCategory(){
        RefreshLiveData<List<Category>> data = new RefreshLiveData<>(callback -> {
           dataService.getCategory().enqueue(new Callback<List<Category>>() {
               @Override
               public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                   callback.onDataLoaded((ArrayList<Category>) response.body());
               }

               @Override
               public void onFailure(Call<List<Category>> call, Throwable t) {

               }
           });
        });
        return data;
    }

}
