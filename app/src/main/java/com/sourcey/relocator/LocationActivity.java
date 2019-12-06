package com.sourcey.relocator;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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
    private Button bookmarkCity1;
    private Button bookmarkCity2;

    private JSONObject city1Json;
    private JSONObject city2Json;
    private String jsonResponse;
    private Button shareButton;
    private Button shareButton2;
    private String locationType;

    private Button navigateCity1;

    private String city1name;
    private String city2name;
    private int userId;
    private float rating = 0;

    private Button rateFirstCity;
    private Button rateSecondCity;

    private AlertDialog ratingDialog;
    private String rateCityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        shareButton2 = findViewById(R.id.share_second_city);
        shareButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                String txt = null;
                try {
                    if(locationType.equals("vacation")) {
                        txt = String.valueOf(city2Json.getString("location").charAt(0)).toUpperCase() + city2Json.getString("location").substring(1, city2Json.getString("location").length());
                    }else{
                        txt = String.valueOf(city2Json.getString("city").charAt(0)).toUpperCase() + city2Json.getString("city").substring(1, city2Json.getString("city").length());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Recommended Place: "+txt);
                i.putExtra(android.content.Intent.EXTRA_TEXT, "Recommended Place: "+txt);
                startActivity(Intent.createChooser(i,"Share via"));
            }
        });
        shareButton = findViewById(R.id.share_first_city);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                String txt = null;
                try {
                    if(locationType.equals("vacation")){
                        txt = String.valueOf(city1Json.getString("location").charAt(0)).toUpperCase() + city1Json.getString("location").substring(1, city1Json.getString("location").length());
                    }else{
                        txt = String.valueOf(city1Json.getString("city").charAt(0)).toUpperCase() + city1Json.getString("city").substring(1, city1Json.getString("city").length());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Recommended Place: "+txt);
                i.putExtra(android.content.Intent.EXTRA_TEXT, "Recommended Place: "+txt);
                startActivity(Intent.createChooser(i,"Share via"));
            }
        });
        Intent intent = getIntent();
        jsonResponse = intent.getStringExtra("jsonResponse");
        locationType = intent.getStringExtra("locationType");
        userId = intent.getIntExtra("userId", 0);

        createDialog();

        navigateCity1 = findViewById(R.id.navigate_first_city);
        recommendation_1 = findViewById(R.id.recommendations_1);
        recommendation_2 = findViewById(R.id.recommendations_2);
        first_city_image = findViewById(R.id.first_city_image);
        second_city_image = findViewById(R.id.second_city_image);
        rateFirstCity = findViewById(R.id.rate_first_city);
        rateSecondCity = findViewById(R.id.rate_second_city);
        
        String image1URL;
        String image2URL;

        try {
            JSONObject cities = new JSONObject(jsonResponse);
            city1Json = cities.getJSONObject("city1");
            city2Json = cities.getJSONObject("city2");

            first_city = (TextView)findViewById(R.id.first_city_name);
            if(locationType.equals("vacation")){
                city1name = String.valueOf(city1Json.getString("location").charAt(0)).toUpperCase() + city1Json.getString("location").substring(1, city1Json.getString("location").length());
                city2name = String.valueOf(city2Json.getString("location").charAt(0)).toUpperCase() + city2Json.getString("location").substring(1, city2Json.getString("location").length());
            }else{
                city1name = String.valueOf(city1Json.getString("city").charAt(0)).toUpperCase() + city1Json.getString("city").substring(1, city1Json.getString("city").length());
                city2name = String.valueOf(city2Json.getString("city").charAt(0)).toUpperCase() + city2Json.getString("city").substring(1, city2Json.getString("city").length());
            }


            first_city.setText(city1name);
            image1URL = city1Json.getString("image_url");
            GlideApp.with(this).load(image1URL).into(first_city_image);
            second_city = (TextView)findViewById(R.id.second_city_name);
            second_city.setText(city2name);
            image2URL = city2Json.getString("image_url");
            GlideApp.with(this).load(image2URL).into(second_city_image);
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

            bookmarkCity1 = (Button)findViewById(R.id.bookmark_first_city);
            bookmarkCity2 = (Button)findViewById(R.id.bookmark_second_city);

            bookmarkCity1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookmarkCity(city1name);
                }
            });
            bookmarkCity2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookmarkCity(city2name);
                }
            });
        }
        catch (JSONException j){
            Log.e("JSONError", j.toString());
        }

        rateFirstCity.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingDialog.show();
                rateCityName = city1name;
            }
        }));

        rateSecondCity.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingDialog.show();
                rateCityName = city2name;
            }
        }));

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

    private void saveRating() {
        JSONObject param = new JSONObject();

        String url = "https://mcprojectauth.herokuapp.com/saveRating";
        try{
            param.put("userId", userId);
            param.put("type", locationType);
            param.put("place", rateCityName);
            param.put("rating", rating);
            Log.i("Location params", param.toString());
            ConnectTask conn = new ConnectTask(url, param, "rating");
            conn.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name");
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_feedback, null);
        builder.setView(customLayout);
        // add a button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RatingBar ratingBar = customLayout.findViewById(R.id.ratingBar);

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                boolean fromUser) {

                        rating = ratingBar.getRating();
                    }
                });

                //Toast.makeText(getBaseContext(), "Rating: "+ratingBar.getNumStars(), Toast.LENGTH_LONG).show();
                rating = (int) ratingBar.getRating();
                saveRating();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        // create and show the alert dialog
        ratingDialog = builder.create();
    }

    private void getRecommendations(String city1name, String city2name){
        JSONObject param = new JSONObject();
        ArrayList<String> cities = new ArrayList<String>();
        cities.add(city1name);
        cities.add(city2name);

        String url = "https://mcfinalprojectml.herokuapp.com/getRecommendations";
        try{
            param.put("userId", userId);
            param.put("type", locationType);
            param.put("alreadyPresentCities", new JSONArray(cities));
            Log.i("Location params", param.toString());
            ConnectTask conn = new ConnectTask(url, param, "recommendation");
            conn.execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleCity1Click() {
        System.out.println(city1Json);
        if(locationType.equals("vacation")) {
            Intent intent = new Intent(LocationActivity.this, place_details.class);
            intent.putExtra("jsonResponse", city1Json.toString());
            startActivity(intent);
        }else{
            Intent intent = new Intent(LocationActivity.this, place2details.class);
            intent.putExtra("jsonResponse", city1Json.toString());
            startActivity(intent);
        }

    }

    private void handleSecondCityClick() {
        if(locationType.equals("vacation")) {
            System.out.println(city2Json);
            Intent intent = new Intent(LocationActivity.this, place_details.class);
            intent.putExtra("jsonResponse", city2Json.toString());
            startActivity(intent);
        }else{
            Intent intent = new Intent(LocationActivity.this, place2details.class);
            intent.putExtra("jsonResponse", city2Json.toString());
            startActivity(intent);
        }
    }


    class ConnectTask extends AsyncTask<Void, Void, String> {

        private final String url;
        private final JSONObject jsonParam;
        private final String requestType;

        public ConnectTask(String url, JSONObject jsonParam, String type) {

            this.url = url;
            this.jsonParam = jsonParam;
            this.requestType = type;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return connectToApi(url, jsonParam);

        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            super.onPostExecute(jsonResponse);
            if (jsonResponse!=null) {
                if(requestType == "recommendation")
                    onTaskSuccess(jsonResponse);
                else if(requestType == "rating")
                    onTaskSuccess();

            }
            else
                onTaskFailed();
        }
    }

    private void onTaskSuccess(String jsonResponse){
        Toast.makeText(getBaseContext(), "Task successful", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, RecommendationActivity.class);
        intent.putExtra("jsonResponse",jsonResponse);
        intent.putExtra("locationType", locationType);
        startActivity(intent);
    }

    private void onTaskSuccess(){
        Toast.makeText(getBaseContext(), "Task successful", Toast.LENGTH_LONG).show();
    }

    private void onTaskFailed(){
        Toast.makeText(getBaseContext(), "Task failed", Toast.LENGTH_LONG).show();
    }

    private String connectToApi(String url, JSONObject param){
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
                String jsonResponse = "";
                Scanner sc = new Scanner(conn.getInputStream());
                while(sc.hasNext())
                {
                    jsonResponse+=sc.nextLine();
                }
                sc.close();
                Log.i("Login response", jsonResponse);
                return jsonResponse;
            }

            conn.disconnect();

            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private void bookmarkCity(String city){
        try{
            String url = "https://mcprojectauth.herokuapp.com/addBookmark";
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("userId", userId);
            jsonParam.put("place", city);
            jsonParam.put("type", locationType);
            BookmarkTask BookmarkTask = new BookmarkTask(url, jsonParam);
            BookmarkTask.execute();
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    class BookmarkTask extends AsyncTask<Void, Void, Boolean>{

        private final String url;
        private final JSONObject jsonParam;

        public BookmarkTask(String url, JSONObject jsonParam){
            this.url = url;
            this.jsonParam = jsonParam;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return getBookmarkResponse(url, jsonParam);
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if(isSuccess == true) {
                Toast.makeText(getBaseContext(), "Bookmark Added", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Boolean getBookmarkResponse(String url, JSONObject jsonParam){
        try{
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            Log.i("JSON", jsonParam.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG", conn.getResponseMessage());
            System.out.println(conn.getResponseCode() + " " + conn.getResponseMessage());

            if(conn.getResponseCode() == 200){
                String inline = new String();
                Scanner sc = new Scanner(conn.getInputStream());
                while(sc.hasNext()){
                    inline += sc.nextLine();
                }
                System.out.println(inline);
                sc.close();
            }

            conn.disconnect();
            if(conn.getResponseCode() == 200){return true;}
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
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
