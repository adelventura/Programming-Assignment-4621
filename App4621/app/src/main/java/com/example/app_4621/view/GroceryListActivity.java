package com.example.app_4621.view;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_4621.R;
import com.example.app_4621.Util;
import com.example.app_4621.data.DbRepository;
import com.example.app_4621.model.Item;
import com.example.app_4621.model.ItemType;
import com.example.app_4621.vm.GroceryListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class GroceryListActivity extends AppCompatActivity implements AddDialogFragment.AddDialogListener, GroceryListViewModel.Listener, SwipeToDeleteCallback.Listener {
    private DbRepository itemRepository = new DbRepository();
    private FirebaseAuth auth;
    private GroceryListViewModel vm;

    private RecyclerView recyclerView;
    private ItemRecyclerViewAdapter recyclerViewAdapter;
    private Spinner sortSpinner;
    private ArrayAdapter sortSpinnerAdapter;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        verifyLoginStatus();

        Util.themeStatusBar(this, true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("Groceries");

        this.vm = new GroceryListViewModel(this, itemRepository);
        this.vm.listener = this;

        initRecyclerView();
        itemRepository.load();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new ItemRecyclerViewAdapter(this, vm);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        initSpinner(recyclerViewAdapter);

        SwipeToDeleteCallback callback = new SwipeToDeleteCallback(recyclerViewAdapter);
        callback.listener = this;
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initSpinner(ItemRecyclerViewAdapter adapter) {
        sortSpinner = (Spinner) findViewById(R.id.sort_spinner);
        sortSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vm.getSortTypes());
        sortSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortSpinnerAdapter);

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
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void verifyLoginStatus() {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) {
            Log.d("TAG", "confirmLoginStatus: uid = " + uid);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
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
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            case R.id.add:
                showAddDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        Item item = new Item(itemName, Integer.parseInt((itemQuantity)), ItemType.getEnumFromString(itemType));
        itemRepository.addItem(item);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "Don't add", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdated() {
        // Update recycler view
        recyclerViewAdapter.notifyDataSetChanged();

        // Update spinner
        sortSpinnerAdapter.clear();
        sortSpinnerAdapter.addAll(vm.getSortTypes());
    }

    @Override
    public void onItemDeleted(int position) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String key = database.getReference("/groceries").push().getKey();
        Item item = vm.getItemList().get(position).item;
        itemRepository.deleteItem(item);
    }
}