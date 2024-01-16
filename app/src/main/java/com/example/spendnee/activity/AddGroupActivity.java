package com.example.spendnee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.spendnee.R;
import com.example.spendnee.adapter.AccountAdapter;
import com.example.spendnee.adapter.CategoryAdapter;
import com.example.spendnee.adapter.GroupMemberAdapter;
import com.example.spendnee.adapter.UserAdapter;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.ActivityAddGroupBinding;
import com.example.spendnee.databinding.DialogCategoryBinding;
import com.example.spendnee.model.Account;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Category;
import com.example.spendnee.model.GroupMember;
import com.example.spendnee.model.User;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.utils.Helper;
import com.example.spendnee.viewmodel.AccountViewModel;
import com.example.spendnee.viewmodel.BudgetViewModel;
import com.example.spendnee.viewmodel.CategoryViewModel;
import com.example.spendnee.viewmodel.GroupMemberViewModel;
import com.example.spendnee.viewmodel.TransactionViewModel;

import java.util.Calendar;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGroupActivity extends AppCompatActivity {

    private ActivityAddGroupBinding binding;
    private GroupMemberViewModel groupMemberViewModel;
    private TransactionViewModel transactionViewModel;
    private CategoryViewModel categoryViewModel;
    private AccountViewModel accountViewModel;
    private String imageCat;

    boolean check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddGroupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        DataLocalManager.setCheckTypeTrans(false);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (DataLocalManager.getCheckUpdateTransaction()){
            if (DataLocalManager.getTypeTransaction().equals(Constants.INCOME)){
                DataLocalManager.setCheckTypeTrans(true);
                binding.activityAddGroupBtnIncome.setTextColor(getColor(R.color.green));
                binding.activityAddGroupBtnExpense.setTextColor(Color.WHITE);
            }else if (DataLocalManager.getTypeTransaction().equals(Constants.EXPENSE)){
                DataLocalManager.setCheckTypeTrans(true);
                binding.activityAddGroupBtnIncome.setTextColor(Color.WHITE);
                binding.activityAddGroupBtnExpense.setTextColor(getColor(R.color.red));
            }

            binding.activityAddGroupEtAmount.setText(DataLocalManager.getAmount());
            binding.activityAddGroupEtCategory.setText(DataLocalManager.getCategoryName());
            binding.activityAddGroupEtDate.setText(DataLocalManager.getDate());
            binding.activityAddGroupEtAccount.setText(DataLocalManager.getAccount());
            binding.activityAddGroupEtNote.setText(DataLocalManager.getNote());
        }

        init();
    }

    private void init(){
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        groupMemberViewModel = new ViewModelProvider(this).get(GroupMemberViewModel.class);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        binding.activityAddGroupIvCancel.setOnClickListener(c ->{
            finish();
        });

        binding.activityAddGroupEtDate.setOnClickListener(c ->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(this);
            datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                calendar.set(Calendar.MONTH, datePicker.getMonth());
                calendar.set(Calendar.YEAR, datePicker.getYear());

                String dateToShow = Helper.formatDate(calendar.getTime());

                binding.activityAddGroupEtDate.setText(dateToShow);
            });
            datePickerDialog.show();
        });

        binding.activityAddGroupEtCategory.setOnClickListener(c -> {
            DialogCategoryBinding dialogCategoryBinding = DialogCategoryBinding.inflate(getLayoutInflater());
            AlertDialog categoryDialog = new AlertDialog.Builder(this).create();
            categoryDialog.setView(dialogCategoryBinding.getRoot());

            CategoryAdapter categoryAdapter = new CategoryAdapter(this, new CategoryAdapter.CategoryClickListener() {
                @Override
                public void onCategoryClicked(Category category) {
                    binding.activityAddGroupEtCategory.setText(category.getNameCategory());
                    imageCat = category.getImageCategory();

//                    if (DataLocalManager.getCheckBudget() || DataLocalManager.getCheckUpdateBudget()){
//                        DataLocalManager.setBudgetCategoryImage(imageCat);
//                    } else if (DataLocalManager.getCheckUpdateTransaction()){
                        DataLocalManager.setCategoryImage(imageCat);
//                    }

                    categoryDialog.dismiss();
                }
            });

            categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

            categoryViewModel.getCategory().observe(this, categories -> {
                categoryAdapter.setCategoryList(categories);
            });

            dialogCategoryBinding.dialogCategoryRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
            dialogCategoryBinding.dialogCategoryRecyclerView.setAdapter(categoryAdapter);

            categoryViewModel.refreshLiveData();

            categoryDialog.show();

        });

        binding.activityAddGroupEtAccount.setOnClickListener(c ->{

            DialogCategoryBinding dialogAccountBinding = DialogCategoryBinding.inflate(getLayoutInflater());
            AlertDialog accountDialog = new AlertDialog.Builder(this).create();
            accountDialog.setView(dialogAccountBinding.getRoot());

            AccountAdapter accountAdapter = new AccountAdapter(this, new AccountAdapter.AccountsClickListener() {
                @Override
                public void onAccountSelected(Account account) {
                    binding.activityAddGroupEtAccount.setText(account.getNameAccount());

                    accountDialog.dismiss();
                }
            });

            accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

            accountViewModel.getAccount().observe(this, accounts1 -> {
                accountAdapter.setAccountList( accounts1);
            });

            dialogAccountBinding.dialogCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            dialogAccountBinding.dialogCategoryRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            dialogAccountBinding.dialogCategoryRecyclerView.setAdapter(accountAdapter);

            accountViewModel.refreshLiveData();

            accountDialog.show();

        });

        binding.activityAddGroupTvMemberName.setOnClickListener(c -> {
            DialogCategoryBinding dialogBinding = DialogCategoryBinding.inflate(getLayoutInflater());
            AlertDialog groupMemberDialog = new AlertDialog.Builder(this).create();
            groupMemberDialog.setView(dialogBinding.getRoot());

            GroupMemberAdapter groupMemberAdapter = new GroupMemberAdapter(this);

            groupMemberViewModel.getGroupMemberList().observe(this, groupMembers -> {
                groupMemberAdapter.setMembers(groupMembers);
            });

            dialogBinding.dialogCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            dialogBinding.dialogCategoryRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            dialogBinding.dialogCategoryRecyclerView.setAdapter(groupMemberAdapter);

            groupMemberViewModel.refreshLiveData();
            groupMemberDialog.show();

        });

        binding.activityAddGroupIvGroup.setOnClickListener(c -> {
            DialogCategoryBinding dialogBinding = DialogCategoryBinding.inflate(getLayoutInflater());
            AlertDialog groupMemberDialog = new AlertDialog.Builder(this).create();
            groupMemberDialog.setTitle("Select user into the group.");
            groupMemberDialog.setView(dialogBinding.getRoot());

            UserAdapter userAdapter = new UserAdapter(this, new UserAdapter.UserItemClicked() {
                @Override
                public void onUserSelected(User user) {

                    final ProgressDialog progressDialog = new ProgressDialog(AddGroupActivity.this);
                    progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    groupMemberViewModel.insertGroupMember(user.getUsername()).enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.body() != null) {
                                if (response.body().getIsSuccess().equals(Constants.successfully)) {
                                    StyleableToast.makeText(AddGroupActivity.this, "success", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    groupMemberViewModel.refreshAllUser();
                                } else {
                                    StyleableToast.makeText(AddGroupActivity.this, "fail", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            StyleableToast.makeText(AddGroupActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            groupMemberViewModel.getAllUser().observe(this, users -> {
                userAdapter.setUsers(users);
            });

            dialogBinding.dialogCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            dialogBinding.dialogCategoryRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            dialogBinding.dialogCategoryRecyclerView.setAdapter(userAdapter);

            groupMemberViewModel.refreshAllUser();
            groupMemberDialog.show();
        });

        binding.activityAddGroupBtnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataLocalManager.setCheckTypeTrans(true);
                binding.activityAddGroupBtnIncome.setTextColor(Color.WHITE);
                binding.activityAddGroupBtnExpense.setTextColor(getColor(R.color.red));

//                if(DataLocalManager.getCheckBudget() || DataLocalManager.getCheckUpdateBudget()){
//                    DataLocalManager.setTypeBudget(Constants.EXPENSE);
//
//                }else {
                    DataLocalManager.setTypeTransaction(Constants.EXPENSE);
//                }
            }
        });

        binding.activityAddGroupBtnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataLocalManager.setCheckTypeTrans(true);
                binding.activityAddGroupBtnIncome.setTextColor(getColor(R.color.green));
                binding.activityAddGroupBtnExpense.setTextColor(Color.WHITE);

                DataLocalManager.setTypeTransaction(Constants.INCOME);

            }
        });


        binding.activityAddGroupBtnSave.setOnClickListener(c -> {

            if (bundle.getString(getString(R.string.check_update)).equals(getString(R.string.update))){
                updateTransactionGroup();
            } else {
                insertTransactionGroup();
            }

        });


    }

    public void updateTransactionGroup(){
        if (!checkInput())
            return;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
        progressDialog.setCancelable(false);
        progressDialog.show();

        String typeTransaction = DataLocalManager.getTypeTransaction();
        Double amount = Double.parseDouble(binding.activityAddGroupEtAmount.getText().toString().trim());
        String category = binding.activityAddGroupEtCategory.getText().toString().trim();
        String date = (binding.activityAddGroupEtDate.getText().toString().trim());
        String account = binding.activityAddGroupEtAccount.getText().toString().trim();
        String note= binding.activityAddGroupEtNote.getText().toString().trim();

        Category category1 = new Category(category, DataLocalManager.getCategoryImage());

        transactionViewModel.updateGroupTransaction(DataLocalManager.getIdTransaction(), typeTransaction,
                amount , category1, date, account, note, DataLocalManager.getIdGroup()).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body() != null){
                    if (response.body().getIsSuccess().equals(Constants.successfully)){
                        StyleableToast.makeText(AddGroupActivity.this, getString(R.string.update_successfully),
                                Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();
                        finish();
                        transactionViewModel.refreshLiveData();
                    } else {
                        StyleableToast.makeText(AddGroupActivity.this, getString(R.string.update_unsuccessful),
                                Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                StyleableToast.makeText(AddGroupActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG, R.style.myToast).show();
            }
        });
    }

    public void insertTransactionGroup(){
        if (!checkInput())
            return;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
        progressDialog.setCancelable(false);
        progressDialog.show();

        String typeTransaction = DataLocalManager.getTypeTransaction();
        Double amount = Double.parseDouble(binding.activityAddGroupEtAmount.getText().toString().trim());
        String category = binding.activityAddGroupEtCategory.getText().toString().trim();
        String date = (binding.activityAddGroupEtDate.getText().toString().trim());
        String account = binding.activityAddGroupEtAccount.getText().toString().trim();
        String note= binding.activityAddGroupEtNote.getText().toString().trim();
        int idGroup = DataLocalManager.getIdGroup();

        Category categories = new Category(category, imageCat);

        transactionViewModel.insertTransactionGroup(DataLocalManager.getUsernameData(),typeTransaction, amount,
                categories, date, account, note, idGroup).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body() != null){
                    if (response.body().getIsSuccess().equals(Constants.successfully)){
                        StyleableToast.makeText(AddGroupActivity.this, getResources().getString(R.string.add_trans_successfully), Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();
                        finish();
//                        DataLocalManager.setCategoryName(category);
                        transactionViewModel.refreshLiveData();

                    } else {
                        StyleableToast.makeText(AddGroupActivity.this, getResources().getString(R.string.add_trans_unsuccessfully), Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();

                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }



    public boolean checkInput(){
        resetError();
        check = true;

        if (!DataLocalManager.getCheckTypeTrans()){
            Toast.makeText(this, getString(R.string.require), Toast.LENGTH_SHORT).show();
            check = false;
        }

        if (TextUtils.isEmpty(binding.activityAddGroupEtAmount.getText().toString().trim())){
            binding.activityAddGroupTilAmount.setError(getResources().getString(R.string.require));
            check = false;
        }

        if (TextUtils.isEmpty(binding.activityAddGroupEtCategory.getText().toString().trim())){
            binding.activityAddGroupTilCategory.setError(getResources().getString(R.string.require));
            check = false;
        }
        if (TextUtils.isEmpty(binding.activityAddGroupEtDate.getText().toString().trim())){
            binding.activityAddGroupTilDate.setError(getResources().getString(R.string.require));
            check = false;
        }

        if (TextUtils.isEmpty(binding.activityAddGroupEtAccount.getText().toString().trim())){
            binding.activityAddGroupTilAccount.setError(getResources().getString(R.string.require));
            check = false;
        }

        return check;
    }

    private void resetError(){
        binding.activityAddGroupTilAmount.setError(null);
        binding.activityAddGroupTilCategory.setError(null);
        binding.activityAddGroupTilDate.setError(null);
        binding.activityAddGroupTilAccount.setError(null);
    }
}