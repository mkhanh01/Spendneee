package com.example.spendnee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.spendnee.databinding.ActivityForgetPasswordBinding;
import com.example.spendnee.fragment.ForgetPasswordFragment;
import com.example.spendnee.R;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.viewmodel.UserViewModel;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {

    private ActivityForgetPasswordBinding binding;

    private UserViewModel viewModel;
    private int code;

//    private FirebaseAuth auth;
//    private static  final String TAG = "ForgetPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.activityForgetPasswordEtEmail.setText(DataLocalManager.getEmail());
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
//        FirebaseApp.initializeApp(this);
//        auth = FirebaseAuth.getInstance();
        init();
    }

    private void init(){
        binding.activityForgetPasswordBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callForgetPasswordFragment();
            }
        });

        binding.activityForgetPasswordTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.activityForgetPasswordBtnGetCode.setOnClickListener(v -> {
            resetError();
            if (checkEmail()){
                viewModel.checkEmail(binding.activityForgetPasswordEtEmail.getText().toString().trim())
                        .enqueue(new Callback<BaseResponse>() {
                            @Override
                            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                BaseResponse baseResponse = response.body();
                                if (baseResponse != null){
                                    if(baseResponse.getIsSuccess().equals(Constants.failed)){
                                        binding.activityForgetPasswordTilEmail.setError(getResources().getString(R.string.email_not_match));
                                    }else {
                                        sendEmail();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<BaseResponse> call, Throwable t) {

                            }
                        });
            }

        });
    }

    public void callForgetPasswordFragment() {
        if (!checkInput())
            return;

        ForgetPasswordFragment forgetPasswordFragment = new ForgetPasswordFragment();
        binding.activityForgetPasswordLinearLayout.setVisibility(View.GONE);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_forget_password_frame_layout, forgetPasswordFragment);
        transaction.commit();

    }

    private void sendEmail(){
        resetError();
        Random random = new Random();
        code = random.nextInt(8999)+1000;

        String stringHost = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", stringHost);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("imiusg@gmail.com",
                        getResources().getString(R.string.password_email_server));
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    binding.activityForgetPasswordEtEmail.getText().toString().trim()));

            mimeMessage.setSubject(getResources().getString(R.string.subject_email));
            mimeMessage.setText(getResources().getString(R.string.hello_email)+ "\n\n" +
                    getResources().getString(R.string.content_email) +  code + "\n\n" +
                    getResources().getString(R.string.end_email));

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //Transport.send(mimeMessage);
                        javax.mail.Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            thread.start();

            StyleableToast.makeText(ForgetPasswordActivity.this,
                    getResources().getString(R.string.check_code),
                    Toast.LENGTH_LONG, R.style.myToast).show();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
//    private void forgetPassword(){
//        auth.sendPasswordResetEmail(binding.activityForgetPasswordEtEmail.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(ForgetPasswordActivity.this, getResources().getString(R.string.please_check_email), Toast.LENGTH_SHORT).show();
//
//                    callLoginActivity();
//                } else {
//                    try {
//                        throw task.getException();
//                    } catch (FirebaseAuthInvalidUserException e){
//                        binding.activityForgetPasswordTilEmail.setError(getResources().getString(R.string.not_exist_email));
//                        binding.activityForgetPasswordTilEmail.requestFocus();
//                    } catch (Exception e){
//                        Log.e(TAG, e.getMessage());
//                        Toast.makeText(ForgetPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//        });
//
//    }

    private void callLoginActivity() {
        Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean checkEmail(){
        binding.activityForgetPasswordTilEmail.setError(null);

        if (TextUtils.isEmpty(binding.activityForgetPasswordEtEmail.getText().toString())){
            binding.activityForgetPasswordTilEmail.setError(getResources().getString(R.string.require));
            return false;
        } else if (!Pattern.matches(getResources().getString(R.string.email_pattern),
                binding.activityForgetPasswordEtEmail.getText().toString().trim())) {
            binding.activityForgetPasswordTilEmail.setError(getResources().getString(R.string.format_error));
            return false;
        }
        return true;
    }
    private boolean checkInput(){
        resetError();

        if (TextUtils.isEmpty(binding.activityForgetPasswordEtEmail.getText().toString().trim())){
            binding.activityForgetPasswordTilEmail.setError(getResources().getString(R.string.require));
            return false;
        } else if (!Pattern.matches(getResources().getString(R.string.email_pattern),
                binding.activityForgetPasswordEtEmail.getText().toString().trim())) {
            binding.activityForgetPasswordTilEmail.setError(getResources().getString(R.string.format_error));
            return false;
        } else if (binding.activityForgetPasswordEtEmail.getText().toString().trim().equals(DataLocalManager.getEmail())){
            binding.activityForgetPasswordTilEmail.setError(getResources().getString(R.string.email_not_match));
        }

        if (TextUtils.isEmpty(binding.activityForgetPasswordEtCode.getText().toString().trim())){
            binding.activityForgetPasswordTilCode.setError(getResources().getString(R.string.require));
            return false;
        }else{
            try {
                if(Integer.parseInt(binding.activityForgetPasswordEtCode.getText().toString().trim()) != code) {
                    binding.activityForgetPasswordTilCode.setError(getResources().getString(R.string.code_error));
                    return false;
                }
            }catch (Exception e){
                binding.activityForgetPasswordTilCode.setError(getResources().getString(R.string.number_error));
                return false;
            }
        }
        return true;
    }

    private void resetError() {
        binding.activityForgetPasswordTilEmail.setError(null);
        binding.activityForgetPasswordTilCode.setError(null);
    }
}