package com.example.app_4621.model;

public class Item {
    private String name;
    private int quantity;
    private ItemType type;

    public Item(String name, int quantity, ItemType type) {
        this.name = name;
        this.quantity = quantity;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public ItemType getType() {
        return type;
    }
}
