package com.sourcey.relocator;

import android.content.Intent;
import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class VacationActivity extends AppCompatActivity {

    private String[] weatherArray;
    private String[] historicalPlacesArray;
    private String[] terrainArray;
    private String[] familyFriendlyArray;
    private String[] partySpotsArray;
    private String[] cuisineArray;
    private String[] transportArray;
    private String[] socialEnvironmentArray;
    private String[] seasonArray;
    private String[] accomodationArray;
    private ProgressDialog progressDialog;

    private TextView budget;
    private Spinner weather;
    private Spinner historicalPlaces;
    private Spinner terrain;
    private Spinner familyFriendly;
    private Spinner partySpots;
    private Spinner cuisine;
    private Spinner transport;
    private Spinner socialEnvironment;
    private Spinner season;
    private Spinner accomodation;
    private Button submitVacationPreferences;

    long budgetVal;
    String budgetRange;
    String weatherVal;
    String historicalPlacesVal;
    String terrainVal;
    String familyFriendlyVal;
    String partySpotsVal;
    String transportVal;
    String cuisineVal;
    String socialEnvironmentVal;
    String seasonVal;
    String accomodationVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation);

        budget = findViewById(R.id.budget);
        submitVacationPreferences = findViewById(R.id.submitVacationPreferences);
        progressDialog = new ProgressDialog(VacationActivity.this, R.style.AppTheme_Dark_Dialog);

        submitVacationPreferences.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                submitForm();
            }
        });

        weather = findViewById(R.id.weather);
        ArrayAdapter<String> weatherAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.weather_array));//setting the country_array to spinner

        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weather.setAdapter(weatherAdapter);

        weatherArray = getResources().getStringArray(R.array.weather_array);

        weather.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                weatherVal = weatherArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                weatherVal = weatherArray[0];       // If nothing is selected, then default value is first value
            }
        });


        historicalPlaces = findViewById(R.id.historicalPlaces);
        ArrayAdapter<String> historicalPlacesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.historicalPlaces_array));//setting the country_array to spinner

        historicalPlacesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        historicalPlaces.setAdapter(historicalPlacesAdapter);

        historicalPlacesArray = getResources().getStringArray(R.array.historicalPlaces_array);

        historicalPlaces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                historicalPlacesVal = historicalPlacesArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                historicalPlacesVal = historicalPlacesArray[0];       // If nothing is selected, then default value is first value
            }
        });

        terrain = findViewById(R.id.terrain);
        ArrayAdapter<String> terrainAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.terrain_array));//setting the country_array to spinner

        terrainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        terrain.setAdapter(terrainAdapter);

        terrainArray = getResources().getStringArray(R.array.terrain_array);

        terrain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                terrainVal = terrainArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                terrainVal = terrainArray[0];       // If nothing is selected, then default value is first value
            }
        });

        familyFriendly = findViewById(R.id.familyFriendly);
        ArrayAdapter<String> familyFriendlyAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.familyFriendly_array));//setting the country_array to spinner

        familyFriendlyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familyFriendly.setAdapter(familyFriendlyAdapter);

        familyFriendlyArray = getResources().getStringArray(R.array.familyFriendly_array);

        familyFriendly.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                familyFriendlyVal = familyFriendlyArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                familyFriendlyVal = familyFriendlyArray[0];       // If nothing is selected, then default value is first value
            }
        });

        partySpots = findViewById(R.id.partySpots);
        ArrayAdapter<String> partySpotsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.partySpots_array));//setting the country_array to spinner

        partySpotsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partySpots.setAdapter(partySpotsAdapter);

        partySpotsArray = getResources().getStringArray(R.array.partySpots_array);

        partySpots.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                partySpotsVal = partySpotsArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                partySpotsVal = partySpotsArray[0];       // If nothing is selected, then default value is first value
            }
        });

        cuisine = findViewById(R.id.cuisine);
        ArrayAdapter<String> cuisineAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.cuisine_array));//setting the country_array to spinner

        cuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisine.setAdapter(cuisineAdapter);

        cuisineArray = getResources().getStringArray(R.array.cuisine_array);

        cuisine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                cuisineVal = cuisineArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                cuisineVal = cuisineArray[0];       // If nothing is selected, then default value is first value
            }
        });

        transport = findViewById(R.id.transport);
        ArrayAdapter<String> transportAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.transport_array));//setting the country_array to spinner

        transportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transport.setAdapter(transportAdapter);

        transportArray = getResources().getStringArray(R.array.transport_array);

        transport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                transportVal = transportArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                transportVal = transportArray[0];       // If nothing is selected, then default value is first value
            }
        });

        socialEnvironment = findViewById(R.id.socialEnvironment);
        ArrayAdapter<String> socialEnvironmentAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.socialEnvironment_array));//setting the country_array to spinner

        socialEnvironmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        socialEnvironment.setAdapter(socialEnvironmentAdapter);

        socialEnvironmentArray = getResources().getStringArray(R.array.socialEnvironment_array);

        socialEnvironment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                socialEnvironmentVal = socialEnvironmentArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                socialEnvironmentVal = socialEnvironmentArray[0];       // If nothing is selected, then default value is first value
            }
        });

        season = findViewById(R.id.season);
        ArrayAdapter<String> seasonAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.season_array));//setting the country_array to spinner

        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        season.setAdapter(seasonAdapter);

        seasonArray = getResources().getStringArray(R.array.season_array);

        season.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                seasonVal = seasonArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                seasonVal = seasonArray[0];       // If nothing is selected, then default value is first value
            }
        });

        accomodation = findViewById(R.id.accomodation);
        ArrayAdapter<String> accomodationAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources()
                .getStringArray(R.array.accomodation_array));//setting the country_array to spinner

        accomodationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accomodation.setAdapter(accomodationAdapter);

        accomodationArray = getResources().getStringArray(R.array.accomodation_array);

        accomodation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {
                accomodationVal = accomodationArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                accomodationVal = accomodationArray[0];       // If nothing is selected, then default value is first value
            }
        });
    }

    public void submitForm(){
        JSONObject param = new JSONObject();
        try {
            budgetVal = Long.parseLong(String.valueOf(budget.getText()));

            if(budgetVal >= 10000)
                budgetRange = "high";
            else if(budgetVal >=3000 && budgetVal<10000)
                budgetRange = "medium";
            else
                budgetRange = "low";

            String url = "https://mcfinalprojectml.herokuapp.com/getNearestVacation";
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("budget", budgetRange);
            jsonParam.put("weather", weatherVal);
            jsonParam.put("historical", historicalPlacesVal);
            jsonParam.put("terrain", terrainVal);
            jsonParam.put("family_friendly", familyFriendlyVal);
            jsonParam.put("party", partySpotsVal);
            jsonParam.put("cuisine", cuisineVal);
            jsonParam.put("transport", transportVal);
            jsonParam.put("social_env", socialEnvironmentVal);
            jsonParam.put("season", seasonVal);
            jsonParam.put("accomodation", accomodationVal);
            VacationTask VacationTask = new VacationTask(url, jsonParam);
            VacationTask.execute();
        } catch (JSONException e) {
            System.out.println(e);
        }
    }

    class VacationTask extends AsyncTask<Void, Void, String> {

        private final String url;
        private final JSONObject jsonParam;

        public VacationTask(String url, JSONObject jsonParam) {

            this.url = url;
            this.jsonParam = jsonParam;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return getVacationResponse(url, jsonParam);
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            Intent intent = new Intent(VacationActivity.this, LocationActivity.class);
            intent.putExtra("jsonResponse",jsonResponse);
            progressDialog.dismiss();
            startActivity(intent);
        }
    }



    public String getVacationResponse(String url, JSONObject param) {

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
                String jsonResponse = new String();
                Scanner sc = new Scanner(conn.getInputStream());
                while(sc.hasNext())
                {
                    jsonResponse+=sc.nextLine();
                }
                sc.close();
                return jsonResponse;
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
