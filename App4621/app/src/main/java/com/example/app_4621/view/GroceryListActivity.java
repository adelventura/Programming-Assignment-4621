package com.example.app_4621.view;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_4621.R;
import com.example.app_4621.Util;
import com.example.app_4621.vm.GroceryListViewModel;

public class GroceryListActivity extends AppCompatActivity implements AddDialogFragment.AddDialogListener {
    private RecyclerView recyclerView;
    private ItemRecyclerViewAdapter recyclerViewAdapter;
    private GroceryListViewModel vm;
    private ConstraintLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);
        layout = findViewById(R.id.layout);

        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

        Util.themeStatusBar(this, true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addBackArrow();

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
                //showAlertDialogButtonClicked();
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
    public void onDialogPositiveClick(DialogFragment dialog, String itemName, String itemType) {
        Toast.makeText(this, "Add " + itemName + " -- Type: " + itemType, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "Don't add", Toast.LENGTH_SHORT).show();

    }
}