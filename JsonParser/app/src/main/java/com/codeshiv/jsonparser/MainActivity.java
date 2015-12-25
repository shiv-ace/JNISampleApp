package com.codeshiv.jsonparser;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button getJsonButton;
    private EditText jsonUrl;
    private TextView ReceivedCode;
    private JsonUtil jsonUtil = JsonUtil.getClassInstance();
    private String messageOnUI = "";
    public static MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        getJsonButton = (Button) findViewById(R.id.getJsonButton);
        getJsonButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View source) {
        if(source.getId() == R.id.getJsonButton){
            jsonUrl = (EditText)findViewById(R.id.urlForJson);
            String url = jsonUrl.getText().toString();
            jsonUtil.setUrl(url);
            jsonUtil.startHttpThread();
        }
    }

    public static MainActivity getMainActivity(){
        return mainActivity;
    }

    public void setMessageOnUI(String msg){
        this.messageOnUI = msg;
    }

    public String getMessageOnUI() {
        return messageOnUI;
    }

    public Handler ServerResponse;

    {
        ServerResponse = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ReceivedCode = (TextView) findViewById(R.id.jsonDisplay);
                ReceivedCode.setText(messageOnUI);
            }
        };
    }
}
