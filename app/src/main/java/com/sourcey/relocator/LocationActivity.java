package com.sourcey.relocator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class LocationActivity extends AppCompatActivity {

    private TextView first_city;
    private ImageView first_city_image;
    private TextView second_city;
    private ImageView second_city_image;
    private CardView city1;
    private CardView city2;
    private Button recommendation_1;
    private Button recommendation_2;

    private JSONObject city1Json;
    private JSONObject city2Json;
    private String jsonResponse;
    private String locationType;

    private Button navigateCity1;

    private String city1name;
    private String city2name;
    private int userId;

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void handleCity1Click() {
        System.out.println(city1Json);
        Intent intent = new Intent(LocationActivity.this, place_details.class);
        intent.putExtra("jsonResponse",city1Json.toString());
        startActivity(intent);
    }

    private void handleSecondCityClick() {
        System.out.println(city2Json);
        Intent intent = new Intent(LocationActivity.this, place_details.class);
        intent.putExtra("jsonResponse",city2Json.toString());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        Intent intent = getIntent();
        jsonResponse = intent.getStringExtra("jsonResponse");
        locationType = intent.getStringExtra("locationType");
        userId = intent.getIntExtra("userId", 0);

        navigateCity1 = findViewById(R.id.navigate_first_city);
        recommendation_1 = findViewById(R.id.recommendations_1);
        recommendation_2 = findViewById(R.id.recommendations_2);

        String image1URL;
        String image2URL;

        try {
            JSONObject cities = new JSONObject(jsonResponse);
            city1Json = cities.getJSONObject("city1");
            city2Json = cities.getJSONObject("city2");

            first_city = (TextView)findViewById(R.id.first_city_name);
            city1name = String.valueOf(city1Json.getString("location").charAt(0)).toUpperCase() + city1Json.getString("location").substring(1, city1Json.getString("location").length());

            first_city.setText(city1name);
            image1URL = city1Json.getString("image_url");

            second_city = (TextView)findViewById(R.id.second_city_name);
            city2name = String.valueOf(city2Json.getString("location").charAt(0)).toUpperCase() + city2Json.getString("location").substring(1, city2Json.getString("location").length());
            second_city.setText(city2name);
            image2URL = city2Json.getString("image_url");
            city1 = findViewById(R.id.first_city_container);
            city1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleCity1Click();
                }
            });
            city2 = findViewById(R.id.second_city_container);
            city2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleSecondCityClick();
                }
            });
        }
        catch (JSONException j){
            Log.e("JSONError", j.toString());
        }

        recommendation_1.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecommendations(city1name, city2name);
            }
        }));

        recommendation_2.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecommendations(city1name, city2name);
            }
        }));
    }

    private void getRecommendations(String city1name, String city2name){
        JSONObject param = new JSONObject();
        ArrayList<String> cities = new ArrayList<String>();
        cities.add(city1name);
        cities.add(city2name);

        String url = "";
        try{
            param.put("userId", userId);
            param.put("type", locationType);
            param.put("alreadyPresentCities", new JSONArray(cities));
            Log.i("Location params", param.toString());
            ConnectTask conn = new ConnectTask(url, param);
            conn.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    class ConnectTask extends AsyncTask<Void, Void, Boolean> {

        private final String url;
        private final JSONObject jsonParam;

        public ConnectTask(String url, JSONObject jsonParam) {

            this.url = url;
            this.jsonParam = jsonParam;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return connectToApi(url, jsonParam);

        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if (isSuccess)
                onTaskSuccess();
            else
                onTaskFailed();
        }
    }

    private void onTaskSuccess(){
        Toast.makeText(getBaseContext(), "Task successful", Toast.LENGTH_LONG).show();
    }

    private void onTaskFailed(){
        Toast.makeText(getBaseContext(), "Task failed", Toast.LENGTH_LONG).show();
    }

    private boolean connectToApi(String url, JSONObject param){
        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);


            Log.i("JSON", param.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(param.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG", conn.getResponseMessage());
            System.out.println(conn.getResponseCode() + " " + conn.getResponseMessage());

            if (conn.getResponseCode() == 200) {
                return true;
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;
        String url;

        public DownLoadImageTask(String url, ImageView imageView){
            this.url = url;
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = url;
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}

            /*new DownloadImageTask((ImageView) findViewById(R.id.first_city_image))
                    .execute(city1Json.getString("image_url"));*/

    /*private void navigateToCity(String destination){
        DateTime now = new DateTime();
        DirectionsResult result = DirectionsApi.newRequest(getGeoContext()).mode(TravelMode.DRIVING).origin(origin).destination(destination).departureTime(now).await();
        private GeoApiContext getGeoContext() {
            GeoApiContext geoApiContext = new GeoApiContext();
            return geoApiContext.setQueryRateLimit(3).setApiKey(getString(R.string.directionsApiKey)).setConnectTimeout(1, TimeUnit.SECONDS);              .setReadTimeout(1, TimeUnit.SECONDS)                .setWriteTimeout(1, TimeUnit.SECONDS);
        };
    }*/


            /*new DownloadImageTask((ImageView) findViewById(R.id.first_city_image))
                    .execute(city2Json.getString("image_url"));*/
            /*DownLoadImageTask d = new DownLoadImageTask(image1URL, first_city_image);
            d.execute();

            d = new DownLoadImageTask(image2URL, second_city_image);
            d.execute();*/

            /*navigateCity1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        navigateToCity(city1Json.getString("location"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });*/
