package com.example.app_4621.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.app_4621.R;
import com.example.app_4621.model.Item;
import com.example.app_4621.model.ItemType;
import com.example.app_4621.vm.GroceryListViewModel;
import com.example.app_4621.vm.ItemViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class ItemRecyclerViewAdapter extends Adapter {
    private final Context context;
    private GroceryListViewModel vm;
    private ItemViewModel recentlyDeletedItemVm;
    private int recentlyDeletedItemPosition;

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

    public void deleteItem(int position) {
        List<ItemViewModel> itemVms = vm.getItemList();

        recentlyDeletedItemVm = itemVms.get(position);
        recentlyDeletedItemPosition = position;
        itemVms.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar(recentlyDeletedItemVm.item.getName());
    }

    public void addItem(String itemName, int itemQuantity, String itemType) {
        Log.d("TAG", "addItem: here");

        //Toast.makeText(context, "adding item called", Toast.LENGTH_SHORT);
        ItemViewModel addedItemVm = new ItemViewModel(new Item(itemName, itemQuantity, ItemType.getEnumFromString(itemType)));

        List<ItemViewModel> itemVms = vm.getItemList();
        itemVms.add(addedItemVm);
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    private void showUndoSnackbar(String name) {
        View view = ((Activity) context).findViewById(R.id.layout);
        Snackbar snackbar = Snackbar.make(view, "" + name + " removed" ,
                Snackbar.LENGTH_LONG);
        snackbar.setAction("undo", v -> undoDelete());
        snackbar.show();
    }

    private void undoDelete() {
        List<ItemViewModel> itemVms = vm.getItemList();

        itemVms.add(recentlyDeletedItemPosition,
                recentlyDeletedItemVm);
        notifyItemInserted(recentlyDeletedItemPosition);
    }
}
