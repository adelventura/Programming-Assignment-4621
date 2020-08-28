package com.example.app_4621.vm;

import android.content.Context;

import com.example.app_4621.data.ItemRepository;
import com.example.app_4621.model.Item;
import com.example.app_4621.data.DebugItemRepository;

import java.util.ArrayList;
import java.util.List;

public class GroceryListViewModel {
    private List<ItemViewModel> itemList = new ArrayList<>();
    private Context context;

    public GroceryListViewModel(Context context) {
        this.context = context;
        ItemRepository debugItemRepository = DebugItemRepository.getInstance();
        List<Item> items = debugItemRepository.getItems();

        for (Item item: items) {
            itemList.add(new ItemViewModel(item));
        }
    }

    public List<ItemViewModel> getItemList() {
        return itemList;
    }
}
