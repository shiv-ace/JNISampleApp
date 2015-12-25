package com.codeshiv.jsonparser;

import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonUtil {

    public static JsonUtil jsonUtil;
    private String result = null;
    private String Url = null;
    private Thread httpThread;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private JsonApplication jsonApplication;
    private MainActivity mainActivity;
    private final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil(){
        // Designed to make sure that Singleton instance of the class persists in the application
    }

    public static JsonUtil getClassInstance(){
        if(jsonUtil == null){
            jsonUtil = new JsonUtil();
        }
        return jsonUtil;
    }

    public void setUrl(String url){
        jsonApplication = JsonApplication.getJsonApplication();
        mainActivity = MainActivity.getMainActivity();
        Url = url;
    }

    public void startHttpThread(){
        httpThread = new Thread(httpRunnable);
        httpThread.start();
    }

    private Runnable httpRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                getJson();
                parseJson();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void getJson(){
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;
        try
        {
            url = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // just want to do an HTTP GET here
            connection.setRequestMethod("GET");
            // give it 15 seconds to respond
            connection.setReadTimeout(15*1000);
            connection.connect();
            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }
            result = stringBuilder.toString();
            postToUI(result);
        }
        catch (Exception e)
        {
            logger.debug("Error : "+e.getLocalizedMessage());
            e.printStackTrace();
            postToUI(e.getLocalizedMessage());
        }
        finally
        {
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        }
    }

    private void parseJson(){
        try {
            System.out.println("JSON on the Http Server : "+result);
            jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("accounts");

            JSONObject jsonObject1 = jsonArray.getJSONObject(0);

            JSONObject jsonObject2 = jsonObject1.getJSONObject("telephony-settings");

            String exclusion = jsonObject2.getString("exclusion");

            System.out.println("JSON : "+exclusion);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void postToUI(String text){
        if(text != null){
            mainActivity.setMessageOnUI(text);
            Message message = Message.obtain();
            message.obj = mainActivity.getMessageOnUI();
            mainActivity.ServerResponse.sendMessage(message);
        }
    }
}