package com.example.spendnee.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendnee.R;
import com.example.spendnee.activity.AddGroupActivity;
import com.example.spendnee.api.API;
import com.example.spendnee.data.DataLocalManager;
import com.example.spendnee.databinding.ItemGroupBinding;
import com.example.spendnee.fragment.GroupTransactionFragment;
import com.example.spendnee.model.Group;
import com.example.spendnee.model.Transaction;
import com.example.spendnee.service.DataService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder>{

    Context context;
    List<Group> groups;
    GroupClickListener groupClickListener;

    public interface  GroupClickListener{
        void onGroupClicked(Group group);
    }

    public GroupAdapter(Context context) {
        this.context = context;
    }

    public GroupAdapter(Context context, GroupClickListener groupClickListener) {
        this.context = context;
        this.groupClickListener = groupClickListener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groups.get(position);

        if (groups == null){
            return;
        }

        holder.binding.itemGroupTvGroupName.setText(group.getNameGroup());
        holder.binding.itemGroupTvGroupUsername.setText(DataLocalManager.getUsernameData());
 //       Picasso.get().load(group.getCategoryImage()).into(holder.binding.itemGroupIvCategoryImage);
        DataService dataService = API.getAccount().create(DataService.class);

        dataService.getTransactionGroup(group.getIdGroup()).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.body() != null){
                    if (response.body().size() > 0){
                        Picasso.get().load(response.body().get(response.body().size() -1).getCategoryImage()).into(holder.binding.itemGroupIvCategoryImage);
                        group.setCategoryImage(response.body().get(response.body().size() -1).getCategoryImage());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });

        holder.binding.itemLibraryPlaylist.setOnClickListener(c ->{
//            callAddGroupActivity(group);
            DataLocalManager.setIdGroup(group.getIdGroup());
            groupClickListener.onGroupClicked(group);
        });
    }

    private void callAddGroupActivity(Group group){
//        DataLocalManager.setIdGroup(group.getIdGroup());
//
//        Intent intent = new Intent(context, AddGroupActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("idGroup", group.getIdGroup());
        bundle.putString("nameGroup", group.getNameGroup());
        bundle.putString("categoryImage", group.getCategoryImage());

//        intent.putExtras(bundle);
//        context.startActivity(intent);
    }

    private void callGroupTransactionFragment(Group group){
        DataLocalManager.setIdGroup(group.getIdGroup());

        GroupTransactionFragment groupTransactionFragment = new GroupTransactionFragment();


        Bundle bundle = new Bundle();
        bundle.putInt("idGroup", group.getIdGroup());
        bundle.putString("nameGroup", group.getNameGroup());
        bundle.putString("categoryImage", group.getCategoryImage());


    }

    @Override
    public int getItemCount() {
        if (groups != null){
            return groups.size();
        }
        return 0;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder{

        ItemGroupBinding binding;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemGroupBinding.bind(itemView);
        }
    }
}
