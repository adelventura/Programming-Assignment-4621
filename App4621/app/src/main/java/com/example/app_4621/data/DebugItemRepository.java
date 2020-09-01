package com.example.app_4621.data;

import com.example.app_4621.model.Item;
import com.example.app_4621.model.ItemType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DebugItemRepository implements ItemRepository {
    private static DebugItemRepository instance = new DebugItemRepository();

    public static DebugItemRepository getInstance() {
        return instance;
    }

    private Item[] items  = {
        new Item("Milk", 1, ItemType.DAIRY),
        new Item("Sliced Bread", 1, ItemType.BREAD),
        new Item("Canned Tomatoes", 1, ItemType.CANNED_GOODS),
        new Item("Frozen Pizza", 1, ItemType.FROZEN_FOODS)
    };

    public List<Item> getItems() {
        return Arrays.asList(items);
    }

    public Item getItem(String itemName) {
        for (Item item: items) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getItemsOfType(ItemType type) {
        List<Item> itemsOfType = new ArrayList<>();

        for (Item item: items) {
            if (item.getType() == type) {
                itemsOfType.add(item);
            }
        }

        return itemsOfType;
    }
}
