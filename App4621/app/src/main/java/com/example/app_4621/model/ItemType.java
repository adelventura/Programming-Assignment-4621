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

    public static ItemType getEnumFromString(String name) {
        for(ItemType e: ItemType.values()) {
            if(e.name.equals(name)) {
                return e;
            }
        }
        return null;// not found
    }

    @Override
    public String toString() {
       return name;
    }
}
