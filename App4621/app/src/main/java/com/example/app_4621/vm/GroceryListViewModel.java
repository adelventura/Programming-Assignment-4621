package com.example.app_4621.vm;

import android.content.Context;
import android.util.Log;

import com.example.app_4621.data.DbRepository;
import com.example.app_4621.data.ItemRepository;
import com.example.app_4621.model.Item;
import com.example.app_4621.model.ItemType;

import java.util.ArrayList;
import java.util.List;

public class GroceryListViewModel implements DbRepository.Listener {

    public interface Listener {
        void onUpdated();
    }

    private List<ItemViewModel> itemList = new ArrayList<>();
    private List<String> sortTypes = new ArrayList<>();
    private ItemRepository itemRepository;
    private ItemType sortMethod = null;

    public Listener listener;

    public GroceryListViewModel(Context context, DbRepository itemRepository) {
        this.itemRepository = itemRepository;
        itemRepository.listener = this;
        sortItemListByType(null);
    }

    public List<ItemViewModel> getItemList() {
        return itemList;
    }

    public void sortItemListByType(ItemType type) {
        this.sortMethod = type;
        updateData();
    }

    public List<String> getSortTypes() {
        return sortTypes;
    }

    private void updateData() {
        this.sortTypes = new ArrayList<>();
        sortTypes.add("All");
        ItemType[] types = ItemType.values();
        for (ItemType type : types) {
            sortTypes.add(type.toString());
        }

        List<Item> items;
        itemList.clear();

        if (sortMethod == null) {
            items = itemRepository.getItems();
        } else {
            items = itemRepository.getItemsOfType(sortMethod);
        }

        for (Item item: items) {
            itemList.add(new ItemViewModel(item));
        }

        if (listener != null) {
            listener.onUpdated();
        }
    }

    @Override
    public void onUpdated() {
        updateData();
    }

}
