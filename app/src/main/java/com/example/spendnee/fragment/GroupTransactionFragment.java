package com.example.spendnee.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spendnee.R;
import com.example.spendnee.activity.AddGroupActivity;
import com.example.spendnee.adapter.TransactionAdapter;
import com.example.spendnee.adapter.UserAdapter;
import com.example.spendnee.api.API;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.DialogCategoryBinding;
import com.example.spendnee.databinding.FragmentGroupTransactionBinding;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Transaction;
import com.example.spendnee.model.User;
import com.example.spendnee.service.DataService;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.viewmodel.GroupMemberViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GroupTransactionFragment extends Fragment {
    private FragmentGroupTransactionBinding binding;
    private GroupMemberViewModel groupMemberViewModel;
    private TransactionAdapter transactionAdapter;
    String sumIn, sumEx, sumTo;
    Double sumIncome = 0.0;
    Double sumExpense = 0.0;
    Double sumTotal = 0.0;

    private DataService dataService = API.getAccount().create(DataService.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGroupTransactionBinding.inflate(inflater, container, false);
        setAdapter();
        init();
        DataLocalManager.setCheckUpdateTransaction(false);

        return binding.getRoot();
    }



    private void setAdapter(){
        binding.fragmentAddGroupRvListTrans.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        transactionAdapter = new TransactionAdapter(getContext(), new TransactionAdapter.TransactionClickListener() {
            @Override
            public void onTransactionClicked(Transaction transaction) {
                if (getArguments().getString(getString(R.string.member)).equals("creator")){
                    callUpdateGroupActivity(getString(R.string.update),transaction.getIdGroup());
                    DataLocalManager.setIdTransaction(transaction.getIdTransaction());
                    DataLocalManager.setTypeTransaction(transaction.getTypeTransaction());
                    DataLocalManager.setAmount(String.valueOf(transaction.getAmount()));
                    DataLocalManager.setCategoryName(transaction.getCategoryName());
                    DataLocalManager.setCategoryImage(transaction.getCategoryImage());
                    DataLocalManager.setAccount(transaction.getAccount());
                    DataLocalManager.setDate(transaction.getDate());
                    DataLocalManager.setNote(transaction.getNote());

                    DataLocalManager.setCheckUpdateTransaction(true);
                }else if (getArguments().getString(getString(R.string.member)).equals("member")){

                }
            }
        });
        binding.fragmentAddGroupRvListTrans.setAdapter(transactionAdapter);

        if (getArguments().getString(getString(R.string.nameGroup)) != null){
            Picasso.get().load(getArguments().getString("categoryImage")).into(binding.fragmentGroupTransactionIvImageGroup);
            binding.fragmentGroupTransactionTvGroupName.setText(getArguments().getString("nameGroup"));

            dataService.getTransactionGroup(getArguments().getInt("idGroup")).enqueue(new Callback<List<Transaction>>() {
                @Override
                public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                    if (response.body() != null){
                        transactionAdapter.setTransactionList(response.body());

                        for (int i = 0; i < transactionAdapter.getItemCount(); i++) {
                            if (transactionAdapter.getTransactionItems().get(i).getTypeTransaction().equals(Constants.INCOME)){
                                sumIncome = sumIncome + transactionAdapter.getTransactionItems().get(i).getAmount();
                                sumIn = String.valueOf(sumIncome);
//                                DataLocalManager.setGroupIncome(String.valueOf(sumIncome));
                                binding.fragmentGroupTransactionTvIncome.setText(sumIn);

                                sumTotal = sumIncome - sumExpense;
                                sumTo = String.valueOf(sumTotal);
//                                DataLocalManager.setGroupTotal(sumTo);
                                binding.fragmentGroupTransactionTvTotal.setText(sumTo);
                            } else if (transactionAdapter.getTransactionItems().get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                                sumExpense = sumExpense + transactionAdapter.getTransactionItems().get(i).getAmount();
                                sumEx = String.valueOf(sumExpense);

                                binding.fragmentGroupTransactionTvExpense.setText(sumEx);

                                sumTotal = sumIncome - sumExpense;
                                sumTo = String.valueOf(sumTotal);
                                binding.fragmentGroupTransactionTvTotal.setText(sumTo);
                            }
                        }


                    } else {
                        StyleableToast.makeText(getContext(), "null",
                                Toast.LENGTH_LONG, R.style.myToast).show();
                    }

                    sumIncome = 0.0;
                    sumExpense = 0.0;
                }

                @Override
                public void onFailure(Call<List<Transaction>> call, Throwable t) {

                }


            });

            if (getArguments().getString(getString(R.string.member)).equals("creator")){
                deleteTransactionGroup(getArguments().getInt("idGroup"));
            }else if (getArguments().getString(getString(R.string.member)).equals("member")){

            }



        }
    }



    private void init(){
        groupMemberViewModel = new ViewModelProvider(this).get(GroupMemberViewModel.class);

        if (getArguments().getString(getString(R.string.member)).equals("member")){
            binding.fragmentGroupTransactionBtnAddMember.setVisibility(View.GONE);
            binding.fragmentGroupTransactionBtnAddTran.setVisibility(View.GONE);
        }else if (getArguments().getString(getString(R.string.member)).equals("creator")){
            binding.fragmentGroupTransactionBtnAddMember.setOnClickListener(c -> {
                DialogCategoryBinding dialogBinding = DialogCategoryBinding.inflate(getLayoutInflater());
                AlertDialog groupMemberDialog = new AlertDialog.Builder(getContext()).create();
                groupMemberDialog.setTitle("Select user into the group.");
                groupMemberDialog.setView(dialogBinding.getRoot());

                UserAdapter userAdapter = new UserAdapter(getContext(), new UserAdapter.UserItemClicked() {
                    @Override
                    public void onUserSelected(User user) {

                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        groupMemberViewModel.insertGroupMember(user.getUsername()).enqueue(new Callback<BaseResponse>() {
                            @Override
                            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                if (response.body() != null) {
                                    if (response.body().getIsSuccess().equals(Constants.successfully)) {
                                        StyleableToast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                        groupMemberViewModel.refreshAllUser();
                                    } else {
                                        StyleableToast.makeText(getContext(), "fail", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<BaseResponse> call, Throwable t) {
                                StyleableToast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            }

                        });
                    }

                });

                groupMemberViewModel.getAllUser().observe(getViewLifecycleOwner(), users -> {
                    userAdapter.setUsers(users);
                });

                dialogBinding.dialogCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                dialogBinding.dialogCategoryRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                dialogBinding.dialogCategoryRecyclerView.setAdapter(userAdapter);

                groupMemberViewModel.refreshAllUser();
                groupMemberDialog.show();
            });
            binding.fragmentGroupTransactionBtnAddTran.setOnClickListener(c ->{
                callAddGroupActivity();
            });
        }

        binding.fragmentGroupTransactionTvCancel.setOnClickListener(c -> {

            if (getArguments().getString(getString(R.string.member)).equals("creator")){
                callCreatorFragment();
            }else if (getArguments().getString(getString(R.string.member)).equals("member")){
                callMemberFragment();
            }

        });


    }

    private void deleteTransactionGroup(int idGroup){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                List<Transaction> transactionList = transactionAdapter.getTransactionItems();
                Transaction transaction = transactionList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.title_dialog));
                builder.setMessage(getString(R.string.delete_message));
                builder.setCancelable(false);

                builder.setPositiveButton(getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataService.deleteTransactionGroup(transaction.getIdTransaction(), idGroup).enqueue(new Callback<BaseResponse>() {
                            @Override
                            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                if (response.body().getIsSuccess().equals(Constants.successfully)) {
                                    StyleableToast.makeText(getContext(),
                                            getString(R.string.deleted_successfully),
                                            Toast.LENGTH_LONG, R.style.myToast).show();
                                    getAllTransactionGroup(idGroup);
//                                    setArguments();

                                } else {
                                    StyleableToast.makeText(getContext(),
                                            getString(R.string.deleted_unsuccessfully),
                                            Toast.LENGTH_LONG, R.style.myToast).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<BaseResponse> call, Throwable t) {

                            }
                        });
                    }
                });

                builder.setNegativeButton(getString(R.string.no_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getAllTransactionGroup(idGroup);
                        dialogInterface.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }).attachToRecyclerView(binding.fragmentAddGroupRvListTrans);
    }

    private void getAllTransactionGroup(int idGroup){
        dataService.getTransactionGroup(idGroup).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.body() != null){
                    transactionAdapter.setTransactionList(response.body());
                } else {
                    StyleableToast.makeText(getContext(), "null",
                            Toast.LENGTH_LONG, R.style.myToast).show();
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });
    }

    private void callAddGroupActivity(){
        Intent intent = new Intent(getContext(), AddGroupActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.check_update),getString(R.string.insert));

        intent.putExtras(bundle);
        getContext().startActivity(intent);
    }

    private void callUpdateGroupActivity(String str, int idGroup){
//        DataLocalManager.setIdGroup(group.getIdGroup());
//
        Intent intent = new Intent(getContext(), AddGroupActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.check_update),str);
        bundle.putInt("idGroup", idGroup);

        intent.putExtras(bundle);
        getContext().startActivity(intent);
    }

    private void callCreatorFragment(){
        CreatorFragment creatorFragment = new CreatorFragment();

        binding.fragmentGroupTransactionFrame2.setVisibility(View.GONE);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_creator_frame1, creatorFragment);

        transaction.commit();

    }

    private void callMemberFragment(){
        MemberFragment memberFragment = new MemberFragment();

        binding.fragmentGroupTransactionFrame2.setVisibility(View.GONE);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_member_frame1, memberFragment);

        transaction.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }
}