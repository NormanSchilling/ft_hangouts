package com.exemple.hangouts.hangouts;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exemple.hangouts.hangouts.BDD.Contact;
import com.exemple.hangouts.hangouts.BDD.ContactDAO;

import java.lang.reflect.Field;


public class AddContactActivity extends AppCompatActivity {

    EditText            nameContactField;
    EditText            numeroContactField;
    Button              saveContactButton;
    Contact             contact;
    ContactDAO          contactManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        contactManager = new ContactDAO(this);
        contactManager.open();
        nameContactField = (EditText)findViewById(R.id.nameContactField);
        numeroContactField = (EditText)findViewById(R.id.numeroContactField);
        saveContactButton = (Button)findViewById(R.id.saveContactButton);

        saveContactButton.setOnClickListener(saveContactButtonListener);
    }

    View.OnClickListener saveContactButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            contact = new Contact(nameContactField.getText().toString(), numeroContactField.getText().toString());
            contactManager.add(contact);
            Intent intent = new Intent(v.getContext(),MainActivity.class);
            startActivity(intent);
        }
    };


}
