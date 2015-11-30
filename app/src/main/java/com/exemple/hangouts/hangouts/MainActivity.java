package com.exemple.hangouts.hangouts;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.exemple.hangouts.hangouts.BDD.Contact;
import com.exemple.hangouts.hangouts.BDD.ContactDAO;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "SaveHour";
    private static final String HOUR = "Hour";
    boolean             displayHour = false;
    Button              addContactButton;
    ContactDAO          contactManager;
    Cursor              contactCursor;
    ListView            listViewContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setBackgroundDrawable(new ColorDrawable(Color.RED));
        }
        addContactButton = (Button)findViewById(R.id.addContactButton);
        listViewContact = (ListView)findViewById(R.id.contactListView);
        addContactButton.setOnClickListener(addContactButtonListener);
        contactManager = new ContactDAO(getBaseContext());
        contactManager.open();
        contactCursor = contactManager.selectAll();
        ContactCursorAdapter contactAdapter = new ContactCursorAdapter(this, contactCursor);
        // Attach cursor adapter to the ListView
        listViewContact.setAdapter(contactAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences mPrefs = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        prefsEditor.putInt(HOUR, hour);
        prefsEditor.commit();
        displayHour = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactCursor = contactManager.selectAll();
        ContactCursorAdapter contactAdapter = new ContactCursorAdapter(this, contactCursor);
        // Attach cursor adapter to the ListView
        listViewContact.setAdapter(contactAdapter);

        if (displayHour) {
            SharedPreferences mPrefs = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
            int hour = mPrefs.getInt(HOUR, 0);
            Toast.makeText(getApplicationContext(), String.valueOf(hour), Toast.LENGTH_SHORT).show();
            displayHour = false;
        }

    }

    View.OnClickListener addContactButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(v.getContext(),AddContactActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        ActionBar bar = getSupportActionBar();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_green) {
            if (bar != null) {
                bar.setBackgroundDrawable(new ColorDrawable(Color.GREEN));
            }
            return true;
        }
        if (id == R.id.action_red) {
            if (bar != null) {
                bar.setBackgroundDrawable(new ColorDrawable(Color.RED));
            }
            return true;
        }
        if (id == R.id.action_blue) {
            if (bar != null) {
                bar.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class ContactItemListener implements AdapterView.OnItemClickListener
    {
        private Cursor      cursor;
        private Contact     contact;

        ContactItemListener(Cursor cursorContact)
        {
            cursor = cursorContact;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(view.getContext(),ProfileActivity.class);
            contact = new Contact(cursor.getLong(cursor.getColumnIndexOrThrow("_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("numero")));
            intent.putExtra("contact", contact);
            startActivity(intent);
        }
    }

    public class ContactCursorAdapter extends CursorAdapter{

        public ContactCursorAdapter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.cursor_contact, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            listViewContact.setOnItemClickListener(new ContactItemListener(cursor));
            TextView nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            TextView numeroTextView = (TextView) view.findViewById(R.id.numeroTextView);
            // Extract properties from cursor
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String numero = cursor.getString(cursor.getColumnIndexOrThrow("numero"));
            // Populate fields with extracted properties
            nameTextView.setText(name);
            numeroTextView.setText(numero);
        }
    }
}
