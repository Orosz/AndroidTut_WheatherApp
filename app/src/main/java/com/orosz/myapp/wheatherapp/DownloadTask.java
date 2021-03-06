package com.orosz.myapp.wheatherapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... urls) {

        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            while (data != -1) {

                char current = (char) data;

                result += current;

                data = reader.read();

            }

            return result;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {

            String message = "";

            JSONObject jsonObject = new JSONObject(result);

            String weatherInfo = jsonObject.getString("weather");

            Log.i("Weather content", weatherInfo);

            JSONArray arr = new JSONArray(weatherInfo);

            for (int i = 0; i < arr.length(); i++) {

                JSONObject jsonPart = arr.getJSONObject(i);

                String main = "";
                String description = "";

                main = jsonPart.getString("main");
                description = jsonPart.getString("description");

                //Log.i("main", jsonPart.getString("main"));
                //Log.i("description", jsonPart.getString("description"));

                if (!main.isEmpty() && !description.isEmpty()) {
                    message = main + ":\n" + description;

                }

            }

            if (!message.isEmpty()) {

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
