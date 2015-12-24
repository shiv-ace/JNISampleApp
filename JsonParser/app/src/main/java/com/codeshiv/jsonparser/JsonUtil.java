package com.codeshiv.jsonparser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonUtil {

    public static JsonUtil jsonUtil;
    private String result = null;
    private String Url = null;
    private Thread httpThread;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

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
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httppost = new HttpPost(Url);
        // Depends on your web service
        httppost.setHeader("Content-type", "application/json");
        InputStream inputStream = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();

            System.out.println("JSON : "+result);
        } catch (Exception e) {
            // Oops
            e.printStackTrace();
        }
        finally {
            try{
                if(inputStream != null)
                    inputStream.close();
            }catch(Exception squish){}
        }
    }

    private void parseJson(){
        try {
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
}