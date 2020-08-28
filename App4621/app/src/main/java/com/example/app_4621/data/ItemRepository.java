package com.example.app_4621.data;

import com.example.app_4621.model.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> getItems();
    Item getItem(String name);

}
