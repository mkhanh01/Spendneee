package com.example.spendnee.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.spendnee.livedate.RefreshLiveData;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Budget;
import com.example.spendnee.model.Category;
import com.example.spendnee.model.Transaction;
import com.example.spendnee.repository.BudgetRepository;

import java.util.List;

import retrofit2.Call;

public class BudgetViewModel extends AndroidViewModel {

    private BudgetRepository repository;
    private RefreshLiveData<List<Budget>> budgetList;

    public BudgetViewModel(@NonNull Application application) {
        super(application);

        repository = new BudgetRepository();
        budgetList = repository.getBudgetList();
    }

    public LiveData<List<Budget>> getBudgetList(){
        return budgetList;
    }

    public Call<BaseResponse> insertBudget(String username, String typeBudget,
                                           Double totalAmount,Double remainingAmount,
                                           Category category, String date,
                                           String account, String note){
        return repository.insertBudget(username, typeBudget, totalAmount,remainingAmount,
                category, date, account, note);

    }

    public Call<BaseResponse> updateBudget(int idBudget,
                                           String typeBudget,
                                           Double totalAmount,
                                           Category category,
                                           String date,
                                           String account,
                                           String note){

        return repository.updateBudget(idBudget, typeBudget, totalAmount, category, date, account, note);
    }

    public void refreshLiveData(){
        budgetList.refresh();
    }
}
