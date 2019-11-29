package com.sourcey.relocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class RecommendationActivity extends AppCompatActivity {
    private String jsonResponse;
    private TextView accomodation;
    private TextView budget;
    private TextView cuisine;
    private TextView family_friendly;
    private TextView historical_places;
    private TextView party_places;
    private TextView transport;

    private TextView city1Name;
    private TextView city2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        city1Name = findViewById(R.id.city1Name);
        city2Name = findViewById(R.id.city2Name);

        Intent intent = getIntent();
        jsonResponse = intent.getStringExtra("jsonResponse");
        JSONObject cities;
        try {
            cities= new JSONObject(jsonResponse);
            JSONObject city1Json = cities.getJSONObject("city1");
            JSONObject city2Json = cities.getJSONObject("city2");

            city1Name.setText(String.valueOf(city1Json.getString("location").charAt(0)).toUpperCase() + city1Json.getString("location").substring(1, city1Json.getString("location").length()));

            accomodation = findViewById(R.id.accomodationCity1);
            accomodation.setText(city1Json.getString("accomodation"));
            budget = findViewById(R.id.budgetCity1);
            budget.setText(city1Json.getString("budget"));
            cuisine = findViewById(R.id.cuisineCity1);
            cuisine.setText(city1Json.getString("cuisine"));
            family_friendly = findViewById(R.id.familyFriendlyCity1);
            family_friendly.setText(city1Json.getString("family_friendly"));
            historical_places = findViewById(R.id.historicalPlacesCity1);
            historical_places.setText(city1Json.getString("historical_places"));
            party_places = findViewById(R.id.partySpotsCity1);
            party_places.setText(city1Json.getString("party_places"));
            transport = findViewById(R.id.transportCity1);
            transport.setText(city1Json.getString("transport"));


            city2Name.setText(String.valueOf(city2Json.getString("location").charAt(0)).toUpperCase() + city2Json.getString("location").substring(1, city2Json.getString("location").length()));

            accomodation = findViewById(R.id.accomodationCity2);
            accomodation.setText(city2Json.getString("accomodation"));
            budget = findViewById(R.id.budgetCity2);
            budget.setText(city2Json.getString("budget"));
            cuisine = findViewById(R.id.cuisineCity2);
            cuisine.setText(city2Json.getString("cuisine"));
            family_friendly = findViewById(R.id.familyFriendlyCity2);
            family_friendly.setText(city2Json.getString("family_friendly"));
            historical_places = findViewById(R.id.historicalPlacesCity2);
            historical_places.setText(city2Json.getString("historical_places"));
            party_places = findViewById(R.id.partySpotsCity2);
            party_places.setText(city2Json.getString("party_places"));
//            transport = findViewById(R.id.transportCity2);
            transport.setText(city2Json.getString("transport"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}