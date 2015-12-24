package com.codeshiv.jsonparser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button getJsonButton;
    private EditText jsonUrl;
    private JsonUtil jsonUtil = JsonUtil.getClassInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}