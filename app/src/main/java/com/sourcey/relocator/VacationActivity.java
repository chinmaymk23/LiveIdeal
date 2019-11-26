package com.sourcey.relocator;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class VacationActivity extends AppCompatActivity {

    private String[] weatherArray;
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

        weather = findViewById(R.id.weather);
        budget = findViewById(R.id.budget);
        submitVacationPreferences = findViewById(R.id.submitVacationPreferences);

        budgetVal = Integer.parseInt(budget.getText().toString());

        submitVacationPreferences.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

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
    }

    public void submitForm(){
        JSONObject param = new JSONObject();
        try {
            String url = "https://mcfinalprojectml.herokuapp.com/getNearestVacation";
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("budget", budget);
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

    class VacationTask extends AsyncTask<Void, Void, Boolean> {

        private final String url;
        private final JSONObject jsonParam;

        public VacationTask(String url, JSONObject jsonParam) {

            this.url = url;
            this.jsonParam = jsonParam;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return getVacationResponse(url, jsonParam);
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
        }
    }



    public boolean getVacationResponse(String url, JSONObject param) {

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
                String inline = new String();
                Scanner sc = new Scanner(conn.getInputStream());
                while(sc.hasNext())
                {
                    inline+=sc.nextLine();
                }
                System.out.println(inline);
                sc.close();
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
}
