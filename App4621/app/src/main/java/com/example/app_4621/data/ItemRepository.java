package com.example.app_4621.data;

import com.example.app_4621.model.Item;
import com.example.app_4621.model.ItemType;

import java.util.List;

public interface ItemRepository {
    Item getItem(String name);
    List<Item> getItems();
    List<Item> getItemsOfType(ItemType type);

}
