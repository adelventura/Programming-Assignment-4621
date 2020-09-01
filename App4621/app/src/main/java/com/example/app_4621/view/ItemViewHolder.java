package com.example.app_4621.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_4621.R;
import com.example.app_4621.vm.ItemViewModel;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTextView;
    private TextView quantityTextView;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        nameTextView = (TextView) itemView.findViewById(R.id.item_name);
        quantityTextView = (TextView) itemView.findViewById(R.id.item_quantity);
    }

    public void setViewModel(ItemViewModel vm) {
        nameTextView.setText(vm.item.getName());
        quantityTextView.setText("Quantity: " + vm.item.getQuantity());
    }
}

