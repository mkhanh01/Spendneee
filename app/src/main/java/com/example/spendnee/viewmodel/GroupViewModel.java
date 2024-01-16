package com.example.spendnee.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.spendnee.livedate.RefreshLiveData;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Group;
import com.example.spendnee.repository.GroupRepository;

import java.util.List;

import retrofit2.Call;

public class GroupViewModel extends AndroidViewModel {

    private GroupRepository repository;
    private RefreshLiveData<List<Group>> groupList;
    private RefreshLiveData<List<Group>> memberGroup;

    public GroupViewModel(@NonNull Application application) {
        super(application);

        repository = new GroupRepository();
        groupList = repository.getGroupList();
        memberGroup = repository.getMemberGroup();

    }

    public LiveData<List<Group>> getGroupList(){
        return groupList;
    }

    public LiveData<List<Group>> getMemberGroup(){
        return memberGroup;
    }

    public Call<BaseResponse> insertGroup(String nameGroup){
        return repository.insertGroup(nameGroup);
    }

    public Call<BaseResponse> deleteGroupName (int idGroup){
        return repository.deleteGroupName(idGroup);

    }

    public Call<BaseResponse> updateGroupName (String nameGroup, String newNameGroup){
        return repository.updateGroupName(nameGroup, newNameGroup);
    }

    public void refreshLiveData (){
        groupList.refresh();
    }
    public void refreshLiveDataMember (){
        memberGroup.refresh();
    }
}
