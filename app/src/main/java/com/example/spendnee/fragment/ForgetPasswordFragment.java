package com.example.spendnee.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spendnee.R;
import com.example.spendnee.activity.LoginActivity;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.FragmentForgetPasswordBinding;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.User;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.viewmodel.UserViewModel;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordFragment extends Fragment {

    private FragmentForgetPasswordBinding binding;
    private UserViewModel viewModel;

    public static ForgetPasswordFragment newInstance() {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);

        init();

        return view;
    }

    private void init(){
        binding.fragmentForgetPasswordTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        binding.fragmentForgetPasswordBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgetPassword();
            }
        });
    }

    private void forgetPassword(){

        if (!checkInput()){
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
        progressDialog.setMessage(getResources().getString(R.string.progressbar_login));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final User user = new User(DataLocalManager.getUsernameData(),
                binding.fragmentForgetPasswordEtConfirmNewPassword.getText().toString());

        viewModel.updatePassword(user).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                if (baseResponse != null){
                    if (baseResponse.getIsSuccess().equals(Constants.successfully)){

                        DataLocalManager.setPassword(user.getPassword());

                        //                 callProfileFragment();

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);

                        StyleableToast.makeText(getContext(), getString(R.string.update_successfully),
                                Toast.LENGTH_LONG, R.style.myToast).show();
                        progressDialog.dismiss();

                    } else {
                        StyleableToast.makeText(getContext(), getString(R.string.update_unsuccessfully),
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

    private boolean checkInput(){
        resetError();
        if (TextUtils.isEmpty(binding.fragmentForgetPasswordEtNewPassword.getText().toString().trim())){
            binding.fragmentForgetPasswordTilNewPassword.setError(getResources().getString(R.string.require));
            return false;
        }

        if (TextUtils.isEmpty(binding.fragmentForgetPasswordEtConfirmNewPassword.getText().toString().trim())){
            binding.fragmentForgetPasswordTilConfirmNewPassword.setError(getResources().getString(R.string.require));
            return false;
        } else {
            if (! binding.fragmentForgetPasswordEtConfirmNewPassword.getText().toString().trim().equals(binding.fragmentForgetPasswordEtNewPassword.getText().toString().trim())){
                binding.fragmentForgetPasswordTilConfirmNewPassword.setError(getResources().getString(R.string.compare_password_require));
                return false;
            }
        }

        return true;
    }

    private void resetError(){
        binding.fragmentForgetPasswordTilNewPassword.setError(null);
        binding.fragmentForgetPasswordTilConfirmNewPassword.setError(null);
    }
}