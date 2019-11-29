package com.sourcey.relocator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Bookmark extends AppCompatActivity {

    private ArrayList<String> pNames = new ArrayList<>();
    private ArrayList<String> dButtons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle  savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        pNames.add("Grand Forks");
        pNames.add("Cerrilios");
        pNames.add("Houston");
        pNames.add("Tampa");
        pNames.add("Rockford");

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, pNames, dButtons);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
