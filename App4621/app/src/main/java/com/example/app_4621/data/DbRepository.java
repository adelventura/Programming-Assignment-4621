package com.example.app_4621.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_4621.model.Item;
import com.example.app_4621.model.ItemType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbRepository implements ItemRepository {

    public interface Listener {
        void onUpdated();
    }

    private List<Item> items = new ArrayList<>();
    private Map<Item, String> keys = new HashMap<>();

    public Listener listener;

    public void load() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference("Grocery-App").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.w("GroceryApp", "getUser:onCancelled " + dataSnapshot.toString());
                        Log.w("GroceryApp", "count = " + String.valueOf(dataSnapshot.getChildrenCount()) + " values " + dataSnapshot.getKey());

                        items.clear();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Item item = data.getValue(Item.class);
                            keys.put(item, data.getKey());
                            items.add(item);
                        }

                        if (listener != null) {
                            listener.onUpdated();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("GroceryApp", "getUser:onCancelled", error.toException());

                    }
                });
    }

    @Override
    public Item getItem(String name) {
        for (Item item: items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public List<Item> getItems() {
        return items;
    }

    @Override
    public List<Item> getItemsOfType(ItemType type) {
        List<Item> itemsOfType = new ArrayList<>();

        for (Item item: items) {
            if (item.getType() == type) {
                itemsOfType.add(item);
            }
        }

        return itemsOfType;
    }

    public void addItem(Item item) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("Grocery-App").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, item.toFirebaseObject());
        database.getReference("Grocery-App").updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                //
            }
        });
    }

    public void deleteItem(Item item) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(keys.get(item), null);
        database.getReference("Grocery-App").updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                //
            }
        });
    }
}
