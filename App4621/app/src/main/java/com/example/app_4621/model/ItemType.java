package com.example.app_4621.model;

public enum ItemType {
    DAIRY,
    MEAT,
    PRODUCE,
    BREAD,
    DRY_GOODS,
    CANNED_GOODS,
    FROZEN_FOODS,
    OTHER;

    @Override
    public String toString() {
       switch(this) {
           case DAIRY:
               return "dairy";
           case MEAT:
               return "meat";
           case PRODUCE:
               return "produce";
           case BREAD:
               return "bread";
           case DRY_GOODS:
               return "dry goods";
           case CANNED_GOODS:
               return "canned goods";
           case FROZEN_FOODS:
               return "frozen foods";
           default:
               return "other";
       }
    }
}
