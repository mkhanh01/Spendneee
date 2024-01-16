package com.example.spendnee.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spendnee.R;
import com.example.spendnee.adapter.TransactionAdapter;
import com.example.spendnee.adapter.ViewPagerAdapter;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.FragmentTransactionBookBinding;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Transaction;
import com.example.spendnee.repository.TransactionRepository;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.utils.Helper;
import com.example.spendnee.viewmodel.TransactionViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionBookFragment extends Fragment {
    private FragmentTransactionBookBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTransactionBookBinding.inflate(inflater, container, false);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getParentFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewpager.setAdapter(viewPagerAdapter);
        binding.fragmentTransTabLayout.setupWithViewPager(binding.viewpager);


        return binding.getRoot();
    }


}