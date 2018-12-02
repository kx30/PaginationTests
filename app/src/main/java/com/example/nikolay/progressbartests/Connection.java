package com.example.nikolay.progressbartests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Connection {

    private int mTotalPages;

    public String getJSON(String connectionURL, int currentPage) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        try {
            URL url = new URL(connectionURL + currentPage + "&limit=10");
            // EXAMPLE http://gallery.dev.webant.ru/api/photos?page=1&limit=2

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultJson;
    }

    public int parseItems(ArrayList<Picture> pictures, String json) {

        JSONObject dataJsonObj;

        try {
            dataJsonObj = new JSONObject(json);
            JSONArray data = dataJsonObj.getJSONArray("data");

            mTotalPages = dataJsonObj.getInt("countOfPages");

            for (int i = 0; i < data.length(); i++) {
                JSONObject images = data.getJSONObject(i);

                JSONObject image = images.getJSONObject("image");

                pictures.add(new Picture(
                        images.getString("name"),
                        images.getString("description"),
                        "http://gallery.dev.webant.ru/media/" + image.getString("contentUrl")
                ));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return mTotalPages;
    }

}
