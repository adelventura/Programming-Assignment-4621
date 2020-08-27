package com.example.app_4621.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemRepo {
    private static ItemRepo instance = new ItemRepo();

    public static ItemRepo getInstance() {
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
}
