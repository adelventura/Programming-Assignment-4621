package com.example.app_4621.vm;

import com.example.app_4621.model.Item;

public class ItemViewModel {
    public Item item;
    public boolean isChecked;

    public ItemViewModel(Item item) {
        this.item = item;
        this.isChecked = false;
    }

    public void check(Boolean isChecked) {
        this.isChecked = isChecked;
    }
}
