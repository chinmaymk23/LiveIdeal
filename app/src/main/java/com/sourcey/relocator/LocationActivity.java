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
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class LocationActivity extends AppCompatActivity {

    private TextView first_city;
    private ImageView first_city_image;
    private TextView second_city;
    private ImageView second_city_image;
    private CardView city1;
    private CardView city2;
    private JSONObject city1Json;
    private JSONObject city2Json;
    private String jsonResponse;
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
        System.out.println(jsonResponse);


        String image1URL;
        String image2URL;

        try {
            JSONObject cities = new JSONObject(jsonResponse);
            city1Json = cities.getJSONObject("city1");
            city2Json = cities.getJSONObject("city2");
            /*new DownloadImageTask((ImageView) findViewById(R.id.first_city_image))
                    .execute(city1Json.getString("image_url"));*/

            first_city = (TextView)findViewById(R.id.first_city_name);
            String txt = String.valueOf(city1Json.getString("location").charAt(0)).toUpperCase() + city1Json.getString("location").substring(1, city1Json.getString("location").length());

            first_city.setText(txt);
            image1URL = city1Json.getString("image_url");

            second_city = (TextView)findViewById(R.id.second_city_name);
            String txt2 = String.valueOf(city2Json.getString("location").charAt(0)).toUpperCase() + city2Json.getString("location").substring(1, city2Json.getString("location").length());
            second_city.setText(txt2);
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
            /*new DownloadImageTask((ImageView) findViewById(R.id.first_city_image))
                    .execute(city2Json.getString("image_url"));*/
            /*DownLoadImageTask d = new DownLoadImageTask(image1URL, first_city_image);
            d.execute();

            d = new DownLoadImageTask(image2URL, second_city_image);
            d.execute();*/
        }
        catch (JSONException j){
            Log.e("JSONError", j.toString());
        }
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
