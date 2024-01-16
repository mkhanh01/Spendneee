package com.example.spendnee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendnee.R;
import com.example.spendnee.databinding.ItemAccountBinding;
import com.example.spendnee.model.GroupMember;
import com.example.spendnee.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    Context context;
    List<User> users;

    public interface UserItemClicked{
        void onUserSelected(User user);
    }

    UserItemClicked userItemClicked;

    public UserAdapter(Context context) {
        this.context = context;
    }

    public UserAdapter(Context context, UserItemClicked userItemClicked) {
        this.context = context;
        this.userItemClicked = userItemClicked;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_account, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);

        if (users == null)
            return;

        holder.binding.itemAccountName.setText(user.getUsername());
        holder.itemView.setOnClickListener(c -> {
            userItemClicked.onUserSelected(user);
        });
    }

    public void setUsers(List<User> usersList) {
        this.users = usersList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (users == null){
            return 0;
        } else {
            return users.size();
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        ItemAccountBinding binding;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemAccountBinding.bind(itemView);
        }
    }
}
