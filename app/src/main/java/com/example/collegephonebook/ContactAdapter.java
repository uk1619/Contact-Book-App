package com.example.collegephonebook;

// ContactAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the contact for this position
        Contact contact = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_item, parent, false);
        }

        // Lookup views in the custom layout
        TextView contactName = convertView.findViewById(R.id.contact_name);
        ImageView profileIcon = convertView.findViewById(R.id.profile_icon);

        // Populate the data into the views
        contactName.setText(contact.getName());
        profileIcon.setImageResource(R.drawable.contact); // Set the icon to profile_placeholder or any desired image

        // Return the completed view to render on screen
        return convertView;
    }
}

