package com.example.app_4621.model;

import java.io.Serializable;
import java.util.HashMap;

public class Item implements Serializable {
    private String name;
    private int quantity;
    private ItemType type;
    private String key;
    private String userId;

    public Item(String name, int quantity, ItemType type) {
        this.name = name;
        this.quantity = quantity;
        this.type = type;
        this.userId = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(String quantity) {
        try {
            this.quantity = Integer.parseInt(quantity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setType(String type) {
        this.type = ItemType.getEnumFromString(type);
    }

    public Item() {}

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public ItemType getType() {
        return type;
    }

    public String getUserId() {
        return userId;
    }

    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> item = new HashMap<>();
        item.put("name", name);
        item.put("quantity", quantity + "");
        item.put("type", type.toString());

        return item;
    }
}
