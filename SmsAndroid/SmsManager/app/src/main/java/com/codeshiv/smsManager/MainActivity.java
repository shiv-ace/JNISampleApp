package com.codeshiv.smsManager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText editTextNumber;
    private EditText editTextMessage;
    private Button button;
    private static MainActivity mainActivity = null;
    private SMSManager smsManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNumber = (EditText)findViewById(R.id.editTextNumber);
        editTextMessage = (EditText)findViewById(R.id.editTextMessage);
        button = (Button)findViewById(R.id.sendButton);
        mainActivity = this;

        smsManager = SMSManager.getSmsManager();
        smsManager.initializeComponents(mainActivity,editTextNumber,editTextMessage,button);
    }
}
