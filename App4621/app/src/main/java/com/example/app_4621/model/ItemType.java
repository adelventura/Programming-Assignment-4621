package com.example.app_4621.model;

public enum ItemType {
    DAIRY("Dairy"),
    MEAT("Meat"),
    PRODUCE("Produce"),
    BREAD("Bread"),
    DRY_GOODS("Dry Goods"),
    CANNED_GOODS("Canned Goods"),
    FROZEN_FOODS("Frozen Foods"),
    OTHER("Other");

    private String name;

    ItemType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
       return name;
    }
}
