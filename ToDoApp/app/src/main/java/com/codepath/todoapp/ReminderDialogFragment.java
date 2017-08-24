package com.codepath.todoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_dropdown_item;

/**
 * Created by rashmisharma on 8/17/17.
 */

public class ReminderDialogFragment extends DialogFragment {

    private EditText name;
    private EditText date;
    private Spinner priority;

    public ReminderDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ReminderDialogFragment newInstance(String title) {
        ReminderDialogFragment frag = new ReminderDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_edit_reminder, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get field from view
        name = (EditText) view.findViewById(R.id.name);
        date = (EditText) view.findViewById(R.id.date);
        priority = (Spinner) view.findViewById(R.id.priority);
        List<String> list = new ArrayList<String>();
        list.add("HIGH");
        list.add("MODERATE");
        list.add("LOW");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),simple_spinner_dropdown_item ,list);
        dataAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
        priority.setAdapter(dataAdapter);

        Button add = (Button) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem(view);
            }
        });

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Save Reminder");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        name.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void saveItem(View view){


        Reminder r= new Reminder();
        r.setName(name.getText().toString());
        r.setPriority(priority.getSelectedItem().toString());

        String d=date.getText().toString();
       java.util.Date d1= new java.util.Date(d);
        r.setDate(d1);
        ReminderDataBaseHandler rdb= ReminderDataBaseHandler.getInstance(getActivity());
        rdb.addReminder(r);
        dismiss();


    }
}