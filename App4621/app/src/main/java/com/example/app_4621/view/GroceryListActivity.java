package com.example.app_4621.view;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_4621.R;
import com.example.app_4621.Util;
import com.example.app_4621.model.ItemType;
import com.example.app_4621.vm.GroceryListViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroceryListActivity extends AppCompatActivity implements AddDialogFragment.AddDialogListener {
    private RecyclerView recyclerView;
    private ItemRecyclerViewAdapter recyclerViewAdapter;
    private GroceryListViewModel vm;
    private ConstraintLayout layout;
    private Spinner sortSpinner;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        layout = findViewById(R.id.layout);

        Util.themeStatusBar(this, true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addBackArrow();
        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("Groceries");

        this.vm = new GroceryListViewModel(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new ItemRecyclerViewAdapter(this, vm);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        initSpinner(recyclerViewAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(recyclerViewAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initSpinner(ItemRecyclerViewAdapter adapter) {
        List<String> sortOptions = new ArrayList<>();
        sortOptions.add("All");
        ItemType[] types = ItemType.values();
        for (ItemType t: types) {
            sortOptions.add(t.toString());
        }
        sortSpinner = (Spinner)findViewById(R.id.sort_spinner);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortOptions);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(spinnerAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    recyclerViewAdapter.sort(null);
                } else {
                    String type = sortSpinner.getSelectedItem().toString();
                    recyclerViewAdapter.sort(ItemType.getEnumFromString(type));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.finish();
                return true;
            case R.id.add:
                showAddDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addBackArrow() {
        // back arrow to left
        if (this.getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    public void showAddDialog() {
        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_add_item");

        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }
        AddDialogFragment dialog = new AddDialogFragment();
        dialog.show(manager, "fragment_add_item");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String itemName, String itemQuantity, String itemType) {
        try {
            recyclerViewAdapter.addItem(itemName, Integer.parseInt(itemQuantity), itemType);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "Don't add", Toast.LENGTH_SHORT).show();
    }
}