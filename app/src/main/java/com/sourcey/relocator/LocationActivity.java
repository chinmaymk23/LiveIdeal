package com.sourcey.relocator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Intent intent = getIntent();
        String jsonResponse = intent.getStringExtra("jsonResponse");
        System.out.println(jsonResponse);

        JSONObject city1Json;
        JSONObject city2Json;
        String image1URL;
        String image2URL;

        try {
            JSONObject cities = new JSONObject(jsonResponse);
            city1Json = cities.getJSONObject("city1");
            city2Json = cities.getJSONObject("city2");

            first_city = (TextView)findViewById(R.id.first_city_name);
            first_city.setText(city1Json.getString("location"));
            image1URL = city1Json.getString("image_url");

            second_city = (TextView)findViewById(R.id.second_city_name);
            second_city.setText(city2Json.getString("location"));
            image2URL = city2Json.getString("image_url");

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
