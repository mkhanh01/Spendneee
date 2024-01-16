package com.example.spendnee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendnee.R;
import com.example.spendnee.databinding.ItemTransactionBinding;
import com.example.spendnee.model.Transaction;
import com.example.spendnee.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class HomeTransactionAdapter extends RecyclerView.Adapter<HomeTransactionAdapter.HomeTransactionViewHolder>{

    Context context;
    List<Transaction> transactions;

    public HomeTransactionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HomeTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeTransactionAdapter.HomeTransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        if (transactions == null){
            return;
        }

        holder.binding.itemTransactionTvAmount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.itemTransactionTvAccount.setText(transaction.getAccount());

        holder.binding.itemTransactionTvDate.setText(transaction.getDate());
        holder.binding.itemTransactionTvCategoryName.setText(transaction.getCategoryName());
        Picasso.get().load(transaction.getCategoryImage()).into(holder.binding.itemTransactionIvCategoryIcon);
        if (transaction.getTypeTransaction().equals(Constants.INCOME)){
            holder.binding.itemTransactionTvAmount.setTextColor(context.getColor(R.color.green));

        } else if (transaction.getTypeTransaction().equals(Constants.EXPENSE)){
            holder.binding.itemTransactionTvAmount.setTextColor(context.getColor(R.color.red));
        }

    }

    @Override
    public int getItemCount() {

//        if (transactions != null){
//            if (transactions.size() <= 3) {
//                return transactions.size();
//            } else {
//                return 3;
//            }
//        } else {
//            return 0;
//        }

        if (transactions != null){
            return transactions.size();
        } else {
            return 0;
        }
    }

    public void setTransactionList(List<Transaction> transactionList){
        this.transactions = transactionList;
        notifyDataSetChanged();
    }

    public List<Transaction> getTransactionItems(){
        return transactions;
    }

    public class HomeTransactionViewHolder extends RecyclerView.ViewHolder {

        ItemTransactionBinding binding;

        public HomeTransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemTransactionBinding.bind(itemView);
        }
    }
}
