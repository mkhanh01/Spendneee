package com.example.spendnee.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.livedate.RefreshLiveData;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Category;
import com.example.spendnee.model.Transaction;
import com.example.spendnee.repository.TransactionRepository;
import com.example.spendnee.utils.Constants;

import java.util.Date;
import java.util.List;

import retrofit2.Call;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionRepository repository;
    private RefreshLiveData<List<Transaction>>  transactionList;
    private RefreshLiveData<List<Transaction>>  transactionList1;
//    private Transaction transaction = new Transaction();
    private String currentDate;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        repository = new TransactionRepository();
        transactionList = repository.getTransactionList();
        transactionList1 = repository.getTransactionFromDate(DataLocalManager.getCurrentDate());
    }

    public LiveData<List<Transaction>> getTransactionList(){
        return transactionList;
    }

    public LiveData<List<Transaction>> getTransactionFromDate(){
        return transactionList1;
    }

    public Call<BaseResponse> insertTransaction(String username, String typeTransaction,
                                                Double amount, Category category, String date, String account,
                                                String note){
        return repository.insertTransaction(username, typeTransaction, amount,
                category, date, account, note);

    }

    public Call<BaseResponse> insertTransactionGroup(String username, String typeTransaction,
                                                Double amount, Category category, String date, String account,
                                                String note, int idGroup){
        return repository.insertTransactionGroup(username, typeTransaction, amount,
                category, date, account, note, idGroup);

    }

    public Call<BaseResponse> updateTransaction(int idTransaction, String typeTransaction,
                                                Double amount, Category category, String date, String account,
                                                String note){
        return repository.updateTransaction(idTransaction, typeTransaction, amount, category, date, account, note);
    }

    public Call<BaseResponse> updateGroupTransaction(int idTransaction, String typeTransaction,
                                                Double amount, Category category, String date, String account,
                                                String note, int idGroup){
        return repository.updateGroupTransaction(idTransaction, typeTransaction, amount, category, date, account, note, idGroup);
    }


    public void refreshLiveData(){
        transactionList.refresh();
    }

}
