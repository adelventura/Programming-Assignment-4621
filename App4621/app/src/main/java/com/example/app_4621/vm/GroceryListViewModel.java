package com.example.app_4621.vm;

import android.content.Context;
import android.util.Log;

import com.example.app_4621.data.DebugItemRepository;
import com.example.app_4621.data.ItemRepository;
import com.example.app_4621.model.Item;
import com.example.app_4621.model.ItemType;

import java.util.ArrayList;
import java.util.List;

public class GroceryListViewModel {
    private List<ItemViewModel> itemList = new ArrayList<>();
    ItemRepository debugItemRepository;

    public GroceryListViewModel(Context context) {
        this.debugItemRepository = DebugItemRepository.getInstance();
        sortItemListByType(null);
    }

    public List<ItemViewModel> getItemList() {
        return itemList;
    }

    public void sortItemListByType(ItemType type) {
        List<Item> items;
        itemList = new ArrayList<>();

        if (type == null) {
            items = debugItemRepository.getItems();
        } else {
            items = debugItemRepository.getItemsOfType(type);
            Log.d("TAG", "sortItemListByType: ");
        }

        for (Item item: items) {
            itemList.add(new ItemViewModel(item));
        }
    }
}
