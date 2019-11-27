package com.sourcey.relocator;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RelocationActivity extends AppCompatActivity {

    private String[] housingArray;
    private String[] transportationArray;
    private String[] weatherRelocArray;
    private String[] crimeArray;
    private String[] populationArray;
    private String[] expensesArray;
    private String[] relocationCostArray;
    private String[] distCitiesArray;
    private String[] trafficArray;
    private String[] educationArray;
    private String[] taxesArray;

    private Spinner housing;
    private Spinner transportation;
    private Spinner weatherReloc;
    private Spinner crime;
    private Spinner population;
    private Spinner expenses;
    private Spinner relocationCost;
    private Spinner distCitites;
    private Spinner traffic;
    private Spinner education;
    private Spinner taxes;
    private Button submit;

    String housingVal;
    String transportationVal;
    String weatherRelocVal;
    String crimeVal;
    String populationVal;
    String expensesVal;
    String relocationCostVal;
    String distCititesVal;
    String trafficVal;
    String educationVal;
    String taxesVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relocation_mode);

        housing = findViewById(R.id.housing);
        transportation = findViewById(R.id.transportation);
        weatherReloc = findViewById(R.id.weatherReloc);
        crime = findViewById(R.id.crime);
        population = findViewById(R.id.population);
        expenses = findViewById(R.id.expenses);
        relocationCost = findViewById(R.id.relocationCost);
        distCitites = findViewById(R.id.distCities);
        traffic = findViewById(R.id.traffic);
        education = findViewById(R.id.education);
        taxes = findViewById(R.id.taxes);
        submit = (Button) findViewById(R.id.submit_form);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        //Housing Spinner
        ArrayAdapter<String> housingAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.housing_array));

        housingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        housing.setAdapter(housingAdapter);

        housingArray = getResources().getStringArray(R.array.housing_array);

        housing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                housingVal = housingArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                housingVal = housingArray[0];//if nothing is selected, then default value is first value
            }
        });

        //Transportation Spinner
        ArrayAdapter<String> transportationAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.transportation_array));

        transportationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transportation.setAdapter(transportationAdapter);

        transportationArray = getResources().getStringArray(R.array.transportation_array);

        transportation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                transportationVal = transportationArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                transportationVal = transportationArray[0];//if nothing is selected, then default value is first value
            }
        });

        //Weather Spinner
        ArrayAdapter<String> weatherAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.weather_array));

        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherReloc.setAdapter(weatherAdapter);

        weatherRelocArray = getResources().getStringArray(R.array.weather_array);

        weatherReloc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                weatherRelocVal = weatherRelocArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                weatherRelocVal = weatherRelocArray[0];//if nothing is selected, then default value is first value
            }
        });

        //Crime Spinner
        ArrayAdapter<String> crimeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.crime_array));

        crimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        crime.setAdapter(crimeAdapter);

        crimeArray = getResources().getStringArray(R.array.crime_array);

        crime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                crimeVal = crimeArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                crimeVal = crimeArray[0];//if nothing is selected, then default value is first value
            }
        });

        //Population Spinner
        ArrayAdapter<String> populationAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.population_array));

        populationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        population.setAdapter(populationAdapter);

        populationArray = getResources().getStringArray(R.array.population_array);

        population.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                populationVal = populationArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                populationVal = populationArray[0];//if nothing is selected, then default value is first value
            }
        });

        //Expenses Spinner
        ArrayAdapter<String> expensesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.expenses_array));

        expensesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenses.setAdapter(expensesAdapter);

        expensesArray = getResources().getStringArray(R.array.expenses_array);

        expenses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                expensesVal = expensesArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                expensesVal = expensesArray[0];//if nothing is selected, then default value is first value
            }
        });

        //Relocation Cost Spinner
        ArrayAdapter<String> relocationCostAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.relocationCost_array));

        relocationCostAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relocationCost.setAdapter(relocationCostAdapter);

        relocationCostArray = getResources().getStringArray(R.array.relocationCost_array);

        relocationCost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                relocationCostVal = relocationCostArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                relocationCostVal = relocationCostArray[0];//if nothing is selected, then default value is first value
            }
        });

        //Distance from Cities Spinner
        ArrayAdapter<String> distCitiesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.distCities_array));

        distCitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distCitites.setAdapter(distCitiesAdapter);

        distCitiesArray = getResources().getStringArray(R.array.distCities_array);

        distCitites.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                distCititesVal = distCitiesArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                distCititesVal = distCitiesArray[0];//if nothing is selected, then default value is first value
            }
        });

        //Traffic Spinner
        ArrayAdapter<String> trafficAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.traffic_array));

        trafficAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        traffic.setAdapter(trafficAdapter);

        trafficArray = getResources().getStringArray(R.array.traffic_array);

        traffic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                trafficVal = trafficArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                trafficVal = trafficArray[0];//if nothing is selected, then default value is first value
            }
        });

        //Education Spinner
        ArrayAdapter<String> educationAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.education_array));

        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        education.setAdapter(educationAdapter);

        educationArray = getResources().getStringArray(R.array.education_array);

        education.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                educationVal = educationArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                educationVal = educationArray[0];//if nothing is selected, then default value is first value
            }
        });

        //Taxes Spinner
        ArrayAdapter<String> taxesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.taxes_array));

        taxesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taxes.setAdapter(taxesAdapter);

        taxesArray = getResources().getStringArray(R.array.taxes_array);

        taxes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                taxesVal = taxesArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                taxesVal = taxesArray[0];//if nothing is selected, then default value is first value
            }
        });


    }

    public void submitForm(){
        JSONObject param = new JSONObject();
        try{
            String url = "https://mcfinalprojectml.herokuapp.com/getNearestRelocation";
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("taxes", taxesVal);
            jsonParam.put("crime_rate", crimeVal);
            jsonParam.put("rent", housingVal);
            jsonParam.put("traffic", trafficVal);
            jsonParam.put("standard_of_education", educationVal);
            jsonParam.put("population_density", populationVal);
            jsonParam.put("living_expenses", expensesVal);
            jsonParam.put("distance_from_other_cities", distCititesVal);
            jsonParam.put("weather", weatherRelocVal);
            jsonParam.put("access_of_local_transport", transportationVal);
            RelocationTask RelocationTask = new RelocationTask(url, jsonParam);
            RelocationTask.execute();
        }catch (JSONException e){
            System.out.println(e);
        }
    }

    class RelocationTask extends AsyncTask<Void, Void, Boolean> {

        private final String url;
        private final JSONObject jsonParam;

        public RelocationTask(String url, JSONObject jsonParam) {

            this.url = url;
            this.jsonParam = jsonParam;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return getRelocationResponse(url, jsonParam);
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
        }
    }


    public boolean getRelocationResponse(String url, JSONObject param) {

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
