package com.example.spendnee.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spendnee.R;
import com.example.spendnee.databinding.ItemCategoryBinding;
import com.example.spendnee.model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categories;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public interface CategoryClickListener{
        void onCategoryClicked(Category category);
    }

    CategoryClickListener categoryClickListener;

//    public CategoryAdapter(Context context, List<Category> categories, CategoryClickListener categoryClickListener) {
//        this.context = context;
//        this.categories = categories;
//        this.categoryClickListener = categoryClickListener;
//    }

    public CategoryAdapter(Context context, CategoryClickListener categoryClickListener) {
        this.context = context;
//        this.categories = categories;
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);

        if (categories == null){
            return;
        }

        holder.binding.itemCategoryTvCategoryName.setText(category.getNameCategory());
//        holder.binding.itemCategoryIvCategoryIcon.setImageResource(category.getImageCategory());
        Picasso.get().load(category.getImageCategory()).into(holder.binding.itemCategoryIvCategoryIcon);

        holder.itemView.setOnClickListener(c -> {
            categoryClickListener.onCategoryClicked(category);
        });
    }

    public void setCategoryList(List<Category> categoryList){
        this.categories = categoryList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (categories == null){
            return 0;
        } else {
            return categories.size();
        }
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        ItemCategoryBinding binding;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCategoryBinding.bind(itemView);
        }
    }
}
