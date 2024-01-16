package com.example.spendnee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendnee.R;
import com.example.spendnee.databinding.ItemAccountBinding;
import com.example.spendnee.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder>{

    Context context;
    List<Account> accounts;

    public AccountAdapter(Context context) {
        this.context = context;
    }

    public interface AccountsClickListener {
        void onAccountSelected(Account account);
    }

    AccountsClickListener accountsClickListener;

    public AccountAdapter(Context context, AccountsClickListener accountsClickListener) {
        this.context = context;
//        this.accounts = accounts;
        this.accountsClickListener = accountsClickListener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.item_account, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accounts.get(position);

        if (accounts == null)
            return;

        holder.binding.itemAccountName.setText(account.getNameAccount());
        holder.itemView.setOnClickListener(c-> {
            accountsClickListener.onAccountSelected(account);
        });
    }

    public void setAccountList(List<Account> accountList){
        this.accounts = accountList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (accounts == null){
            return 0;
        } else {
            return accounts.size();
        }

    }

    public class AccountViewHolder extends RecyclerView.ViewHolder {

        ItemAccountBinding binding;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemAccountBinding.bind(itemView);
        }
    }
}
