package com.example.spendnee.repository;

import com.example.spendnee.api.API;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.livedate.RefreshLiveData;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Group;
import com.example.spendnee.service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupRepository {
    private DataService dataService;

    public GroupRepository(){
        this.dataService = API.getAccount().create(DataService.class);
    }

    public RefreshLiveData<List<Group>> getGroupList(){
        RefreshLiveData<List<Group>> data = new RefreshLiveData<>(callback -> {
            dataService.getGroupList(DataLocalManager.getUsernameData()).enqueue(new Callback<List<Group>>() {
                @Override
                public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                    callback.onDataLoaded((ArrayList<Group>) response.body());
                }

                @Override
                public void onFailure(Call<List<Group>> call, Throwable t) {

                }
            });
        });
        return data;
    }

    public RefreshLiveData<List<Group>> getMemberGroup(){
        RefreshLiveData<List<Group>> data = new RefreshLiveData<>(callback -> {
            dataService.getMemberGroup(DataLocalManager.getUsernameData()).enqueue(new Callback<List<Group>>() {
                @Override
                public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                    callback.onDataLoaded((ArrayList<Group>) response.body());
                }

                @Override
                public void onFailure(Call<List<Group>> call, Throwable t) {

                }
            });
        });
        return data;
    }


    public Call<BaseResponse> insertGroup(String nameGroup){
        return dataService.insertGroup(nameGroup, DataLocalManager.getUsernameData());
    }

    public Call<BaseResponse> updateGroupName (String nameGroup, String newNameGroup){
        return dataService.updateGroupName(nameGroup, newNameGroup, DataLocalManager.getUsernameData());
    }

    public Call<BaseResponse> deleteGroupName (int idGroup){
        return dataService.deleteGroupName(idGroup);
    }
}
