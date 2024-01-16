package com.example.spendnee.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.spendnee.R;
import com.example.spendnee.activity.LoginActivity;
import com.example.spendnee.adapter.BudgetAdapter;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.FragmentBudgetBinding;
import com.example.spendnee.model.BaseResponse;
import com.example.spendnee.model.Budget;
import com.example.spendnee.repository.BudgetRepository;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.viewmodel.BudgetViewModel;
import com.github.mikephil.charting.formatter.IFillFormatter;

import java.util.Calendar;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetFragment extends Fragment {

    private FragmentBudgetBinding binding;
    private BudgetViewModel viewModel;
    private Calendar calendar;

    Double sumFood, sumEdu, sumHealth, sumIns, sumFur, sumHouseware;
    Double sumPet, sumCos, sumEnt, sumInv, sumSalary, sumOther;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBudgetBinding.inflate(inflater, container, false);

        calendar = Calendar.getInstance();
        init();

        viewModel.refreshLiveData();
        DataLocalManager.setCheckBudget(false);
        DataLocalManager.setCheckUpdateTransaction(false);
        DataLocalManager.setCheckUpdateBudget( false);

        return binding.getRoot();
    }

    public void init(){
        BudgetAdapter budgetAdapter = new BudgetAdapter(getContext(), new BudgetAdapter.BudgetClickListener() {
            @Override
            public void onBudgetClicked(Budget budget) {
                showBottomDialog(budget.getIdBudget());
                DataLocalManager.setCheckUpdateBudget(true);

                DataLocalManager.setIdBudget(budget.getIdBudget());
                DataLocalManager.setTypeBudget(budget.getTypeBudget());
                DataLocalManager.setBudgetCategoryImage(budget.getCategoryImage());
                DataLocalManager.setTotalAmount(String.valueOf(budget.getTotalAmount()));
                DataLocalManager.setBudgetCategoryName(budget.getCategoryName());
                DataLocalManager.setAccountBudget(budget.getAccount());
                DataLocalManager.setDateBudget(budget.getDate());
                DataLocalManager.setNoteBudget(budget.getNote());


                DataLocalManager.setCheckBudget(false);
            }
        });

        viewModel = new ViewModelProvider(getActivity()).get(BudgetViewModel.class);
        viewModel.getBudgetList().observe(getViewLifecycleOwner(), budgets -> {
            budgetAdapter.setBudgetList(budgets);

            if (budgets != null) {
                binding.fragmentBudgetIvEmptyBox.setVisibility(View.GONE);
                binding.fragmentBudgetTvDoNotHaveBudget.setVisibility(View.GONE);
            }

            for (int i = 0; i < budgets.size(); i++) {

                if (budgets.get(i).getTypeBudget().equals(Constants.INCOME)){

                    if (budgets.get(i).getCategoryName().equals(Constants.FOOD)){
                        sumFood = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getFoodIncome());
                        budgets.get(i).setRemainingAmount(sumFood);
                        DataLocalManager.setFood(String.valueOf(sumFood));

                    } if (budgets.get(i).getCategoryName().equals(Constants.EDUCATION)){
                        sumEdu = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getEducationIncome());
                        budgets.get(i).setRemainingAmount(sumEdu);
                        DataLocalManager.setEducation(String.valueOf(sumEdu));

                    }
                    if (budgets.get(i).getCategoryName().equals(Constants.HEALTH)){
                        sumHealth = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getHealthIncome());
                        budgets.get(i).setRemainingAmount(sumHealth);
                        DataLocalManager.setHealth(String.valueOf(sumHealth));
                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.INSURANCE)){
                        sumIns = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getInsuranceIncome());
                        budgets.get(i).setRemainingAmount(sumIns);
                        DataLocalManager.setInsurance(String.valueOf(sumIns));
                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.FURNITURE)){
                        sumFur = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getFurnitureIncome());
                        budgets.get(i).setRemainingAmount(sumFur);
                        DataLocalManager.setFurniture(String.valueOf(sumFur));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.HOUSEWARE)){
                        sumHouseware = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getHousewareIncome());
                        budgets.get(i).setRemainingAmount(sumHouseware);
                        DataLocalManager.setHouseware(String.valueOf(sumHouseware));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.PET)){
                        sumPet = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getPETIncome());
                        budgets.get(i).setRemainingAmount(sumPet);
                        DataLocalManager.setPet(String.valueOf(sumPet));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.COSMETICS)){
                        sumCos = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getCosmeticsIncome());
                        budgets.get(i).setRemainingAmount(sumCos);
                        DataLocalManager.setCosmetics(String.valueOf(sumCos));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.ENTERTAINMENT)){
                        sumEnt = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getEntertainmentIncome());
                        budgets.get(i).setRemainingAmount(sumEnt);
                        DataLocalManager.setEntertainment(String.valueOf(sumEnt));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.INVESTMENT)){
                        sumInv = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getInvestmentIncome());
                        budgets.get(i).setRemainingAmount(sumInv);
                        DataLocalManager.setInvestment(String.valueOf(sumInv));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.SALARY)){
                        sumSalary = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getSalaryIncome());
                        budgets.get(i).setRemainingAmount(sumSalary);
                        DataLocalManager.setSalary(String.valueOf(sumSalary));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.OTHER)){
                        sumOther = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getOtherIncome());
                        budgets.get(i).setRemainingAmount(sumOther);
                        DataLocalManager.setOther(String.valueOf(sumOther));

                    }

                }

                else if (budgets.get(i).getTypeBudget().equals(Constants.EXPENSE)){

                    if (budgets.get(i).getCategoryName().equals(Constants.FOOD)){
                        sumFood = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getFoodIncome());
                        budgets.get(i).setRemainingAmount(sumFood);
                        DataLocalManager.setFood(String.valueOf(sumFood));

                    } if (budgets.get(i).getCategoryName().equals(Constants.EDUCATION)){
                        sumEdu = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getEducationIncome());
                        budgets.get(i).setRemainingAmount(sumEdu);
                        DataLocalManager.setEducation(String.valueOf(sumEdu));

                    }
                    if (budgets.get(i).getCategoryName().equals(Constants.HEALTH)){
                        sumHealth = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getHealthIncome());
                        budgets.get(i).setRemainingAmount(sumHealth);
                        DataLocalManager.setFood(String.valueOf(sumHealth));
                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.INSURANCE)){
                        sumIns = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getInsuranceIncome());
                        budgets.get(i).setRemainingAmount(sumIns);
                        DataLocalManager.setInsurance(String.valueOf(sumIns));
                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.FURNITURE)){
                        sumFur = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getFurnitureIncome());
                        budgets.get(i).setRemainingAmount(sumFur);
                        DataLocalManager.setFurniture(String.valueOf(sumFur));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.HOUSEWARE)){
                        sumHouseware = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getHousewareIncome());
                        budgets.get(i).setRemainingAmount(sumHouseware);
                        DataLocalManager.setHouseware(String.valueOf(sumHouseware));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.PET)){
                        sumPet = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getPETIncome());
                        budgets.get(i).setRemainingAmount(sumPet);
                        DataLocalManager.setPet(String.valueOf(sumPet));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.COSMETICS)){
                        sumCos = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getCosmeticsIncome());
                        budgets.get(i).setRemainingAmount(sumCos);
                        DataLocalManager.setCosmetics(String.valueOf(sumCos));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.ENTERTAINMENT)){
                        sumEnt = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getEntertainmentIncome());
                        budgets.get(i).setRemainingAmount(sumEnt);
                        DataLocalManager.setEntertainment(String.valueOf(sumEnt));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.INVESTMENT)){
                        sumInv = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getInvestmentIncome());
                        budgets.get(i).setRemainingAmount(sumInv);
                        DataLocalManager.setInvestment(String.valueOf(sumInv));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.SALARY)){
                        sumSalary = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getSalaryIncome());
                        budgets.get(i).setRemainingAmount(sumSalary);
                        DataLocalManager.setSalary(String.valueOf(sumSalary));

                    }
                    else if (budgets.get(i).getCategoryName().equals(Constants.OTHER)){
                        sumOther = budgets.get(i).getTotalAmount() + Double.valueOf(DataLocalManager.getOtherIncome());
                        budgets.get(i).setRemainingAmount(sumOther);
                        DataLocalManager.setOther(String.valueOf(sumOther));

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

        binding.fragmentBudgetRecycleLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.fragmentBudgetRecycleLayout.setAdapter(budgetAdapter);

        binding.fragmentBudgetFabCreateBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataLocalManager.setCheckBudget(true);
                DataLocalManager.setCheckUpdateBudget(false);

                if (DataLocalManager.getCheckLogin()){
                    new BottomAddFragment().show(getParentFragmentManager(), null);

                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        // DELETE BUDGET ITEM

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                List<Budget> budgetList = budgetAdapter.getBudgetItem();
                Budget budget = budgetList.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.title_dialog));
                builder.setMessage(getResources().getString(R.string.delete_message));
                builder.setCancelable(false);

                builder.setPositiveButton(getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteBudget(budget.getIdBudget());
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
        }).attachToRecyclerView(binding.fragmentBudgetRecycleLayout);


    }

    public void deleteBudget(int idBudget){

        BudgetRepository repository = new BudgetRepository();

        repository.deleteBudget(idBudget).enqueue(new Callback<BaseResponse>() {
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

    private void showBottomDialog(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(getString(R.string.id_budget), id);

        BottomAddFragment bottomAddFragment = new BottomAddFragment();

        bottomAddFragment.setArguments(bundle);
        bottomAddFragment.show(getParentFragmentManager(), null);

        DataLocalManager.setCheckUpdateBudget(true);

    }
}