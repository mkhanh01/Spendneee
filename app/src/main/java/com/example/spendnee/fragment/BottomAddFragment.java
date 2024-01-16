package com.example.spendnee.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.spendnee.R;
import com.example.spendnee.adapter.AccountAdapter;
import com.example.spendnee.adapter.CategoryAdapter;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.DialogCategoryBinding;
import com.example.spendnee.databinding.FragmentBottomAddBinding;
import com.example.spendnee.model.Account;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Category;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.utils.Helper;
import com.example.spendnee.viewmodel.AccountViewModel;
import com.example.spendnee.viewmodel.BudgetViewModel;
import com.example.spendnee.viewmodel.CategoryViewModel;
import com.example.spendnee.viewmodel.TransactionViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.Calendar;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomAddFragment extends BottomSheetDialogFragment {
    private FragmentBottomAddBinding binding;
    private TransactionViewModel transactionViewModel;
    private CategoryViewModel categoryViewModel;
    private BudgetViewModel budgetViewModel;
    private AccountViewModel accountViewModel;
    private String imageCat;

    private boolean check = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBottomAddBinding.inflate(inflater, container, false);

        DataLocalManager.setCheckTypeTrans(false);

        if (DataLocalManager.getCheckUpdateTransaction()){

            if (DataLocalManager.getTypeTransaction().equals(Constants.INCOME)){
                DataLocalManager.setCheckTypeTrans(true);
                binding.fragmentBottomAddBtnIncome.setTextColor(getContext().getColor(R.color.green));
                binding.fragmentBottomAddBtnExpense.setTextColor(Color.WHITE);
            }else if (DataLocalManager.getTypeTransaction().equals(Constants.EXPENSE)){
                DataLocalManager.setCheckTypeTrans(true);
                binding.fragmentBottomAddBtnIncome.setTextColor(Color.WHITE);
                binding.fragmentBottomAddBtnExpense.setTextColor(getContext().getColor(R.color.red));
            }

            binding.fragmentBottomAddEtAmount.setText(DataLocalManager.getAmount());
            binding.fragmentBottomAddEtCategory.setText(DataLocalManager.getCategoryName());
            binding.fragmentBottomAddEtDate.setText(DataLocalManager.getDate());
            binding.fragmentBottomAddEtAccount.setText(DataLocalManager.getAccount());
            binding.fragmentBottomAddEtNote.setText(DataLocalManager.getNote());

        } else if (DataLocalManager.getCheckUpdateBudget()){

            if (DataLocalManager.getTypeBudget().equals(Constants.INCOME)){
                binding.fragmentBottomAddBtnIncome.setTextColor(getContext().getColor(R.color.green));
                binding.fragmentBottomAddBtnExpense.setTextColor(Color.WHITE);
            }else if (DataLocalManager.getTypeBudget().equals(Constants.EXPENSE)){
                binding.fragmentBottomAddBtnIncome.setTextColor(Color.WHITE);
                binding.fragmentBottomAddBtnExpense.setTextColor(getContext().getColor(R.color.red));
            }

            binding.fragmentBottomAddEtAmount.setText(DataLocalManager.getTotalAmount());
            binding.fragmentBottomAddEtDate.setText(DataLocalManager.getDateBudget());
            binding.fragmentBottomAddEtCategory.setText(DataLocalManager.getBudgetCategoryName());
            binding.fragmentBottomAddEtAccount.setText(DataLocalManager.getAccountBudget());
            binding.fragmentBottomAddEtNote.setText(DataLocalManager.getNoteBudget());
        }

        init();

        return binding.getRoot();

    }

    private void init(){

        transactionViewModel = new ViewModelProvider(getActivity()).get(TransactionViewModel.class);
        budgetViewModel = new ViewModelProvider(getActivity()).get(BudgetViewModel.class);

        binding.fragmentBottomAddIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
            }
        });

        binding.fragmentBottomAddIvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                    calendar.set(Calendar.MONTH, datePicker.getMonth());
                    calendar.set(Calendar.YEAR, datePicker.getYear());

                    String dateToShow = Helper.formatDate(calendar.getTime());

                    binding.fragmentBottomAddEtDate.setText(dateToShow);
                });
                datePickerDialog.show();
            }
        });

        binding.fragmentBottomAddEtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataLocalManager.getCheckUpdateBudget()){
                    dateRange();
                }else {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                    datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                        calendar.set(Calendar.MONTH, datePicker.getMonth());
                        calendar.set(Calendar.YEAR, datePicker.getYear());

                        String dateToShow = Helper.formatDate(calendar.getTime());

                        binding.fragmentBottomAddEtDate.setText(dateToShow);

                    });
                    datePickerDialog.show();
                }

            }
        });

        binding.fragmentBottomAddEtCategory.setOnClickListener(c -> {
            DialogCategoryBinding dialogCategoryBinding = DialogCategoryBinding.inflate(getLayoutInflater());
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogCategoryBinding.getRoot());

            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), new CategoryAdapter.CategoryClickListener() {
                @Override
                public void onCategoryClicked(Category category) {
                    binding.fragmentBottomAddEtCategory.setText(category.getNameCategory());
                    imageCat = category.getImageCategory();

                    if (DataLocalManager.getCheckBudget() || DataLocalManager.getCheckUpdateBudget()){
                        DataLocalManager.setBudgetCategoryImage(imageCat);
                    } else if (DataLocalManager.getCheckUpdateTransaction()){
                        DataLocalManager.setCategoryImage(imageCat);
                    }

                    categoryDialog.dismiss();
                }
            });

            categoryViewModel = new ViewModelProvider(getActivity()).get(CategoryViewModel.class);

            categoryViewModel.getCategory().observe(getViewLifecycleOwner(), categories -> {
                categoryAdapter.setCategoryList(categories);
            });

            dialogCategoryBinding.dialogCategoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            dialogCategoryBinding.dialogCategoryRecyclerView.setAdapter(categoryAdapter);

            categoryViewModel.refreshLiveData();

            categoryDialog.show();

        });

        binding.fragmentBottomAddEtAccount.setOnClickListener(c ->{

            DialogCategoryBinding dialogAccountBinding = DialogCategoryBinding.inflate(getLayoutInflater());
            AlertDialog accountDialog = new AlertDialog.Builder(getContext()).create();
            accountDialog.setView(dialogAccountBinding.getRoot());

            AccountAdapter accountAdapter = new AccountAdapter(getContext(), new AccountAdapter.AccountsClickListener() {
                @Override
                public void onAccountSelected(Account account) {
                    binding.fragmentBottomAddEtAccount.setText(account.getNameAccount());

                    accountDialog.dismiss();
                }
            });

            accountViewModel = new ViewModelProvider(getActivity()).get(AccountViewModel.class);

            accountViewModel.getAccount().observe(getViewLifecycleOwner(), accounts1 -> {
                accountAdapter.setAccountList( accounts1);
            });

            dialogAccountBinding.dialogCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            dialogAccountBinding.dialogCategoryRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            dialogAccountBinding.dialogCategoryRecyclerView.setAdapter(accountAdapter);

            accountViewModel.refreshLiveData();

            accountDialog.show();

        });

        binding.fragmentBottomAddBtnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataLocalManager.setCheckTypeTrans(true);
                binding.fragmentBottomAddBtnIncome.setTextColor(Color.WHITE);
                binding.fragmentBottomAddBtnExpense.setTextColor(getContext().getColor(R.color.red));

                if(DataLocalManager.getCheckBudget() || DataLocalManager.getCheckUpdateBudget()){
                    DataLocalManager.setTypeBudget(Constants.EXPENSE);

                }else {
                    DataLocalManager.setTypeTransaction(Constants.EXPENSE);
                }
            }
        });

        binding.fragmentBottomAddBtnIncome.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataLocalManager.setCheckTypeTrans(true);
                binding.fragmentBottomAddBtnIncome.setTextColor(getContext().getColor(R.color.green));
                binding.fragmentBottomAddBtnExpense.setTextColor(Color.WHITE);

                if(DataLocalManager.getCheckBudget() || DataLocalManager.getCheckUpdateBudget()){
                    DataLocalManager.setTypeBudget(Constants.INCOME);
                } else {
                    DataLocalManager.setTypeTransaction(Constants.INCOME);
                }
            }
        });

        binding.fragmentBottomAddBtnSave.setOnClickListener(c -> {
            if (DataLocalManager.getCheckBudget()){
                addBudget();
            } else if (DataLocalManager.getCheckUpdateTransaction()){
                updateTransaction();
            }else if (DataLocalManager.getCheckUpdateBudget()){
                updateBudget();
            } else {
                addTransaction();
            }


        });
    }

    public void dateRange(){
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();

        materialDatePicker.show(getParentFragmentManager(), "Tag_picker");
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                binding.fragmentBottomAddEtDate.setText(materialDatePicker.getHeaderText());
            }
        });
    }

    public void addTransaction(){

        if (!checkInput())
            return;

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
        progressDialog.setCancelable(false);
        progressDialog.show();

        String typeTransaction = DataLocalManager.getTypeTransaction();
        Double amount = Double.parseDouble(binding.fragmentBottomAddEtAmount.getText().toString().trim());
        String category = binding.fragmentBottomAddEtCategory.getText().toString().trim();
        String date = (binding.fragmentBottomAddEtDate.getText().toString().trim());
        String account = binding.fragmentBottomAddEtAccount.getText().toString().trim();
        String note= binding.fragmentBottomAddEtNote.getText().toString().trim();

        Category categories = new Category(category, imageCat);

        transactionViewModel.insertTransaction(DataLocalManager.getUsernameData(),typeTransaction, amount,
                categories, date, account, note).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body() != null){
                    if (response.body().getIsSuccess().equals(Constants.successfully)){
                        StyleableToast.makeText(getContext(), getResources().getString(R.string.add_trans_successfully), Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();
                        getDialog().dismiss();
                        DataLocalManager.setCategoryName(category);
                        transactionViewModel.refreshLiveData();

                    } else {
                        StyleableToast.makeText(getContext(), getResources().getString(R.string.add_trans_unsuccessfully), Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();

                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }

    public void addBudget(){

        if (!checkInput())
            return;

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
        progressDialog.setCancelable(false);
        progressDialog.show();

        String typeBudget = DataLocalManager.getTypeBudget();
        Double totalAmount = Double.parseDouble(binding.fragmentBottomAddEtAmount.getText().toString().trim());
        Double remainingAmount = 0.0;
        String category = binding.fragmentBottomAddEtCategory.getText().toString().trim();
        String date = (binding.fragmentBottomAddEtDate.getText().toString().trim());
        String account = binding.fragmentBottomAddEtAccount.getText().toString().trim();
        String note= binding.fragmentBottomAddEtNote.getText().toString().trim();

        Category categories = new Category(category, imageCat);

        budgetViewModel.insertBudget(DataLocalManager.getUsernameData(),typeBudget, totalAmount,remainingAmount,
                categories, date, account, note).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body() != null){
                    if (response.body().getIsSuccess().equals(Constants.successfully)){
                        StyleableToast.makeText(getContext(), getResources().getString(R.string.add_budget_successfully), Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();
                        getDialog().dismiss();

                        DataLocalManager.setTypeBudget(typeBudget);
                        DataLocalManager.setTotalAmount(String.valueOf(totalAmount));
                        DataLocalManager.setBudgetCategoryName(category);
                      //  DataLocalManager.setDateBudget(date);

                        budgetViewModel.refreshLiveData();

                    } else {
                        StyleableToast.makeText(getContext(), getResources().getString(R.string.add_budget_unsuccessfully), Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();

                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                StyleableToast.makeText(getContext(), "add fail", Toast.LENGTH_LONG, R.style.myToast).show();
            }
        });

    }


    public void updateTransaction(){
        if (!checkInput())
            return;

        if (!DataLocalManager.getCheckTypeTrans()){
            Toast.makeText(getContext(), getString(R.string.require), Toast.LENGTH_SHORT).show();
           return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
        progressDialog.setCancelable(false);
        progressDialog.show();

        String typeTransaction = DataLocalManager.getTypeTransaction();
        Double amount = Double.parseDouble(binding.fragmentBottomAddEtAmount.getText().toString().trim());
        String category = binding.fragmentBottomAddEtCategory.getText().toString().trim();
        String date = (binding.fragmentBottomAddEtDate.getText().toString().trim());
        String account = binding.fragmentBottomAddEtAccount.getText().toString().trim();
        String note= binding.fragmentBottomAddEtNote.getText().toString().trim();

        Category category1 = new Category(category, DataLocalManager.getCategoryImage());

        transactionViewModel.updateTransaction(DataLocalManager.getIdTransaction(), typeTransaction,
               amount , category1, date, account, note).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body() != null){
                    if (response.body().getIsSuccess().equals(Constants.successfully)){
                        StyleableToast.makeText(getContext(), getString(R.string.update_successfully),
                                Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();
                        getDialog().dismiss();
                        transactionViewModel.refreshLiveData();
                    } else {
                        StyleableToast.makeText(getContext(), getString(R.string.update_unsuccessful),
                                Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                StyleableToast.makeText(getContext(), t.getMessage(),
                        Toast.LENGTH_LONG, R.style.myToast).show();
            }
        });

    }

    public void updateBudget(){
        if (!checkInput())
            return;

//        if (!DataLocalManager.getCheckBudget()){
//            Toast.makeText(getContext(), getString(R.string.require), Toast.LENGTH_SHORT).show();
//            return;
//        }

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
        progressDialog.setCancelable(false);
        progressDialog.show();

        String typeBudget = DataLocalManager.getTypeBudget();
        Double totalAmount = Double.parseDouble(binding.fragmentBottomAddEtAmount.getText().toString().trim());
        String category = binding.fragmentBottomAddEtCategory.getText().toString().trim();
        String date = binding.fragmentBottomAddEtDate.getText().toString().trim();
        String account = binding.fragmentBottomAddEtAccount.getText().toString().trim();
        String note= binding.fragmentBottomAddEtNote.getText().toString().trim();

        Category category1 = new Category(category, DataLocalManager.getBudgetCategoryImage());

        budgetViewModel.updateBudget(DataLocalManager.getIdBudget(), typeBudget,
                totalAmount , category1, date, account, note).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body() != null){
                    if (response.body().getIsSuccess().equals(Constants.successfully)){
                        StyleableToast.makeText(getContext(), getString(R.string.update_successfully),
                                Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();
                        getDialog().dismiss();

                        DataLocalManager.setTypeBudget(typeBudget);
                        DataLocalManager.setTotalAmount(String.valueOf(totalAmount));
                        DataLocalManager.setBudgetCategoryName(category);

                        budgetViewModel.refreshLiveData();
                    } else {
                        StyleableToast.makeText(getContext(), getString(R.string.update_unsuccessful),
                                Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                StyleableToast.makeText(getContext(), t.getMessage(),
                        Toast.LENGTH_LONG, R.style.myToast).show();
            }
        });

    }

    public boolean checkInput(){
        resetError();
        check = true;

        if (TextUtils.isEmpty(binding.fragmentBottomAddEtAmount.getText().toString().trim())){
            binding.fragmentBottomAddTilAmount.setError(getResources().getString(R.string.require));
            check = false;
        }

        if (TextUtils.isEmpty(binding.fragmentBottomAddEtCategory.getText().toString().trim())){
            binding.fragmentBottomAddTilCategory.setError(getResources().getString(R.string.require));
            check = false;
        }
        if (TextUtils.isEmpty(binding.fragmentBottomAddEtDate.getText().toString().trim())){
            binding.fragmentBottomAddTilDate.setError(getResources().getString(R.string.require));
            check = false;
        }

        if (TextUtils.isEmpty(binding.fragmentBottomAddEtAccount.getText().toString().trim())){
            binding.fragmentBottomAddTilAccount.setError(getResources().getString(R.string.require));
            check = false;
        }

        return check;
    }

    private void resetError(){
        binding.fragmentBottomAddEtAmount.setError(null);
        binding.fragmentBottomAddEtCategory.setError(null);
        binding.fragmentBottomAddEtDate.setError(null);
        binding.fragmentBottomAddEtAccount.setError(null);
    }

    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
    }

}