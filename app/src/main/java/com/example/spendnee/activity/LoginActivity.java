package com.example.spendnee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.spendnee.R;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.ActivityLoginBinding;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.User;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.viewmodel.UserViewModel;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//import com.google.firebase.auth.FirebaseAuthInvalidUserException;
//import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (!DataLocalManager.getCheckLogin()){
            binding.activityLoginTvBack.setVisibility(View.GONE);
        }

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        init();
    }

    private void init(){
        binding.activityLoginEtUsername.setText(DataLocalManager.getUsernameData());

        binding.activityLoginBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInput()){
                    return;
                }
                login();
            }
        });

        binding.activityLoginTvNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        binding.activityLoginTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.activityLoginTvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        binding.activityLoginTvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkInput (){
        resetError();

        boolean check = true;
        if (binding.activityLoginEtUsername.getText().toString().trim().isEmpty()){
            binding.activityLoginTilUsername.setError(getResources().getString(R.string.require));
            check = false;
        }else {
            if (!Pattern.matches(getString(R.string.special_character),
                    binding.activityLoginEtUsername.getText().toString().trim())){
                binding.activityLoginTilUsername.setError(getResources().getString(R.string.username_special_character));
                check = false;
            }
        }

        if (binding.activityLoginEtPassword.getText().toString().trim().isEmpty()){
            binding.activityLoginTilPassword.setError(getResources().getString(R.string.require));
            check = false;
        }

        return check;
    }

    private void login (){



        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
        progressDialog.setMessage(getResources().getString(R.string.progressbar_login));
        progressDialog.setCancelable(false);
        progressDialog.show();


        final User user = new User(binding.activityLoginEtUsername.getText().toString().trim(),
                binding.activityLoginEtPassword.getText().toString().trim());

        userViewModel.login(user).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, retrofit2.Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                if (baseResponse != null){
                    if (baseResponse.getIsSuccess().equals(Constants.successfully)){
                        StyleableToast.makeText(LoginActivity.this, getResources().
                                        getString(R.string.login_successfully),
                                Toast.LENGTH_LONG, R.style.myToast).show();
                        DataLocalManager.setUsernameData(user.getUsername());
                        DataLocalManager.setEmail(user.getEmail());
                        DataLocalManager.setPassword(user.getPassword());
                        DataLocalManager.setNameData(response.body().getUser().getName());
                        DataLocalManager.setCheckLogin(true);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);


                        //   callProfileFragment();
                        //    callChangePasswordFragment();
                        progressDialog.dismiss();
                    }else {

                        StyleableToast.makeText(LoginActivity.this, getResources().getString
                                        (R.string.login_unsuccessful), Toast.LENGTH_LONG,
                                R.style.myToast).show();

                        progressDialog.dismiss();

                        binding.activityLoginTilUsername.setError(" ");
                        binding.activityLoginTilPassword.setError(" ");

                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                StyleableToast.makeText(LoginActivity.this, "t.getMessage()",
                        Toast.LENGTH_LONG, R.style.myToast).show();
                progressDialog.dismiss();
            }
        });
    }

    private void resetError() {
        binding.activityLoginTilUsername.setError(null);
        binding.activityLoginTilPassword.setError(null);
    }
}