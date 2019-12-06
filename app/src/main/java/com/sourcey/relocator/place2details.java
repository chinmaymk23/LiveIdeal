package com.sourcey.relocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;

public class place2details extends AppCompatActivity {

    private String jsonResponse;
    private TextView housing;
    private TextView transport;
    private TextView weather;
    private TextView crime;
    private TextView population;
    private TextView expenses;
    private TextView distCities;
    private TextView traffic;
    private TextView education;
    private TextView taxes;
    private TextView similarityScore;
    private TextView similarityScoreDesc;
    private ImageView first_city_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place2details);
        Intent intent = getIntent();
        jsonResponse = intent.getStringExtra("jsonResponse");
        JSONObject cities = null;
        try{
            cities = new JSONObject(jsonResponse);
            System.out.println(cities);
            housing = findViewById(R.id.housing);
            housing.setText(cities.getString("rent"));

            //transport = findViewById(R.id.transport);
            //transport.setText(cities.getJSONArray("access_of_local_transport").toString());

            //weather = findViewById(R.id.weather);
            //weather.setText(cities.getJSONArray("weather").toString());

            crime = findViewById(R.id.crime);
            crime.setText(cities.getString("crime_rate"));

            population = findViewById(R.id.population);
            population.setText(cities.getString("population_density"));

            expenses = findViewById(R.id.expenses);
            expenses.setText(cities.getString("living_expense"));

            distCities = findViewById(R.id.dist_cities);
            distCities.setText(cities.getString("dist_cities"));

            traffic = findViewById(R.id.traffic);
            traffic.setText(cities.getString("traffic"));

            education = findViewById(R.id.education);
            education.setText(cities.getString("standard_of_education"));

            taxes = findViewById(R.id.taxes);
            taxes.setText(cities.getString("taxes"));

            first_city_image = findViewById(R.id.imageView);
            GlideApp.with(this).load(cities.getString("image_url")).into(first_city_image);
            if (cities.getString("similarity") == null) {
                similarityScore = findViewById(R.id.textView16);
                similarityScoreDesc = findViewById(R.id.similarityscore);
                similarityScoreDesc.setVisibility(View.GONE);
                similarityScore.setVisibility(View.GONE);
            } else {
                float num = Float.parseFloat(cities.getString("similarity")) * 100;
                double n = Math.round(num * 100.0) / 100.0;
                similarityScoreDesc = findViewById(R.id.similarityscore);
                similarityScoreDesc.setText(String.valueOf(n));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}