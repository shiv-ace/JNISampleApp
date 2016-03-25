//package com.codeshiv.smsManager;
//
//import android.telephony.SmsManager;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//public class SMSManager implements View.OnClickListener{
//
//    private static SMSManager sms = null;
//    private SmsManager smsManager = null;
//    private MainActivity mainActivity = null;
//    private EditText editTextNumber = null;
//    private EditText editTextMessage = null;
//    private Button button = null;
//
//    private SMSManager(){
//
//    }
//
//    public static SMSManager getSmsManager(){
//        if(sms == null){
//            sms = new SMSManager();
//        }
//        return sms;
//    }
//
//    public void initializeComponents(MainActivity mainActivity,EditText editTextNumber,EditText editTextMessage,Button button){
//
//        this.mainActivity = mainActivity;
//        this.editTextMessage = editTextMessage;
//        this.editTextNumber = editTextNumber;
//        this.button = button;
//
//        button.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        if(v.getId() == R.id.sendButton){
//            sendMessage(editTextNumber.getText().toString(),editTextMessage.getText().toString());
//        }
//    }
//
//    private void sendMessage(String number, String message){
//
//        smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage(number,null,message,null,null);
//    }
//}
