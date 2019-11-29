package com.sourcey.relocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}