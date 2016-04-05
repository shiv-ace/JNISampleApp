package com.codeshiv.jninativeapp;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String s = CreateNativeCall();
        System.out.println(s);
    }

    public native String CreateNativeCall();

    static {
        System.loadLibrary("nativeApplication");
    }
}
