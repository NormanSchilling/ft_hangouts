package com.exemple.hangouts.hangouts.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by nono on 01/12/15.
 */
public class SmsReceiver extends BroadcastReceiver {

    private final String        ACTION_RECEIVE_SMS = "android.provider.Telephony.SMS_RECEIVED";
    private String              bodyMessage = null;
    private String              phoneNumber = null;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_RECEIVE_SMS)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");

                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1)
                {
                    bodyMessage = messages[0].getMessageBody();
                    phoneNumber = messages[0].getDisplayOriginatingAddress();
                }

                Toast.makeText(context, "Expediteur : " + phoneNumber, Toast.LENGTH_LONG).show();
                Toast.makeText(context, "Message : " + bodyMessage, Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getBodyMessage()
    {
        return this.bodyMessage;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }
}
