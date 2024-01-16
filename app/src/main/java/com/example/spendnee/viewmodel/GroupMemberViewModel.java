package com.example.spendnee.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.spendnee.livedate.RefreshLiveData;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.GroupMember;
import com.example.spendnee.model.User;
import com.example.spendnee.repository.GroupMemberRepository;

import java.util.List;

import retrofit2.Call;

public class GroupMemberViewModel extends AndroidViewModel {

    private GroupMemberRepository repository;
    private RefreshLiveData<List<GroupMember>> members;

    private RefreshLiveData<List<User>> users;

    public GroupMemberViewModel(@NonNull Application application) {
        super(application);

        repository = new GroupMemberRepository();
        members = repository.getGroupMemberList();
        users = repository.getAllUser();
    }

    public LiveData<List<GroupMember>> getGroupMemberList(){
        return members;
    }

    public LiveData<List<User>> getAllUser(){
        return users;
    }

    public Call<BaseResponse> insertGroupMember(String username){
        return repository.insertGroupMember(username);
    }

    public void refreshLiveData (){
        members.refresh();
    }
    public void refreshAllUser(){
        users.refresh();
    }
}
