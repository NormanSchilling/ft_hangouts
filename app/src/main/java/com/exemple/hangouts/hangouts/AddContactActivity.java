package com.exemple.hangouts.hangouts;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exemple.hangouts.hangouts.BDD.Contact;
import com.exemple.hangouts.hangouts.BDD.ContactDAO;

import java.lang.reflect.Field;


public class AddContactActivity extends ActionBarActivity {

    EditText            nameContactField;
    EditText            numeroContactField;
    Button              saveContactButton;
    Contact             contact;
    ContactDAO          contactManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        nameContactField = (EditText)findViewById(R.id.nameContactField);
        numeroContactField = (EditText)findViewById(R.id.numeroContactField);
        saveContactButton = (Button)findViewById(R.id.saveContactButton);

        saveContactButton.setOnClickListener(saveContactButtonListener);
    }

    View.OnClickListener saveContactButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            contact = new Contact(nameContactField.getText().toString(), numeroContactField.getText().toString());
            contactManager = new ContactDAO(v.getContext());
            contactManager.add(contact);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
