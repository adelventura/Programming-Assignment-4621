package com.example.app_4621.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_4621.R;
import com.example.app_4621.vm.ItemViewModel;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTextView;
    private TextView quantityTextView;
    private ItemViewModel vm;
    private Context context;
    private ConstraintLayout parentLayout;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        this.context = itemView.getContext();
        parentLayout = (ConstraintLayout) itemView.findViewById(R.id.item_cell_layout);
        nameTextView = (TextView) itemView.findViewById(R.id.item_name);
        quantityTextView = (TextView) itemView.findViewById(R.id.item_quantity);

        //parentLayout.
    }

    public void setViewModel(ItemViewModel vm) {
        this.vm = vm;
        nameTextView.setText(vm.item.getName());
        quantityTextView.setText("Quantity: " + vm.item.getQuantity());
    }

}

