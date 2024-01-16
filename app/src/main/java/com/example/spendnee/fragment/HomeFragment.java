package com.example.spendnee.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spendnee.R;
import com.example.spendnee.activity.AddGroupActivity;
import com.example.spendnee.adapter.TransactionAdapter;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.FragmentHomeBinding;
import com.example.spendnee.model.Transaction;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.viewmodel.TransactionViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendOrientation;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private TransactionViewModel viewModel;
    String sumIn, sumEx, sumTo;
    Double sumIncome = 0.0;
    Double sumExpense = 0.0;
    Double sumTotal = 0.0;

    Intent intent = new Intent();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.fragmentHomePcChart.setDrawHoleEnabled(true);
        binding.fragmentHomePcChart.setUsePercentValues(true);
        binding.fragmentHomePcChart.setEntryLabelTextSize(13);
        binding.fragmentHomePcChart.setEntryLabelColor(Color.WHITE);
        binding.fragmentHomePcChart.setCenterText(getString(R.string.app_name));
        binding.fragmentHomePcChart.setCenterTextSize(24);
        binding.fragmentHomePcChart.getDescription().setEnabled(false);

        Legend legend = binding.fragmentHomePcChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);

        DataLocalManager.setSumIncome("0.0");
        DataLocalManager.setSumExpense("0.0");
        DataLocalManager.setSumTotal("0.0");

        DataLocalManager.setFoodIncome("0.0");
        DataLocalManager.setEducationIncome("0.0");
        DataLocalManager.setHealthIncome("0.0");
        DataLocalManager.setInsuranceIncome("0.0");
        DataLocalManager.setFurnitureIncome("0.0");
        DataLocalManager.setHousewareIncome("0.0");
        DataLocalManager.setPetIncome("0.0");
        DataLocalManager.setCosmeticsIncome("0.0");
        DataLocalManager.setEntertainmentIncome("0.0");
        DataLocalManager.setInvestmentIncome("0.0");
        DataLocalManager.setSalaryIncome("0.0");
        DataLocalManager.setOtherIncome("0.0");

        init();
        viewModel.refreshLiveData();
        return binding.getRoot();
    }

    public void init(){


        TransactionAdapter transactionAdapter = new TransactionAdapter(getContext(), new TransactionAdapter.TransactionClickListener() {
            @Override
            public void onTransactionClicked(Transaction transaction) {

            }
        });


        binding.fragmentHomeTvTotalAmount.setText(DataLocalManager.getSumTotal());

        viewModel = new ViewModelProvider(getActivity()).get(TransactionViewModel.class);
        viewModel.getTransactionList().observe(getViewLifecycleOwner(), transactions -> {
            transactionAdapter.setTransactionList(transactions);

            for (int i = 0; i < transactions.size(); i++) {
                if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                    sumIncome = sumIncome + transactions.get(i).getAmount();
                    sumIn = String.valueOf(sumIncome);
                    DataLocalManager.setSumIncome(String.valueOf(sumIncome));

                    sumTotal = sumIncome - sumExpense;
                    sumTo = String.valueOf(sumTotal);
                    DataLocalManager.setSumTotal(sumTo);
                    binding.fragmentHomeTvTotalAmount.setText(sumTo);

                    Bundle bundle = new Bundle();
                    bundle.putString("income", sumIn);
                    bundle.putString("total", sumTo);
                    intent.putExtras(bundle);

                } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                    sumExpense = sumExpense + transactions.get(i).getAmount();
                    sumEx = String.valueOf(sumExpense);
                    DataLocalManager.setSumExpense(String.valueOf(sumExpense));

                    sumTotal = sumIncome - sumExpense;
                    sumTo = String.valueOf(sumTotal);
                    DataLocalManager.setSumTotal(sumTo);
                    binding.fragmentHomeTvTotalAmount.setText(sumTo);

                    Bundle bundle = new Bundle();
                    bundle.putString("expense", sumEx);
                    bundle.putString("total", sumTo);
                    intent.putExtras(bundle);
                }

            }


            if (DataLocalManager.getCheckLogin()){
                ArrayList<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(Float.parseFloat(DataLocalManager.getSumIncome()), getString(R.string.income)));
                entries.add(new PieEntry(Float.parseFloat(DataLocalManager.getSumExpense()), getString(R.string.expense)));

                ArrayList<Integer> colors = new ArrayList<>();
                colors.add(Color.BLACK);
                colors.add(R.color.gray_200);

                PieDataSet dataSet = new PieDataSet(entries, DataLocalManager.getUsernameData());
                dataSet.setColors(colors);

                PieData data = new PieData(dataSet);
                data.setDrawValues(true);
                data.setValueFormatter(new PercentFormatter(binding.fragmentHomePcChart));
                data.setValueTextSize(14f);
                data.setValueTextColor(Color.WHITE);

                binding.fragmentHomePcChart.setData(data);
                binding.fragmentHomePcChart.invalidate();

                binding.fragmentHomePcChart.animateY(1400, Easing.EaseInOutQuad);
            } else {
                binding.fragmentHomeTvTotalAmount.setText("0.0");
            }

            sumIncome = 0.0;
            sumExpense = 0.0;
            sumTotal = 0.0;
        });

        binding.fragmentHomeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        binding.fragmentHomeRecyclerView.setAdapter(transactionAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}