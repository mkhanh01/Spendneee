package com.example.spendnee.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spendnee.R;
import com.example.spendnee.activity.HomeActivity;
import com.example.spendnee.activity.LoginActivity;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.FragmentPaybookBinding;

public class PaybookFragment extends Fragment {

    private FragmentPaybookBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPaybookBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        init();


        return view;
    }

    private void init() {
        binding.fragmentPaybookTvBack.setOnClickListener(c->{
            callPaybookFragment();
        });

        binding.fragmentPaybookFabAddPaybook.setOnClickListener( c->{
            if (DataLocalManager.getCheckLogin()){
                showBottomDialog();
            } else {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showBottomDialog(){
        new BottomAddFragment().show(getParentFragmentManager(), null);
    }

    public void callPaybookFragment(){

        AccountFragment accountFragment = new AccountFragment();
        binding.fragmentPaybookLinear2.setVisibility(View.GONE);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_paybook_linear1, accountFragment);

        transaction.commit();

    }
}