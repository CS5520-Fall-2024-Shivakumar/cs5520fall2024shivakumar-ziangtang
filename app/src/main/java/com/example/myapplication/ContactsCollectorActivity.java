// ContactsCollectorActivity.java
package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;

public class ContactsCollectorActivity extends AppCompatActivity {

    private ArrayList<Contact> contactList = new ArrayList<>();
    private ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_collector);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_contacts);
        FloatingActionButton fabAddContact = findViewById(R.id.fab_add_contact);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(this, contactList);
        recyclerView.setAdapter(contactAdapter);

        fabAddContact.setOnClickListener(v -> showAddContactDialog());
    }

    private void showAddContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Contact");

        // Inflate the dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_contact, null);
        EditText editTextName = dialogView.findViewById(R.id.editText_contact_name);
        EditText editTextPhone = dialogView.findViewById(R.id.editText_contact_phone);

        builder.setView(dialogView);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = editTextName.getText().toString();
            String phone = editTextPhone.getText().toString();

            if (!name.isEmpty() && !phone.isEmpty()) {
                Contact newContact = new Contact(name, phone);
                contactAdapter.addContact(newContact);

                Snackbar.make(findViewById(R.id.recyclerView_contacts),
                                "Contact added", Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> contactAdapter.removeContact(contactList.size() - 1))
                        .show();
            } else {
                Snackbar.make(findViewById(R.id.recyclerView_contacts),
                                "Name and phone cannot be empty", Snackbar.LENGTH_SHORT)
                        .show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
