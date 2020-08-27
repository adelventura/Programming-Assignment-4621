package com.example.app_4621.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import com.example.app_4621.R;
import com.example.app_4621.vm.GroceryListViewModel;

import static androidx.recyclerview.widget.RecyclerView.*;

public class ItemRecyclerViewAdapter extends Adapter {
    private final Context context;
    private GroceryListViewModel vm;

    public ItemRecyclerViewAdapter(Context context, GroceryListViewModel vm) {
        this.context = context;
        this.vm = vm;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
        ViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((ItemViewHolder) holder).setViewModel(vm.getItemList().get(position));
    }

    @Override
    public int getItemCount() {
        return vm.getItemList().size();
    }
}
