package com.codeshiv.smsManager;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private EditText editTextNumber;
    private EditText editTextMessage;
    private Button button;
    private static MainActivity mainActivity = null;
//    private SMSManager smsManager = null;
    private Credentials credentials = null;

    private MyPageAdapter pageAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> fragments = getFragments();
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        ViewPager pager =
                (ViewPager)findViewById(R.id.viewPager);
        pager.setAdapter(pageAdapter);


        Button button = (Button)findViewById(R.id.login);

        button.setOnClickListener(this);
    }

    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(MyFragment.newInstance("Fragment 1"));
        fList.add(MyFragment.newInstance("Fragment 2"));
        fList.add(MyFragment.newInstance("Fragment 3"));

        return fList;
    }

    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.login){
            credentials = Credentials.getCredentials();
            credentials.connect("https://v4-dev.getsigneasy.com/v4/token",
                    "darthvader@starwars.com","vader");
        }
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }
}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        editTextNumber = (EditText)findViewById(R.id.editTextNumber);
//        editTextMessage = (EditText)findViewById(R.id.editTextMessage);
//        button = (Button)findViewById(R.id.sendButton);
//        mainActivity = this;
//
//        credentials = Credentials.getCredentials();
//
//        credentials.connect("https://v4-dev.getsigneasy.com/v4/token",
//                "darthvader@starwars.com","vader");
//
////        smsManager = SMSManager.getSmsManager();
////        smsManager.initializeComponents(mainActivity,editTextNumber,editTextMessage,button);
////        readContact();
//    }
//
//    private void readContact(){
//        ContentResolver contentResolver = getContentResolver();
//        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//        while(cursor.moveToNext()){
//            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//            System.out.println(contactName);
//        }
//    }
