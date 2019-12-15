package com.example.gyminventoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences shared;
    SharedPreferences.Editor spEditor;

    final int itemRequestCode = 500;
    final int inventoryRequestCode = 501;

    ListView itemList;
    ListView inventoryList;

    String spCount = "itemCount";
    String spItemPrefix = "item_";
    String spItemCPrefix = "itemInv_";
    ArrayList<GymItem> items;
    ItemAdapter itemAdapter;
    InventoryAdapter inventoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<>();

        shared = getSharedPreferences("com.example.GymInventory", Context.MODE_PRIVATE);
        spEditor = shared.edit();

        int size = shared.getInt(spCount, 0);
        for(int i = 0; i < size; i++) {
            items.add(new GymItem(
                    "",
                    shared.getString(spItemPrefix + i, ""),
                    shared.getInt(spItemCPrefix + i, 0)));
        }

        items.add(new GymItem("", "Add new item"));

        itemList = findViewById(R.id.item_list_view);
        inventoryList = findViewById(R.id.inventory_list_view);

        itemAdapter = new ItemAdapter(this, R.layout.gym_item_entry, items);
        inventoryAdapter = new InventoryAdapter(this, R.layout.gym_item_entry, items);

        itemList.setAdapter(itemAdapter);
        inventoryList.setAdapter(inventoryAdapter);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent start;
                String item = items.get(position).itemName;
                if(item.equals("Add new item")) {
                    start = new Intent(parent.getContext(), ItemEntryActivity.class);
                    startActivityForResult(start, itemRequestCode);
                }
                else {
                    start = new Intent(parent.getContext(), InventoryEntryActivity.class);
                    start.putExtra("itemPosReceived", position);
                    startActivityForResult(start, inventoryRequestCode);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(int i = 0; i < items.size() - 1; i++) {
            spEditor.putString(spItemPrefix + i, items.get(i).itemName);
            spEditor.putInt(spItemCPrefix + i, items.get(i).itemCount);
        }
        spEditor.putInt(spCount, items.size() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if (requestCode == itemRequestCode) {
                String item = data.getStringExtra("item");
                GymItem newItem = new GymItem("", item);
                items.add(items.size() - 1, newItem);
                itemAdapter.notifyDataSetChanged();
            }
            else if(requestCode == inventoryRequestCode) {
                int pos = data.getIntExtra("itemPosReturned", 0);
                int count = data.getIntExtra("inventory", 0);
                items.get(pos).itemCount += count;
                inventoryAdapter.notifyDataSetChanged();
            }
        }

    }
}

class ItemAdapter extends ArrayAdapter<GymItem> {

    public ItemAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public ItemAdapter(Context context, int resource, int textViewResourceID, List objects) {
        super(context, resource, textViewResourceID, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = View.inflate(this.getContext(), R.layout.gym_item_entry, null);
        TextView text = convertView.findViewById(R.id.item_text_view);
        GymItem item = this.getItem(position);
        text.setText(item.itemName);
        return convertView;
    }
}

class InventoryAdapter extends ArrayAdapter<GymItem> {

    public InventoryAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public InventoryAdapter(Context context, int resource, int textViewResourceID, List objects) {
        super(context, resource, textViewResourceID, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = View.inflate(this.getContext(), R.layout.gym_item_entry, null);
        TextView text = convertView.findViewById(R.id.item_text_view);
        GymItem item = this.getItem(position);
        text.setText(item.itemName + ": " + item.itemCount);
        if(item.itemName.equals("Add new item")) {
            convertView.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return super.getCount() - 1;
    }
}
