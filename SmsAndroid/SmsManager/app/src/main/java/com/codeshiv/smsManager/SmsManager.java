package com.codeshiv.smsManager;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SmsManager {

    private static SmsManager smsManager = null;
    private TextView textView = null;
    private MainActivity mainActivity = null;
    private List<Sms> lstSms = new ArrayList<>();
    private String messageOnUI = null;

    private SmsManager(){

    }

    public static SmsManager getSmsManager(){
        if(smsManager == null){
            smsManager = new SmsManager();
        }
        return smsManager;
    }

    public void initializeComponents(MainActivity mainActivity, TextView textView){

        this.textView = textView;
        this.mainActivity = mainActivity;

        getSmsFromDevice();
    }

    private void getSmsFromDevice(){
        Sms objSms;
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = mainActivity.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        mainActivity.startManagingCursor(c);
        assert c != null;
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c
                        .getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }

                lstSms.add(objSms);
                c.moveToNext();
            }
        }
        c.close();

        populateTextView();
    }

    private void populateTextView(){

        messageOnUI = "";

        for(Sms sms : lstSms){
            messageOnUI = messageOnUI.concat(sms.getAddress()+"\n");
        }

        textView.setText(messageOnUI);
    }
}
