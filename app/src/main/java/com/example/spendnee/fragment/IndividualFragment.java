package com.example.spendnee.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.FragmentIndividualBinding;
import com.example.spendnee.fragment.BottomAddFragment;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Transaction;
import com.example.spendnee.repository.TransactionRepository;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.viewmodel.TransactionViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IndividualFragment extends Fragment {
    private FragmentIndividualBinding binding;

    private TransactionViewModel viewModel;
    String sumIn, sumEx, sumTo;
    Double sumIncome = 0.0;
    Double sumExpense = 0.0;
    Double sumTotal = 0.0;
    Double sumFromNoWhere = 0.0;

    Double sumFood = 0.0, sumEdu= 0.0, sumHealth= 0.0, sumIns= 0.0, sumFur= 0.0, sumHouseware= 0.0;
    Double sumPet= 0.0, sumCos= 0.0, sumEnt= 0.0, sumInv= 0.0, sumSalary= 0.0, sumOther= 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIndividualBinding.inflate(inflater, container, false);

        init();
        viewModel.refreshLiveData();
        DataLocalManager.setCheckUpdateTransaction(false);

        return binding.getRoot();
    }

    private void init(){
        TransactionAdapter transactionAdapter = new TransactionAdapter(getContext(), new TransactionAdapter.TransactionClickListener() {
            @Override
            public void onTransactionClicked(Transaction transaction) {
                showBottomDialog(transaction.getIdTransaction());
                DataLocalManager.setIdTransaction(transaction.getIdTransaction());
                DataLocalManager.setTypeTransaction(transaction.getTypeTransaction());
                DataLocalManager.setAmount(String.valueOf(transaction.getAmount()));
                DataLocalManager.setCategoryName(transaction.getCategoryName());
                DataLocalManager.setCategoryImage(transaction.getCategoryImage());
                DataLocalManager.setAccount(transaction.getAccount());
                DataLocalManager.setDate(transaction.getDate());
                DataLocalManager.setNote(transaction.getNote());

                DataLocalManager.setCheckUpdateTransaction(true);

            }
        });

        viewModel = new ViewModelProvider(getActivity()).get(TransactionViewModel.class);

        viewModel.getTransactionList().observe(getViewLifecycleOwner(), transactions -> {
            transactionAdapter.setTransactionList(transactions);

            for (int i = 0; i < transactions.size(); i++) {
                if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                    sumIncome = sumIncome + transactions.get(i).getAmount();
                    sumIn = String.valueOf(sumIncome);
                    DataLocalManager.setSumIncome(String.valueOf(sumIncome));
                    binding.fragmentIndividualTvIncome.setText(sumIn);

                    sumTotal = sumIncome - sumExpense;
                    sumTo = String.valueOf(sumTotal);
                    DataLocalManager.setSumTotal(sumTo);
                    binding.fragmentIndividualTvTotal.setText(sumTo);

                } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                    sumExpense = sumExpense + transactions.get(i).getAmount();
                    sumEx = String.valueOf(sumExpense);
                    DataLocalManager.setSumExpense(String.valueOf(sumExpense));
                    binding.fragmentIndividualTvExpense.setText(sumEx);

                    sumTotal = sumIncome - sumExpense;
                    sumTo = String.valueOf(sumTotal);
                    DataLocalManager.setSumTotal(sumTo);
                    binding.fragmentIndividualTvTotal.setText(sumTo);
                }

            }
            sumIncome = 0.0;
            sumExpense = 0.0;
            sumFromNoWhere = 0.0;


            for (int i = 0; i < transactions.size(); i++) {
                if (transactions.get(i).getCategoryName().equals(Constants.FOOD) ){
                    if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                        sumFood = sumFood + transactions.get(i).getAmount();
                        DataLocalManager.setFoodIncome(String.valueOf(sumFood));
                    } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){

                        if (Double.valueOf(DataLocalManager.getFoodIncome()) > 0){
//                            sumFood = 0.0;
                            sumFood = Double.valueOf(DataLocalManager.getFoodIncome()) - transactions.get(i).getAmount();
                            DataLocalManager.setFoodIncome(String.valueOf(sumFood));
                        } else {
//                            sumFood = 0.0;
                            sumFood = sumFood - transactions.get(i).getAmount();
                            DataLocalManager.setFoodIncome(String.valueOf(sumFood));
                        }

                    }

                }
                else if (transactions.get(i).getCategoryName().equals(Constants.EDUCATION)){

                    if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                        sumEdu = sumEdu + transactions.get(i).getAmount();
                        DataLocalManager.setEducationIncome(String.valueOf(sumEdu));

                    } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                        if (Double.valueOf(DataLocalManager.getEducationIncome()) > 0){
                            sumEdu = Double.valueOf(DataLocalManager.getEducationIncome()) - transactions.get(i).getAmount();
                            DataLocalManager.setEducationIncome(String.valueOf(sumEdu));
                        } else {
                            sumEdu = sumEdu - transactions.get(i).getAmount();
                            DataLocalManager.setEducationIncome(String.valueOf(sumEdu));
                        }

                    }
                }
                else if (transactions.get(i).getCategoryName().equals(Constants.HEALTH)){

                    if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                        sumHealth = sumHealth + transactions.get(i).getAmount();
                        DataLocalManager.setHealthIncome(String.valueOf(sumHealth));

                    } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                        if (Double.valueOf(DataLocalManager.getHealthIncome()) > 0){
                            sumHealth = Double.valueOf(DataLocalManager.getHealthIncome()) - transactions.get(i).getAmount();
                            DataLocalManager.setHealthIncome(String.valueOf(sumHealth));
                        } else {
                            sumHealth = sumHealth - transactions.get(i).getAmount();
                            DataLocalManager.setHealthIncome(String.valueOf(sumHealth));
                        }

                    }

                }
                else if (transactions.get(i).getCategoryName().equals(Constants.INSURANCE)){

                    if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                        sumIns = sumIns + transactions.get(i).getAmount();
                        DataLocalManager.setInsuranceIncome(String.valueOf(sumIns));

                    } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                        if (Double.valueOf(DataLocalManager.getInsuranceIncome()) > 0){
                            sumIns = Double.valueOf(DataLocalManager.getInsuranceIncome()) - transactions.get(i).getAmount();
                            DataLocalManager.setInsuranceIncome(String.valueOf(sumIns));
                        } else {
                            sumIns = sumIns - transactions.get(i).getAmount();
                            DataLocalManager.setInsuranceIncome(String.valueOf(sumIns));
                        }

                    }


                }
                else if (transactions.get(i).getCategoryName().equals(Constants.FURNITURE)){

                    if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                        sumFur = sumFur + transactions.get(i).getAmount();
                        DataLocalManager.setFurnitureIncome(String.valueOf(sumFur));

                    } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                        if (Double.valueOf(DataLocalManager.getFurnitureIncome()) > 0){
                            sumFur = Double.valueOf(DataLocalManager.getFurnitureIncome()) - transactions.get(i).getAmount();
                            DataLocalManager.setFurnitureIncome(String.valueOf(sumFur));
                        } else {
                            sumFur = sumFur - transactions.get(i).getAmount();
                            DataLocalManager.setFurnitureIncome(String.valueOf(sumFur));
                        }

                    }

                }
                else if (transactions.get(i).getCategoryName().equals(Constants.HOUSEWARE)){

                    if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                        sumHouseware = sumHouseware + transactions.get(i).getAmount();
                        DataLocalManager.setHousewareIncome(String.valueOf(sumHouseware));

                    } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                        if (Double.valueOf(DataLocalManager.getHousewareIncome()) > 0){
                            sumHouseware = Double.valueOf(DataLocalManager.getHousewareIncome()) - transactions.get(i).getAmount();
                            DataLocalManager.setHousewareIncome(String.valueOf(sumHouseware));
                        } else {
                            sumHouseware = sumHouseware - transactions.get(i).getAmount();
                            DataLocalManager.setHousewareIncome(String.valueOf(sumHouseware));
                        }

                    }

                }
                else if (transactions.get(i).getCategoryName().equals(Constants.PET)){

                    if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                        sumPet = sumPet + transactions.get(i).getAmount();
                        DataLocalManager.setPetIncome(String.valueOf(sumPet));

                    } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                        if (Double.valueOf(DataLocalManager.getPETIncome()) > 0){
                            sumPet = Double.valueOf(DataLocalManager.getPETIncome()) - transactions.get(i).getAmount();
                            DataLocalManager.setPetIncome(String.valueOf(sumPet));
                        } else {
                            sumPet = sumPet - transactions.get(i).getAmount();
                            DataLocalManager.setPetIncome(String.valueOf(sumPet));
                        }

                    }

                }
                else if (transactions.get(i).getCategoryName().equals(Constants.COSMETICS)){
                    if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                        sumCos = sumCos + transactions.get(i).getAmount();
                        DataLocalManager.setCosmeticsIncome(String.valueOf(sumCos));

                    } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                        if (Double.valueOf(DataLocalManager.getCosmeticsIncome()) > 0){
                            sumCos = Double.valueOf(DataLocalManager.getCosmeticsIncome()) - transactions.get(i).getAmount();
                            DataLocalManager.setCosmeticsIncome(String.valueOf(sumCos));
                        } else {
                            sumCos = sumCos - transactions.get(i).getAmount();
                            DataLocalManager.setCosmeticsIncome(String.valueOf(sumCos));
                        }

                    }

                }
                else if (transactions.get(i).getCategoryName().equals(Constants.ENTERTAINMENT)){
                    if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                        sumEnt = sumEnt + transactions.get(i).getAmount();
                        DataLocalManager.setEntertainmentIncome(String.valueOf(sumEnt));

                    } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                        if (Double.valueOf(DataLocalManager.getEntertainmentIncome()) > 0){
                            sumEnt = Double.valueOf(DataLocalManager.getEntertainmentIncome()) - transactions.get(i).getAmount();
                            DataLocalManager.setEntertainmentIncome(String.valueOf(sumEnt));
                        } else {
                            sumEnt = sumEnt - transactions.get(i).getAmount();
                            DataLocalManager.setEntertainmentIncome(String.valueOf(sumEnt));
                        }

                    }

                }
                else if (transactions.get(i).getCategoryName().equals(Constants.INVESTMENT)){

                    if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                        sumInv = sumInv + transactions.get(i).getAmount();
                        DataLocalManager.setInvestmentIncome(String.valueOf(sumInv));

                    } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                        if (Double.valueOf(DataLocalManager.getInvestmentIncome()) > 0){
                            sumInv = Double.valueOf(DataLocalManager.getInvestmentIncome()) - transactions.get(i).getAmount();
                            DataLocalManager.setInvestmentIncome(String.valueOf(sumInv));
                        } else {
                            sumInv = sumInv - transactions.get(i).getAmount();
                            DataLocalManager.setInvestmentIncome(String.valueOf(sumInv));
                        }

                    }

                }
                else if (transactions.get(i).getCategoryName().equals(Constants.SALARY)){
                    if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                        sumSalary = sumSalary + transactions.get(i).getAmount();
                        DataLocalManager.setSalaryIncome(String.valueOf(sumSalary));

                    } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                        if (Double.valueOf(DataLocalManager.getSalaryIncome()) > 0){
                            sumSalary = Double.valueOf(DataLocalManager.getSalaryIncome()) - transactions.get(i).getAmount();
                            DataLocalManager.setSalaryIncome(String.valueOf(sumSalary));
                        } else {
                            sumSalary = sumSalary - transactions.get(i).getAmount();
                            DataLocalManager.setSalaryIncome(String.valueOf(sumSalary));
                        }

                    }

                }
                else if (transactions.get(i).getCategoryName().equals(Constants.OTHER)){
                    if (transactions.get(i).getTypeTransaction().equals(Constants.INCOME)){
                        sumOther = sumOther + transactions.get(i).getAmount();
                        DataLocalManager.setOtherIncome(String.valueOf(sumOther));

                    } else if (transactions.get(i).getTypeTransaction().equals(Constants.EXPENSE)){
                        if (Double.valueOf(DataLocalManager.getOtherIncome()) > 0){
                            sumOther = Double.valueOf(DataLocalManager.getOtherIncome()) - transactions.get(i).getAmount();
                            DataLocalManager.setOtherIncome(String.valueOf(sumOther));
                        } else {
                            sumOther = sumOther - transactions.get(i).getAmount();
                            DataLocalManager.setOtherIncome(String.valueOf(sumOther));
                        }

                    }

                }

            }

            sumFood = 0.0;
            sumEdu= 0.0;
            sumHealth= 0.0;
            sumIns= 0.0;
            sumFur= 0.0;
            sumHouseware= 0.0;
            sumPet= 0.0;
            sumCos= 0.0;
            sumEnt= 0.0;
            sumInv= 0.0;
            sumSalary= 0.0 ;
            sumOther= 0.0;

        });

        binding.fragmentIndividualRvListTrans.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        binding.fragmentIndividualRvListTrans.setAdapter(transactionAdapter);

        new ItemTouchHelper(new androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback(0, androidx.recyclerview.widget.ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                List<Transaction> transactions = transactionAdapter.getTransactionItems();
                Transaction transaction = transactions.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle(getResources().getString(R.string.title_dialog));
                builder.setMessage(getResources().getString(R.string.delete_message));
                builder.setCancelable(false);

                builder.setPositiveButton(getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTransaction(transaction.getIdTransaction());
                    }
                });

                builder.setNegativeButton(getString(R.string.no_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.refreshLiveData();
                        dialogInterface.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        }).attachToRecyclerView(binding.fragmentIndividualRvListTrans);

    }

    public void deleteTransaction(int idTransaction){
        TransactionRepository repository = new TransactionRepository();

        repository.deleteTransaction(idTransaction).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().getIsSuccess().equals(Constants.successfully)){
                    StyleableToast.makeText(getContext(), getString(R.string.deleted_successfully),
                            Toast.LENGTH_LONG, R.style.myToast).show();
                    viewModel.refreshLiveData();
                } else {
                    StyleableToast.makeText(getContext(), getString(R.string.deleted_unsuccessfully),
                            Toast.LENGTH_LONG, R.style.myToast).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                StyleableToast.makeText(getContext(), t.getMessage(),
                        Toast.LENGTH_LONG, R.style.myToast).show();
            }
        });
    }

    private void showBottomDialog(int idTrans){
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.id_transaction), idTrans);

        BottomAddFragment bottomAddFragment = new BottomAddFragment();

        bottomAddFragment.setArguments(bundle);
        bottomAddFragment.show(getParentFragmentManager(), null);

        DataLocalManager.setCheckUpdateTransaction(true);
    }
}