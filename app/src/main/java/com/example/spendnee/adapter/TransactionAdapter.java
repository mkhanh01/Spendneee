package com.example.spendnee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendnee.R;
import com.example.spendnee.api.API;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.ItemTransactionBinding;
import com.example.spendnee.model.Category;
import com.example.spendnee.model.Transaction;
import com.example.spendnee.service.DataService;
import com.example.spendnee.utils.Constants;
import com.example.spendnee.utils.Helper;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>{

    Context context;
    List<Transaction> transactions;
    TransactionClickListener transactionClickListener;

    Double sumIncome = 0.0;
    Double sumExpense = 0.0;
    Double sumTotal = 0.0;

    public interface TransactionClickListener{
        void onTransactionClicked(Transaction transaction);
    }

    public TransactionAdapter(Context context) {
        this.context = context;
    }

    public TransactionAdapter(Context context, TransactionClickListener transactionClickListener) {
        this.context = context;
        this.transactionClickListener = transactionClickListener;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
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

        holder.itemView.setOnClickListener(c ->{
            transactionClickListener.onTransactionClicked(transaction);
        });

    }

    @Override
    public int getItemCount() {
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

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        ItemTransactionBinding binding;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemTransactionBinding.bind(itemView);
        }
    }
}
