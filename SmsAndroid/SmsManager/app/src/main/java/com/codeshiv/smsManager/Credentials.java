package com.codeshiv.smsManager;

import android.os.AsyncTask;
import android.util.Base64;

import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RoomEntity;
import com.google.common.hash.Hashing;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Credentials {


    public static Credentials credentials = null;


    private Credentials(){

    }

    public static Credentials getCredentials() {
        if (credentials == null) {
            credentials = new Credentials();
        }
        return credentials;
    }

    public void connect(String url,String userName, String passWrd){
        AsyncHandler asyncHandler = new AsyncHandler();
        asyncHandler.execute(url,userName,passWrd);
    }

    public class AsyncHandler extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {

            // Encryption
            byte []encodedPasswrd = Base64.encode(params[2].getBytes(),0);
            final String hashed = Hashing.sha256()
                    .hashString(encodedPasswrd.toString(), StandardCharsets.UTF_8)
                    .toString();

            String userCredentials = params[1]+":"+hashed;


            byte []encodedStringAuth = Base64.encode(userCredentials.getBytes(),0);

            URL Url = null;
            BufferedReader reader = null;
            StringBuilder stringBuilder;
            HttpURLConnection connection = null;
            try
            {
                Url = new URL(params[0]);
                connection = (HttpURLConnection) Url.openConnection();
                // just want to do an HTTP GET here
                connection.setRequestMethod("POST");
                // give it 15 seconds to respond
                connection.setReadTimeout(15*1000);

                connection.setRequestProperty ("Authorization", "Basic "+encodedStringAuth.toString());

                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setRequestProperty("Content-Length", "" + postData.getBytes().length);
                connection.setRequestProperty("Content-Language", "en-US");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.connect();

                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream ());
                wr.writeBytes (hashed);
                wr.flush ();
                wr.close ();
                System.out.println("Response Code = "+connection.getResponseCode());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }finally {
                connection.disconnect();
            }
            return null;
        }
    }
}
