package com.example.spendnee.activity;

 import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
 import androidx.lifecycle.ViewModelProvider;

 import android.app.ProgressDialog;
 import android.content.Intent;
import android.os.Bundle;
 import android.provider.ContactsContract;
 import android.text.TextUtils;
 import android.util.Log;
 import android.view.View;
 import android.widget.Toast;

 import com.example.spendnee.R;
 import com.example.spendnee.data.DataLocalManager;
 import com.example.spendnee.databinding.ActivitySignupBinding;
 import com.example.spendnee.model.BaseResponse;
 import com.example.spendnee.model.User;
 import com.example.spendnee.utils.Constants;
 import com.example.spendnee.viewmodel.UserViewModel;
// import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
// import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
// import com.google.firebase.auth.FirebaseAuthUserCollisionException;
// import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
// import com.google.firebase.auth.FirebaseUser;
// import com.google.firebase.auth.SignInMethodQueryResult;
// import com.google.firebase.database.DatabaseReference;
// import com.google.firebase.database.FirebaseDatabase;
// import com.google.firebase.database.core.UserWriteRecord;

 import java.util.Properties;
 import java.util.Random;
 import java.util.regex.Pattern;

 import javax.mail.Authenticator;
 import javax.mail.Message;
 import javax.mail.MessagingException;
 import javax.mail.PasswordAuthentication;
 import javax.mail.Session;
 import javax.mail.Transport;
 import javax.mail.internet.AddressException;
 import javax.mail.internet.InternetAddress;
 import javax.mail.internet.MimeMessage;

 import io.github.muddz.styleabletoast.StyleableToast;
 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private UserViewModel userViewModel;
    private boolean check = true;
    private int code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view  = binding.getRoot();
        setContentView(view);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        init();

    }

    private void init(){
        binding.activitySignupBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInput())
                    return;
                signup();

            }
        });

        binding.activitySignupBtnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmail()){
                    userViewModel.checkEmail(binding.activitySignupEtEmail.getText().toString().trim()).enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            BaseResponse baseResponse = response.body();
                            if (baseResponse != null){
                                if(baseResponse.getIsSuccess().equals(Constants.failed)){
                                    sendEmail();
                                }else {
                                    binding.activitySignupTilEmail.setError(getResources().getString(R.string.exist_email));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        binding.activitySignupTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.activitySignupTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void signup(){
        if (!checkInput()){
            return;
        }

        if (!checkUsername()){
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle(getResources().getString(R.string.progressbar_tittle));
        progressDialog.setMessage(getResources().getString(R.string.progressbar_signup));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final User user = new User(binding.activitySignupEtUsername.getText().toString().trim(),
                binding.activitySignupEtUsername.getText().toString().trim(),
                binding.activitySignupEtPassword.getText().toString().trim(),
                binding.activitySignupEtEmail.getText().toString().trim(),
                binding.activitySignupEtConfirmPassword.getText().toString().trim());

        userViewModel.signup(user).enqueue(new Callback<BaseResponse>() {

            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                BaseResponse baseResponse = response.body();
                if (baseResponse != null){
                    if (baseResponse.getIsSuccess().equals(Constants.failed)){
                        binding.activitySignupTilEmail.setError(getResources().getString(R.string.exist_email));
                    } else {
                        StyleableToast.makeText(SignUpActivity.this,
                                getResources().getString(R.string.signup_successfully),
                                Toast.LENGTH_LONG, R.style.myToast).show();

                        DataLocalManager.setUsernameData(binding.activitySignupEtUsername.getText().toString().trim());
                        DataLocalManager.setEmail(binding.activitySignupEtEmail.getText().toString().trim());
                        DataLocalManager.setPassword(binding.activitySignupEtPassword.getText().toString());
//                        DataLocalManager.setCheckEdit(true);
                        callLoginActivity();
                    }

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }
    private void callLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }

    private boolean checkUsername (){

        userViewModel.checkUsername(binding.activitySignupEtUsername.getText().toString().trim())
                .enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.body().getIsSuccess().equals(Constants.successfully)){
                            StyleableToast.makeText(SignUpActivity.this,
                                    getResources().getString(R.string.username_exist),
                                    Toast.LENGTH_LONG, R.style.myToast).show();
                            binding.activitySignupTilUsername.setError(getString(R.string.username_exist));
                            check = false;
                        }else {
                            check = true;
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

                    }
                });

        return check;
    }

    public boolean checkEmail(){
        binding.activitySignupTilEmail.setError(null);

        if (TextUtils.isEmpty(binding.activitySignupEtEmail.getText().toString())){
            binding.activitySignupTilEmail.setError(getResources().getString(R.string.require));
            return false;
        } else if (!Pattern.matches(getResources().getString(R.string.email_pattern),
                binding.activitySignupEtEmail.getText().toString().trim())) {
            binding.activitySignupTilEmail.setError(getResources().getString(R.string.format_error));
            return false;
        }
        return true;
    }

    public boolean checkInput(){
        resetError();
        check = true;

        if (TextUtils.isEmpty(binding.activitySignupEtUsername.getText().toString().trim())){
            binding.activitySignupTilUsername.setError(getResources().getString(R.string.require));
            check = false;
        }else {
            if (!Pattern.matches(getString(R.string.special_character),
                    binding.activitySignupEtUsername.getText().toString().trim())){
                binding.activitySignupTilUsername.setError(getResources().getString(R.string.username_special_character));
                check = false;
            }
        }

        if (TextUtils.isEmpty(binding.activitySignupEtEmail.getText().toString().trim())){
            binding.activitySignupTilEmail.setError(getResources().getString(R.string.require));
            check = false;
        }else {
            if (!Pattern.matches(getResources().getString(R.string.email_pattern),
                    binding.activitySignupEtEmail.getText().toString().trim())) {
                binding.activitySignupTilEmail.setError(getResources().getString(R.string.format_error));
                check = false;
            }
        }

        if (TextUtils.isEmpty(binding.activitySignupEtPassword.getText().toString().trim())){
            binding.activitySignupTilPassword.setError(getResources().getString(R.string.require));
            check = false;
        }

        if (binding.activitySignupEtPassword.getText().toString().trim().length() < 6){
            binding.activitySignupTilPassword.setError(getResources().getString(R.string.format_password));
            check = false;
        }

        if (TextUtils.isEmpty(binding.activitySignupEtConfirmPassword.getText().toString().trim())) {
            binding.activitySignupTilConfirmPassword.setError(getResources().getString(R.string.require));
            check = false;
        }else {
            if (!binding.activitySignupEtPassword.getText().toString().trim().equals(
                    binding.activitySignupEtConfirmPassword.getText().toString().trim())){
                binding.activitySignupTilConfirmPassword.setError(getResources().getString(R.string.compare_password_require));
                check = false;
            }
        }

        if (TextUtils.isEmpty(binding.activitySignupEtCode.getText().toString().trim())){
            binding.activitySignupTilCode.setError(getResources().getString(R.string.require));
            check = false;
        }else {
            try {
                if(Integer.parseInt(binding.activitySignupEtCode.getText().toString().trim()) != code) {
                    binding.activitySignupTilCode.setError(getResources().getString(R.string.code_error));
                    check = false;
                }
            }catch (Exception e){
                binding.activitySignupTilCode.setError(getResources().getString(R.string.number_error));
                check = false;
            }
        }
        return check;
    }

    private void sendEmail (){
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
                return new PasswordAuthentication(getResources().getString(R.string.email_server),
                        getResources().getString(R.string.password_email_server));
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);

        try {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    binding.activitySignupEtEmail.getText().toString().trim()));

            mimeMessage.setSubject(getResources().getString(R.string.subject_email));
            mimeMessage.setText(getResources().getString(R.string.hello_email)+ "\n\n" +
                    getResources().getString(R.string.content_email) +  code + "\n\n" +
                    getResources().getString(R.string.end_email));

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

            StyleableToast.makeText(SignUpActivity.this,
                    getResources().getString(R.string.check_code),
                    Toast.LENGTH_LONG, R.style.myToast).show();
        } catch (AddressException e){
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void resetError(){
        binding.activitySignupTilUsername.setError(null);
        binding.activitySignupTilEmail.setError(null);
        binding.activitySignupTilPassword.setError(null);
        binding.activitySignupTilConfirmPassword.setError(null);
        binding.activitySignupTilCode.setError(null);
    }
}