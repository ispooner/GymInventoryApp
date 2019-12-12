package com.example.gyminventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ItemEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_entry);
    }

    public void onItemSubmitted(View v) {
        EditText itemEntry = findViewById(R.id.entry_item_edit);
        String item = itemEntry.getText().toString().trim();
        if(item.isEmpty())
            return; //do nothing
        Intent result = new Intent();
        result.putExtra("item", item);
        setResult(RESULT_OK, result);
        finish();
    }
}
