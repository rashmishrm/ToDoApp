package com.codepath.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> items;
    ArrayAdapter<String> itemsAdaptor;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems=(ListView) findViewById(R.id.lvItems);

        items= new ArrayList<String>();
        readItems();
        itemsAdaptor= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdaptor);
        setUpViewListener();


    }

    private void readItems(){
        File filesDir= getFilesDir();
        File todoFile = new File(filesDir,"todo1.txt");
        try {
            items= new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items= new ArrayList<>();
            e.printStackTrace();
        }

    }

    private void writeItems(){
        File filesDir= getFilesDir();
        File todoFile = new File(filesDir,"todo1.txt");
        try {
            FileUtils.writeLines(todoFile,items);
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    private void setUpViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                items.remove(i);
                itemsAdaptor.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("item", items.get(i));
                intent.putExtra("position", i);
                //startActivity(intent);
                startActivityForResult(intent, 20);


            }
        });
    }

    public void onAddItem(View view){
        EditText editText= (EditText) findViewById(R.id.etNewItem);
        String text=  editText.getText().toString();
        itemsAdaptor.add(text);
        editText.setText("");
        writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == 20) {
            // Extract name value from result extras
            String item = data.getExtras().getString("item");
            int position= data.getExtras().getInt("position",0);
            System.out.print(position);
            items.remove(position);
            if(position==0){
                items.add(0,item);
            }
            else{
                items.add(position-1,item);
            }

            itemsAdaptor.notifyDataSetChanged();


        }
    }
}
