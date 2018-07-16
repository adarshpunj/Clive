package com.example.clive;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.text.DecimalFormat;

public class LocationProcess extends AsyncTask<LocationData,Void,LocationData>{
    Context context;
    String data = " ";

    public LocationProcess(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(LocationData locationData) {
        super.onPostExecute(locationData);
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject sys = jsonObject.getJSONObject("sys");
            JSONObject main = jsonObject.getJSONObject("main");

            String country = sys.getString("country");
            String name = jsonObject.getString("name");
            String tmp = main.getString("temp");

            JSONArray jsonArray = new JSONArray(jsonObject.getString("weather"));
            JSONObject weather = jsonArray.getJSONObject(0);
            String description = weather.getString("description");

            double temperature = Double.parseDouble(tmp);
            temperature = temperature-273.15;
            DecimalFormat decimalFormat = new DecimalFormat("#");
            decimalFormat.setRoundingMode(RoundingMode.CEILING);

            MainActivity.temperature.setText(""+decimalFormat.format(temperature)+"º");
            MainActivity.geography.setText(name+", "+country);

            if(!MainActivity.night){
                new GetIcons().execute(description);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected LocationData doInBackground(LocationData... locationData) {
        String latitude = locationData[0].getmLatitude();
        String longitude = locationData[0].getmLongitude();
        String owmURL = "http://api.openweathermap.org/data/2.5/weather?lat="+latitude+
                "&lon="+longitude+"&appid=c2675cc27e2d55338119291273f23807";
        try {
            URL url = new URL(owmURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                data = data + line;
            }
        }catch (MalformedInputException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}