package com.example.spendnee.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.spendnee.R;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.ActivityHomeBinding;
import com.example.spendnee.fragment.AccountFragment;
import com.example.spendnee.fragment.BottomAddFragment;
import com.example.spendnee.fragment.BudgetFragment;
import com.example.spendnee.fragment.HomeFragment;
import com.example.spendnee.fragment.TransactionBookFragment;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DataLocalManager.setCheckFromLogout(false);
        calendar = Calendar.getInstance();
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.transaction_book:
                    replaceFragment(new TransactionBookFragment());
                    break;
                case R.id.budget:
                    replaceFragment(new BudgetFragment());
                    break;
                case R.id.account:
                    replaceFragment(new AccountFragment());
                    break;
            }

            return  true;
        });

        binding.activityHomeFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (DataLocalManager.getCheckLogin()){
                    showBottomDialog();
                } else {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_home_frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog(){
        new BottomAddFragment().show(getSupportFragmentManager(), null);
        DataLocalManager.setCheckUpdateTransaction(false);
        DataLocalManager.setCheckUpdateBudget(false);
        DataLocalManager.setCheckBudget(false);
    }
}