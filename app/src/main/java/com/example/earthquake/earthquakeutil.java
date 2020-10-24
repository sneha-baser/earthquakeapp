package com.example.earthquake;

import android.text.TextUtils;
import android.util.Log;

import com.example.earthquake.custom_info;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public final class earthquakeutil {


    private static final String LOG_TAG =   earthquakeutil.class.getSimpleName();

    //create url fun
    static public  ArrayList<custom_info> fetchearthquake(String urls){
        //create url by passing urls i.e in form of string
        URL url = createurl(urls);
        String jsonresponse=null;
        try{
            //make http request
            jsonresponse = makehttprequest(url);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
//extract feature
        ArrayList<custom_info> sample = extractFeatureFromJson(jsonresponse);

        // Return the {@link Event}
        return sample;

    }
    //create url method
    public static URL createurl(String u){
        URL url =null;
        try {
            url = new URL(u);


        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }
    //make http request method
    public static String makehttprequest(URL url) throws IOException {
        String jsonresponse =" ";
        if(url==null)
            return jsonresponse;
        HttpURLConnection urlconnection= null;
        InputStream inputstream = null;
        try {
            urlconnection = (HttpURLConnection)url.openConnection();
            urlconnection.setReadTimeout(10000);
            urlconnection.setConnectTimeout(15000);
            urlconnection.setRequestMethod("GET");
            urlconnection.connect();
            if(urlconnection.getResponseCode()==200){
                InputStream inputStream = urlconnection.getInputStream();
                jsonresponse = readFromStream(inputStream);

            }

            else{
                Log.e(LOG_TAG, "Error response code: " + urlconnection.getResponseCode());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (urlconnection != null) {
                urlconnection.disconnect();
            }
            if (inputstream != null) {
                inputstream.close();
            }

        }

        return  jsonresponse;

    }

//readFromStream fun

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputstreamreader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputstreamreader);
            String line = bufferedReader.readLine();
            while(line!=null){
                output.append(line);
                line = bufferedReader.readLine();
            }

        }

        return output.toString();
    }

//extract feature
    private static ArrayList<custom_info> extractFeatureFromJson(String earthquakeJSON) {
        // If the JSON string is empty or null, then return early.
        int i;
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }
        ArrayList<custom_info> earthquakes = new ArrayList<custom_info>();
        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray featureArray = baseJsonResponse.getJSONArray("features");

            // If there are results in the features array
            for(i=0;i<featureArray.length();i++){

                JSONObject currentearthquake = featureArray.getJSONObject(i);
                JSONObject properties = currentearthquake.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");

                // Extract the value for the key called "place"
                String location = properties.getString("place");

                // Extract the value for the key called "time"
                long time = properties.getLong("time");

                // Extract the value for the key called "url"
                String url = properties.getString("url");


                // Create a new {@link Event} object
                custom_info erth= new custom_info(magnitude, location,time , url);
                earthquakes.add(erth);

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }


}
