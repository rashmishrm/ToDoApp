package com.codepath.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Reminder> reminders;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdaptor;
    ListView lvItems;
    ReminderAdaptor adaptor=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Construct the data source
         reminders = new ArrayList<Reminder>();
// Create the adapter to convert the array to views
        adaptor = new ReminderAdaptor(this, reminders);
// Attach the adapter to a ListView
         lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(adaptor);

        readItems();

 setUpViewListener();


    }

    private void readItems(){


        ReminderDataBaseHandler rdb= ReminderDataBaseHandler.getInstance(this);
        reminders=rdb.getAllReminders();
        adaptor.addAll(reminders);


    }

    private void remove(Reminder reminder){
        ReminderDataBaseHandler rdb= ReminderDataBaseHandler.getInstance(this);
        rdb.deleteReminder(reminder.getId());

    }

    private void setUpViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Reminder r= reminders.get(i);
                adaptor.remove(r);
                //reminders.remove(i);
                //adaptor.notifyDataSetChanged();
                remove(r);
                return true;
            }
        });

//        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(MainActivity.this, EditActivity.class);
//                intent.putExtra("item", items.get(i));
//                intent.putExtra("position", i);
//                //startActivity(intent);
//                startActivityForResult(intent, 20);
//
//
//            }
//        });
    }
    private void showEditDialog() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        ReminderDialogFragment editNameDialogFragment = ReminderDialogFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }


public void openDialog(View view){
    showEditDialog();

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
