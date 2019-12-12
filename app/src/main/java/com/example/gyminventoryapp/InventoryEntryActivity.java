package com.example.gyminventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InventoryEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_entry);
    }

    public void onInventorySubmitted(View v) {
        EditText itemEntry = findViewById(R.id.inventory_item_edit);
        String item = itemEntry.getText().toString().trim();
        int itemPos = getIntent().getIntExtra("itemPosReceived", 0);
        if(item.isEmpty())
            return; //do nothing
        Integer count = 0;
        try {
            count = Integer.parseInt(item);
        } catch (NumberFormatException e) {
            Toast toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText("Must be integer number");
            toast.show();
        }
        Intent result = new Intent();
        result.putExtra("inventory", count);
        result.putExtra("itemPosReturned", itemPos);
        setResult(RESULT_OK, result);
        finish();
    }
}
