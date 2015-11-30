package com.exemple.hangouts.hangouts;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exemple.hangouts.hangouts.BDD.Contact;
import com.exemple.hangouts.hangouts.BDD.ContactDAO;


public class ProfileActivity extends AppCompatActivity {

    Intent                  parentIntent;
    EditText                nameProfileContactEditText;
    EditText                numeroProfileContactEditText;
    Button                  updateContactButton;
    Button                  deleteContactButton;
    Button                  smsContactButton;
    Contact                 contact;
    ContactDAO              contactManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        contactManager = new ContactDAO(this);
        contactManager.open();
        nameProfileContactEditText = (EditText) findViewById(R.id.nameProfileContactEditText);
        numeroProfileContactEditText = (EditText) findViewById(R.id.numeroProfileContactEditText);
        smsContactButton = (Button) findViewById(R.id.messageContactButton);
        updateContactButton = (Button) findViewById(R.id.updateContactButton);
        deleteContactButton = (Button) findViewById(R.id.deleteContactButton);
        parentIntent = getIntent();

        contact = parentIntent.getParcelableExtra("contact");
        nameProfileContactEditText.setText(contact.getName());
        numeroProfileContactEditText.setText(contact.getNumero());

        smsContactButton.setOnClickListener(smsContactButtonListener);
        updateContactButton.setOnClickListener(updateContactButtonListener);
        deleteContactButton.setOnClickListener(deleteContactButtonListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    View.OnClickListener smsContactButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new  Intent(v.getContext(), smsSendActivity.class);
            intent.putExtra("contact", contact);
            startActivity(intent);
        }
    };

    View.OnClickListener updateContactButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Contact contactUpdate = new Contact(contact.getId(), nameProfileContactEditText.getText().toString(), numeroProfileContactEditText.getText().toString());
            contactManager.update(contactUpdate);
            Intent intent = new Intent(v.getContext(),MainActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener deleteContactButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("id", String.valueOf(contact.getId()));
            contactManager.delete(contact.getId());
            Intent intent = new Intent(v.getContext(),MainActivity.class);
            startActivity(intent);
        }
    };

}
