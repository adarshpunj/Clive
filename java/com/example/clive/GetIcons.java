package com.example.clive;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

public class GetIcons extends AsyncTask<String, Integer, Bitmap> {
    String data = " ";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        MainActivity.icon.setImageBitmap(bitmap);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String i = strings[0];
        String imgURL = getURLasString(i);
        try {
            URL url = new URL(imgURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream input = httpURLConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        }catch (MalformedInputException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    public String getURLasString(String string){
        switch (string){
            case "clear sky":
                //
            case "few clouds":
               return "https://drive.google.com/uc?authuser=0&id=1k2pwpnfDPURAaAJ1rO8feA-USoqZDQPU&export=download";
            case "light rain":
            case "shower rain":
                return "https://drive.google.com/uc?authuser=0&id=1AnLMq0tCxefWLakoTHKaSFbLsQAmkFU2&export=download";
            case "scattered clouds":
                return "https://drive.google.com/uc?authuser=0&id=17Myf4zVNRt1IcxVsOZKhIzWIM_oO16yO&export=download";
            case "broken clouds":
                return "https://drive.google.com/uc?authuser=0&id=1EPgZ1EYRAQXxEoRQAwfSkT0rKoGymV1Z&export=download";
            case "thunderstorm":
                return "https://drive.google.com/uc?authuser=0&id=13nxpyCKFLPlrQ9SRj1_YH8QMpbWO62xL&export=download";
            default:
                return "https://drive.google.com/uc?authuser=0&id=1KFBbjDT02FCHwhp22tRo-prGbdSGNo5X&export=download";
        }
    }
}
