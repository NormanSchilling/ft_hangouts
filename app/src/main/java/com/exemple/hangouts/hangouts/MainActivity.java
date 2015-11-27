package com.exemple.hangouts.hangouts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

import com.exemple.hangouts.hangouts.BDD.Contact;
import com.exemple.hangouts.hangouts.BDD.ContactDAO;


public class MainActivity extends ActionBarActivity {

    Button              addContactButton;
    ContactDAO          contactManager;
    Cursor              contactCursor;
    ListView            listViewContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    protected void onResume() {
        super.onResume();
        contactCursor = contactManager.selectAll();
        ContactCursorAdapter contactAdapter = new ContactCursorAdapter(this, contactCursor);
        // Attach cursor adapter to the ListView
        listViewContact.setAdapter(contactAdapter);
        listViewContact.setOnItemClickListener(new ContactItemListener(contactCursor));
    }

    View.OnClickListener addContactButtonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent=new Intent(v.getContext(),AddContactActivity.class);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ContactItemListener implements AdapterView.OnItemClickListener
    {
        private Cursor      cursor;

        ContactItemListener(Cursor cursorContact)
        {
            cursor = cursorContact;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            
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
