package com.example.collegephonebook;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ContactAdapter contactAdapter;
    private ArrayList<Contact> contacts;
    private ArrayList<String> contactNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        EditText textbox = findViewById(R.id.textbox);
        EditText searchBar = findViewById(R.id.search_bar);
        ListView contactList = findViewById(R.id.contact_list);
        Button addContactButton = findViewById(R.id.add_contact_button);

        
        // Initialize the contact list with sample data
        contacts = new ArrayList<>();
        contactNames = new ArrayList<>();

        contacts.add(new Contact("Rakesh Singh", "+917546749756", "rakesh440@gmail.com", "123 Street, City"));
        contacts.add(new Contact("Rohan Roy", "+916987654321", "rohan652@example.com", "456 Avenue, City"));
        contacts.add(new Contact("Saurav Raj", "+917865435464", "saurav992@gmail.com", "789 Boulevard, City"));

        // Populate contactNames list for display in ListView
        for (Contact contact : contacts) {
            contactNames.add(contact.getName());
        }

        // Set up the adapter for the ListView
        contactAdapter = new ContactAdapter(this, contacts);
        contactList.setAdapter(contactAdapter);

        // Add Contact button click listener
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddContactDialog();
            }
        });

        // List item click listener to show contact details
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact selectedContact = contacts.get(position);
                showContactDetailsDialog(selectedContact);
            }
        });

        // Search functionality
        searchBar.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                // Not used
            }
        });
    }

    // Show a dialog with full contact details
    private void showContactDetailsDialog(Contact contact) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(contact.getName());

        String contactDetails = "Phone: " + contact.getPhoneNumber() + "\n"
                + "Email: " + contact.getEmail() + "\n"
                + "Address: " + contact.getAddress();
        builder.setMessage(contactDetails);

        builder.setPositiveButton("OK", null);
        builder.show();
    }

    // Show a dialog to add a new contact using the hidden add_contact_layout in activity_main.xml
    // Show a dialog to add a new contact using the custom add_contact_dialog layout
    private void showAddContactDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Add New Contact");

        // Inflate the custom add_contact_dialog layout
        final View customLayout = getLayoutInflater().inflate(R.layout.add_contact_dialog, null);

        // Set the layout in the dialog
        builder.setView(customLayout);

        builder.setPositiveButton("Add", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                EditText nameInput = customLayout.findViewById(R.id.name_input);
                EditText phoneInput = customLayout.findViewById(R.id.phone_input);
                EditText emailInput = customLayout.findViewById(R.id.email_input);
                EditText addressInput = customLayout.findViewById(R.id.address_input);

                String name = nameInput.getText().toString().trim();
                String phone = phoneInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String address = addressInput.getText().toString().trim();

                if (!name.isEmpty()) {
                    Contact newContact = new Contact(name, phone, email, address);
                    contacts.add(newContact);
                    contactNames.add(newContact.getName());
                    contactAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Contact Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Contact name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }
}

