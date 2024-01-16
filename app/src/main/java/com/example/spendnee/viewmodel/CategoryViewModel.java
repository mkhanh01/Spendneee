package com.example.spendnee.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.spendnee.livedate.RefreshLiveData;
import com.example.spendnee.model.Category;
import com.example.spendnee.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository repository;
    private RefreshLiveData<List<Category>> liveData;

    public RefreshLiveData getLiveData(){
        return liveData;
    }

    public CategoryViewModel(@NonNull Application application) {
        super(application);

        repository = new CategoryRepository();
        liveData = repository.getCategory();
    }

    public LiveData<List<Category>> getCategory(){
        return liveData;
    }

    public void refreshLiveData(){
         liveData.refresh();
    }
}
