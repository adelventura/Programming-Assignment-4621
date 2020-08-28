package com.example.app_4621.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.app_4621.R;
import com.example.app_4621.vm.GroceryListViewModel;

public class GroceryListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;
    private GroceryListViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        this.vm = new GroceryListViewModel(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new ItemRecyclerViewAdapter(this, vm);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(recyclerViewAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}