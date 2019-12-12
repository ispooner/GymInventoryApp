package com.example.gyminventoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<GymItem> items = new ArrayList<>();
        items.add(new GymItem("", "Dumbbells", 4));
        items.add(new GymItem("", "Treadmills", 0));
        items.add(new GymItem("", "Medicine balls", 3));
        items.add(new GymItem("", "Add new item"));

        itemList = findViewById(R.id.item_list_view);
        inventoryList = findViewById(R.id.inventory_list_view);

        ItemAdapter itemAdapter = new ItemAdapter(this, R.layout.gym_item_entry, items);
        InventoryAdapter inventoryAdapter = new InventoryAdapter(this, R.layout.gym_item_entry, items);

        itemList.setAdapter(itemAdapter);
        inventoryList.setAdapter(inventoryAdapter);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


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
            convertView.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
}
