package com.exemple.hangouts.hangouts;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exemple.hangouts.hangouts.BDD.Contact;
import com.exemple.hangouts.hangouts.BDD.ContactDAO;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class smsSendActivity extends AppCompatActivity {

    List<String>         listSms;
    ListView             smsListView;
    TextView             nameContactSmsSend;
    EditText             smsSendEditText;
    Button               smsSendButton;
    Intent               parentIntent;
    Contact              contact;
    ContactDAO           contactManager;
    PhoneNumberUtils     phoneUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_send);
        listSms = new ArrayList<>();
        smsListView = (ListView) findViewById(R.id.smsListView);
        nameContactSmsSend = (TextView) findViewById(R.id.nameContactSmsSend);
        smsSendEditText = (EditText) findViewById(R.id.smsSendEditText);
        smsSendButton = (Button) findViewById(R.id.sendMessageButton);
        parentIntent = getIntent();
        contact = parentIntent.getParcelableExtra("contact");
        nameContactSmsSend.setText(contact.getName());
        smsSendButton.setOnClickListener(smsSendButtonListener);

    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        long timeMillis = smsInboxCursor.getColumnIndex("date");
        Date date = new Date(timeMillis);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        String dateText = format.format(date);

        smsInboxCursor.moveToLast();
        while (smsInboxCursor.moveToPrevious())
        {
            String numero = smsInboxCursor.getString(smsInboxCursor.getColumnIndex("address"));
            numero = numero.replace("+33", "0");
            if (contact.getNumero().equals(numero)) {
                listSms.add(smsInboxCursor.getString(smsInboxCursor.getColumnIndex("body")));
            }
        }

        SmsAdapter adapter = new SmsAdapter(this, R.layout.cursor_sms, R.id.smsTextView, listSms);
        smsListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSmsInbox();
    }

    protected void sendSMS() {
        String smsMessage = smsSendEditText.getText().toString();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(contact.getNumero(), null, smsMessage, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    View.OnClickListener smsSendButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendSMS();
        }
    };

    public class SmsAdapter extends ArrayAdapter<String>
    {
        public SmsAdapter(Context context, int resource, int id_view, List<String> objects)
        {
            super(context, resource, id_view, objects);
        }
    }

}
