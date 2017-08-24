package com.codepath.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rashmisharma on 8/16/17.
 */

public class ReminderAdaptor extends ArrayAdapter<Reminder> {
    public ReminderAdaptor(Context context, List<Reminder> reminders) {
        super(context, 0, reminders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Reminder reminder = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout, parent, false);
        }
        // Lookup view for data population
        TextView name = convertView.findViewById(R.id.name);
        TextView date = convertView.findViewById(R.id.date);
        TextView priority = convertView.findViewById(R.id.priority);

        // Populate the data into the template view using the data object
        name.setText(reminder.getName());
        date.setText(reminder.getDate().toString());
        priority.setText(reminder.getPriority());
        // Return the completed view to render on screen
        return convertView;
    }
}