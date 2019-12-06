package com.sourcey.relocator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Bookmark extends AppCompatActivity {

    private List<String> pNames = new ArrayList<>();
    private ArrayList<String> dButtons = new ArrayList<>();
    private String locationType;
    private String url;
    private int userId;
    private String result;
    private int i;

    @Override
    protected void onCreate(Bundle  savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        locationType = intent.getStringExtra("locationType");

            url = "https://mcprojectauth.herokuapp.com/getBookMarks?userId=" + userId + "&type=" + locationType;

        GetBookmark getBookmark = new GetBookmark();
        try {
            result = getBookmark.execute(url).get();
            System.out.println("PLACES = " + processJSON(result));
            pNames = processJSON(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("RESULT = " + result);

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, pNames, dButtons, userId, locationType);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected List<String> processJSON(String jsonAsString) throws Exception {
        JSONObject obj = new JSONObject(jsonAsString);
        JSONArray arr = obj.getJSONArray("data");
        List<String> places = new ArrayList<>();
        for(int i = 0 ; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);
            places.add((String)o.get("place"));
        }
        return places;
    }
    public class GetBookmark extends AsyncTask<String, Void, String>{
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String result;
            String inputLine;
            try {
                URL myUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();

                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                connection.connect();

                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                reader.close();
                streamReader.close();

                result = stringBuilder.toString();
            }catch (IOException e){
                e.printStackTrace();
                result = null;
            }

            return result;
        }
    }

}
