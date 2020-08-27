package com.example.app_4621.vm;

import android.content.Context;

import com.example.app_4621.model.Item;
import com.example.app_4621.model.ItemRepo;

import java.util.ArrayList;
import java.util.List;

public class GroceryListViewModel {
    private List<ItemViewModel> itemList = new ArrayList<>();
    private Context context;

    public GroceryListViewModel(Context context) {
        this.context = context;
        ItemRepo itemRepo = ItemRepo.getInstance();
        List<Item> items = itemRepo.getItems();

        for (Item item: items) {
            itemList.add(new ItemViewModel(item));
        }
    }

    public List<ItemViewModel> getItemList() {
        return itemList;
    }
}
