package com.sourcey.relocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Recommendation2Activity extends AppCompatActivity {

    private String jsonResponse;

    private TextView housing_rates;
    private TextView crime_rates;
    private TextView pop_density;
    private TextView living_expense;
    private TextView dist_cities;
    private TextView traffic;
    private TextView education;
    private TextView taxes;
    private TextView score;
    private TextView similarityScoreDesc;

    private TextView city1Name;
    private TextView city2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation2);

        city1Name = findViewById(R.id.city1Name);
        city2Name = findViewById(R.id.city2Name);

        Intent intent = getIntent();
        jsonResponse = intent.getStringExtra("jsonResponse");
        JSONObject cities;
        try{
            cities = new JSONObject(jsonResponse);
            System.out.println(cities);
            JSONObject city1Json = cities.getJSONObject("city1");
            JSONObject city2Json = cities.getJSONObject("city2");

            city1Name.setText(String.valueOf(city1Json.getString("city").charAt(0)).toUpperCase() + city1Json.getString("city").substring(1, city1Json.getString("city").length()));

            housing_rates = findViewById(R.id.housingRates);
            housing_rates.setText(city1Json.getString("rent"));

            crime_rates = findViewById(R.id.crimeRates);
            crime_rates.setText(city1Json.getString("crime_rate"));

            pop_density = findViewById(R.id.popDense);
            pop_density.setText(city1Json.getString("population_density"));

            living_expense = findViewById(R.id.livExpense);
            living_expense.setText(city1Json.getString("living_expense"));

            dist_cities = findViewById(R.id.distCities);
            dist_cities.setText(city1Json.getString("dist_cities"));

            traffic = findViewById(R.id.traffic);
            traffic.setText(city1Json.getString("traffic"));

            education = findViewById(R.id.education);
            education.setText(city1Json.getString("standard_of_education"));

            taxes = findViewById(R.id.taxes);
            taxes.setText(city1Json.getString("taxes"));




            city2Name.setText(String.valueOf(city2Json.getString("city").charAt(0)).toUpperCase() + city2Json.getString("city").substring(1, city2Json.getString("city").length()));

            housing_rates = findViewById(R.id.housingRates1);
            housing_rates.setText(city2Json.getString("rent"));

            crime_rates = findViewById(R.id.crimeRates1);
            crime_rates.setText(city2Json.getString("crime_rate"));

            pop_density = findViewById(R.id.popDense1);
            pop_density.setText(city2Json.getString("population_density"));

            living_expense = findViewById(R.id.livExpense1);
            living_expense.setText(city2Json.getString("living_expense"));

            dist_cities = findViewById(R.id.distCities1);
            dist_cities.setText(city2Json.getString("dist_cities"));

            traffic = findViewById(R.id.traffic1);
            traffic.setText(city2Json.getString("traffic"));

            education = findViewById(R.id.education1);
            education.setText(city2Json.getString("standard_of_education"));

            taxes = findViewById(R.id.taxes1);
            taxes.setText(city2Json.getString("taxes"));


        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
