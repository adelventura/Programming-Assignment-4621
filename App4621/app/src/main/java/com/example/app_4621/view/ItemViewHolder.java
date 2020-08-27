package com.example.app_4621.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_4621.R;
import com.example.app_4621.model.Item;
import com.example.app_4621.vm.ItemViewModel;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private TextView itemTextView;
    private ItemViewModel vm;
    private Context context;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        this.context = itemView.getContext();
        itemTextView = (TextView) itemView.findViewById(R.id.text_view);
    }

    public void setViewModel(ItemViewModel vm) {
        this.vm = vm;
        itemTextView.setText(vm.item.getName());
    }

}

