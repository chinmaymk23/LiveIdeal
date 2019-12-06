package com.sourcey.relocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class place_details extends AppCompatActivity {
    private String jsonResponse;
    private TextView accomodation;
    private TextView budget;
    private TextView cuisine;
    private TextView family_friendly;
    private TextView historical_places;
    private TextView party_places;
    private TextView transport;
    private TextView similarityScore;
    private TextView similarityScoreDesc;
    private ImageView first_city_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        Intent intent = getIntent();
        jsonResponse = intent.getStringExtra("jsonResponse");
        JSONObject cities = null;
        try {
            cities = new JSONObject(jsonResponse);
            accomodation = findViewById(R.id.accomodation);
            accomodation.setText(cities.getString("accomodation"));
            budget = findViewById(R.id.budget);
            budget.setText(cities.getString("budget"));
            cuisine = findViewById(R.id.cuisine);
            cuisine.setText(cities.getString("cuisine"));
            family_friendly = findViewById(R.id.familyFriendly);
            family_friendly.setText(cities.getString("family_friendly"));
            historical_places = findViewById(R.id.historicalPlaces);
            historical_places.setText(cities.getString("historical_places"));
            party_places = findViewById(R.id.partySpots);
            party_places.setText(cities.getString("party_places"));
            transport = findViewById(R.id.transport);
            transport.setText(cities.getString("transport"));
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

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
