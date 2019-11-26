package com.sourcey.relocator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Homepage extends AppCompatActivity {

    private Button relocationMode;
    private Button vacationMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        relocationMode = findViewById(R.id.relocationMode);
        vacationMode = findViewById(R.id.vacationMode);

        relocationMode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, RelocationActivity.class);
                startActivity(intent);
            }
        });


        vacationMode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, VacationActivity.class);
                startActivity(intent);
            }
        });
    }
}
