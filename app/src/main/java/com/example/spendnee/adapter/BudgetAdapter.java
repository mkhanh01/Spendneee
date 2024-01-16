package com.example.spendnee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendnee.R;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.ItemBudgetBinding;
import com.example.spendnee.model.Budget;
import com.example.spendnee.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>{
    Context context;
    List<Budget> budgets;

    Double sumFood, sumEdu, sumHealth, sumIns, sumFur, sumHouseware;
    Double sumPet, sumCos, sumEnt, sumInv, sumSalary, sumOther;

    BudgetClickListener budgetClickListener;

    public interface BudgetClickListener{
        void onBudgetClicked(Budget budget);
    }

    public BudgetAdapter(Context context) {
        this.context = context;
    }

    public BudgetAdapter(Context context, BudgetClickListener budgetClickListener) {
        this.context = context;
        this.budgetClickListener = budgetClickListener;
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BudgetViewHolder(LayoutInflater.from(context).inflate(R.layout.item_budget, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        Budget budget = budgets.get(position);

        if (budgets == null) {
            return;
        }

        holder.binding.itemBudgetTvTotalAmount.setText(String.valueOf(budget.getTotalAmount()));
        holder.binding.itemBudgetTvRemainingAmount.setText(String.valueOf(budget.getRemainingAmount()));
        holder.binding.itemBudgetTvAccount.setText(budget.getAccount());
        holder.binding.itemBudgetTvDate.setText(budget.getDate());
        holder.binding.itemBudgetTvCategoryName.setText(budget.getCategoryName());
        Picasso.get().load(budget.getCategoryImage()).into(holder.binding.itemBudgetIvCategoryIcon);
        if (budget.getTypeBudget().equals(Constants.INCOME)) {
            holder.binding.itemBudgetTvTotalAmount.setTextColor(context.getColor(R.color.green));

        } else if (budget.getTypeBudget().equals(Constants.EXPENSE)) {
            holder.binding.itemBudgetTvTotalAmount.setTextColor(context.getColor(R.color.red));
        }

        holder.itemView.setOnClickListener(c -> {
            budgetClickListener.onBudgetClicked(budget);
        });

    }

    @Override
    public int getItemCount() {
        if (budgets != null){
            return budgets.size();
        } else {
            return 0;
        }
    }

    public void setBudgetList(List<Budget> budgetList){
        this.budgets = budgetList;
        notifyDataSetChanged();
    }

    public List<Budget> getBudgetItem(){
        return budgets;
    }

    public class BudgetViewHolder extends RecyclerView.ViewHolder {

        ItemBudgetBinding binding;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBudgetBinding.bind(itemView);
        }
    }
}
