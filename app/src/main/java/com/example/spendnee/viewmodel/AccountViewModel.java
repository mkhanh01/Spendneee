package com.example.spendnee.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.spendnee.livedate.RefreshLiveData;
import com.example.spendnee.model.Account;
import com.example.spendnee.repository.AccountRepository;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository repository;
    private RefreshLiveData<List<Account>> liveData;

    public AccountViewModel(@NonNull Application application) {
        super(application);

        repository = new AccountRepository();
        liveData = repository.getAccount();
    }

    public RefreshLiveData getLiveData(){
        return liveData;
    }

  public LiveData<List<Account>> getAccount(){
        return liveData;
  }

  public void refreshLiveData(){
        liveData.refresh();
  }

}
