package com.orosz.myapp.wheatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity{

    EditText locationEditText;
    TextView resultTextView;

    public void findWeather(View view) {

        //We need the API key....no account
        //Log.i("Location", locationEditText.getText().toString());
        DownloadTask task = new DownloadTask();
        //task.execute("http://api.openweathermap.org/data/2.5/weather?q=" + locationEditText.getText().toString());

        String message = "";

        try {
            message = processJSONData(
                    task.execute("http://api.openweathermap.org/data/2.5/weather?q=" +
                            locationEditText.getText().toString()).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        resultTextView.setText(message);


    }

    public String processJSONData(String result) {
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
                return message;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationEditText = (EditText)findViewById(R.id.locationEditText);
        resultTextView = (TextView)findViewById(R.id.resultTextView);
    }

}
