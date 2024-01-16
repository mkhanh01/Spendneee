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

import java.util.ConcurrentModificationException;
import java.util.List;

public class GroupMemberAdapter  extends  RecyclerView.Adapter<GroupMemberAdapter.GroupMemberViewHolder>{

    Context context;
    List<GroupMember> members;

    public interface GroupMemberClicked{
        void onGroupMemberSelected(GroupMember groupMember);
    }

    GroupMemberClicked groupMemberClicked;

    public GroupMemberAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public GroupMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupMemberViewHolder(LayoutInflater.from(context).inflate(R.layout.item_account, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMemberViewHolder holder, int position) {
        GroupMember groupMember = members.get(position);

        if (members == null){
            return;
        }

        holder.binding.itemAccountName.setText(groupMember.getUsername());
        holder.itemView.setOnClickListener(c -> {
            groupMemberClicked.onGroupMemberSelected(groupMember);
        });
    }

    @Override
    public int getItemCount() {
        if (members == null){
            return 0;
        } else {
            return members.size();
        }

    }

    public GroupMemberAdapter(Context context, GroupMemberClicked groupMemberClicked) {
        this.context = context;
        this.groupMemberClicked = groupMemberClicked;
    }


    public void setMembers(List<GroupMember> members) {
        this.members = members;
        notifyDataSetChanged();
    }

    public class GroupMemberViewHolder extends RecyclerView.ViewHolder{

        ItemAccountBinding binding;

        public GroupMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemAccountBinding.bind(itemView);
        }
    }
}
