package com.example.spendnee.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spendnee.R;
import com.example.spendnee.activity.LoginActivity;
import com.example.spendnee.activity.SignUpActivity;
import com.example.spendnee.activity.UploadPictureActivity;
import com.example.spendnee.activity.WelcomeActivity;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.FragmentAccountBinding;
import com.example.spendnee.model.User;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
//    private FirebaseAuth auth;
    private String username, email;

    public static AccountFragment newInstance(){
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.fragmentAccountTvName.setText(DataLocalManager.getUsernameData());
//        binding.fragmentAccountTvEmail.setText(DataLocalManager.getEmail());

        binding.fragmentAccountTvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), DataLocalManager.getEmail(), Toast.LENGTH_SHORT).show();
            }
        });

        init();

        return view;
    }

    private void init(){

        binding.fragmentAccountTvName.setText(DataLocalManager.getNameData());
        if (DataLocalManager.getNameData().equals("")){
            binding.fragmentAccountTvName.setText(DataLocalManager.getUsernameData());
        } else {
            binding.fragmentAccountTvName.setText(DataLocalManager.getNameData());
        }

        if (!DataLocalManager.getCheckLogin()){
            binding.fragmentAccountBtnLogout.setText(getString(R.string.login));
            binding.fragmentAccountTvName.setVisibility(View.GONE);
        }

        binding.fragmentAccountCivAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callUploadPictureActivity();
            }
        });

        binding.fragmentAccountIvChooseAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callUploadPictureActivity();
            }
        });

        binding.fragmentAccountRlPaybook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPaybookFragment();
            }
        });

        binding.fragmentAccountBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!DataLocalManager.getCheckLogin()){
                    callLoginActivity();
                    getActivity().finish();
                }else {
                    showDialogLogout();
                }
            }
        });
    }

    private void callLoginActivity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void callUploadPictureActivity(){
        Intent intent = new Intent(getActivity(), UploadPictureActivity.class);
        startActivity(intent);
    }

    private void showDialogLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getString(R.string.title_dialog));
        builder.setMessage(getString(R.string.dialog_logout));
        builder.setCancelable(false);

        builder.setPositiveButton(getString(R.string.yes_dialog),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        DataLocalManager.clearDataLocal();
                        DataLocalManager.setFirstInstall(true);
                        DataLocalManager.setCheckFromLogout(true);
                        getActivity().finish();
                        startActivity(intent);
                    }
                });

        builder.setNegativeButton(getString(R.string.no_dialog),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void callPaybookFragment(){

        PaybookFragment paybookFragment = new PaybookFragment();
        binding.fragmentAccountLinearLayout2.setVisibility(View.GONE);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_account_linear_layout1, paybookFragment);

        transaction.commit();

    }

}