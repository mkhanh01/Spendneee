package com.example.spendnee.repository;

import com.example.spendnee.api.API;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.livedate.RefreshLiveData;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.GroupMember;
import com.example.spendnee.model.User;
import com.example.spendnee.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupMemberRepository {
    private DataService dataService;

    public GroupMemberRepository(){
        this.dataService = API.getAccount().create(DataService.class);
    }

    public RefreshLiveData<List<GroupMember>> getGroupMemberList(){
        RefreshLiveData<List<GroupMember>> data = new RefreshLiveData<>(callback -> {
           dataService.getGroupMember(DataLocalManager.getIdGroup()).enqueue(new Callback<List<GroupMember>>() {
               @Override
               public void onResponse(Call<List<GroupMember>> call, Response<List<GroupMember>> response) {
                   callback.onDataLoaded((ArrayList<GroupMember>) response.body());
               }

               @Override
               public void onFailure(Call<List<GroupMember>> call, Throwable t) {

               }
           });
        });

        return data;
    }

    public RefreshLiveData<List<User>> getAllUser(){
        RefreshLiveData<List<User>> data = new RefreshLiveData<>(callback -> {
            dataService.getAllUser(DataLocalManager.getIdGroup()).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    callback.onDataLoaded((ArrayList<User>) response.body());
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {

                }
            });
        });

        return data;
    }

    public Call<BaseResponse> insertGroupMember(String username){
        return dataService.insertGroupMember(DataLocalManager.getIdGroup(), username);
    }


}
