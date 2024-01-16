package com.example.spendnee.fragment;

import static android.graphics.Color.TRANSPARENT;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.spendnee.R;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.DialogAddGroupBinding;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Group;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.viewmodel.GroupMemberViewModel;
import com.example.spendnee.viewmodel.GroupViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogAddGroup extends DialogFragment{
    private DialogAddGroupBinding binding;
    private GroupViewModel viewModel;
    private GroupMemberViewModel memberViewModel;

    public static DialogAddGroup newInstance(){
        return new DialogAddGroup();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DialogAddGroupBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        init();

        return view;
    }

    private void init(){
        viewModel = new ViewModelProvider(getActivity()).get(GroupViewModel.class);
        memberViewModel = new ViewModelProvider(getActivity()).get(GroupMemberViewModel.class);

        if (getArguments().getString(getString(R.string.check_update)).equals(getString(R.string.update))){
            binding.dialogAddGroupTvContent.setText(getString(R.string.enter_new_name_for_your_group));
            binding.dialogAddGroupEtNameGroup.setText(getArguments().getString(getString(R.string.nameGroup)));
            binding.dialogAddGroupBtnSave.setText(getString(R.string.update));
        }

        binding.dialogAddGroupIvCancel.setOnClickListener(c-> {
            dismiss();
        });

        binding.dialogAddGroupBtnSave.setOnClickListener(c -> {
            if (checkInput()){
                if (getArguments().getString(getString(R.string.check_update)).equals(getString(R.string.update))){
                    updateNameGroup();
                } else {
                    insertGroup();
//                    addUserMember();
                }

            } else {
                return;
            }
        });

    }

    private boolean checkInput(){
        List<Group> list = new ArrayList<>();

        binding.dialogAddGroupTilNameGroup.setError(null);
        if (binding.dialogAddGroupEtNameGroup.getText().toString().trim().isEmpty()){
            binding.dialogAddGroupTilNameGroup.setError(getString(R.string.require));
            return false;
        }

        viewModel.getGroupList().observe(getViewLifecycleOwner(), groups -> {
            list.addAll(groups);
        });

        for (Group group: list) {
            if (binding.dialogAddGroupEtNameGroup.getText().toString()
                    .equals(group.getNameGroup())){
                binding.dialogAddGroupTilNameGroup.setError(getString(R.string.exists));
                return false;
            }
        }
        return true;
    }

    private void insertGroup(){
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
        progressDialog.setCancelable(false);
        progressDialog.show();

        viewModel.insertGroup(binding.dialogAddGroupEtNameGroup.getText().toString().trim())
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.body() != null){
                            if (response.body().getIsSuccess().equals(Constants.successfully)){
                                StyleableToast.makeText(getContext(), "success", Toast.LENGTH_LONG).show();

                                progressDialog.dismiss();
                                getDialog().dismiss();
                                viewModel.refreshLiveData();
                            } else {
                                StyleableToast.makeText(getContext(), "fail", Toast.LENGTH_LONG).show();
                                binding.dialogAddGroupTilNameGroup.setError("");
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

    private void addUserMember(){
        memberViewModel.insertGroupMember(DataLocalManager.getUsernameData()).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body() != null) {
                    if (response.body().getIsSuccess().equals(Constants.successfully)) {
//                        StyleableToast.makeText(getContext(), "name user", Toast.LENGTH_LONG).show();
                        memberViewModel.refreshAllUser();
                    } else {
//                        StyleableToast.makeText(getContext(), "fail name user", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                StyleableToast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateNameGroup(){
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
        progressDialog.setCancelable(false);
        progressDialog.show();

        viewModel.updateGroupName(getArguments().getString(getString(R.string.nameGroup)),
                binding.dialogAddGroupEtNameGroup.getText().toString().trim()).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if(response.body() != null){
                    if (response.body().getIsSuccess().equals(Constants.successfully)){
                        StyleableToast.makeText(getContext(), getString(R.string.update_successfully),
                                Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();
                        getDialog().dismiss();
                        viewModel.refreshLiveData();


                    }else{
                        StyleableToast.makeText(getContext(), getString(R.string.update_unsuccessful),
                                Toast.LENGTH_LONG, R.style.myToast).show();
                        binding.dialogAddGroupTilNameGroup.setError(" ");
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

    @Override
    public void onResume() {
        super.onResume();
        //   loadData();
        viewModel.refreshLiveData();

        ViewGroup.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) layoutParams);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(TRANSPARENT));

    }

}
