package com.codepath.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {


    String text="";
    int position=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        text = getIntent().getStringExtra("item");
        position= getIntent().getIntExtra("position",0);
        EditText editText= (EditText) findViewById(R.id.editText);
        editText.setText(text);

    }


    public void save(View view){
        EditText editText= (EditText) findViewById(R.id.editText);
        String text=  editText.getText().toString();
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("item", text);
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
    }
}
