package com.sourcey.relocator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class Homepage extends AppCompatActivity {

    private Button relocationMode;
    private Button vacationMode;
    private Button bookmarkRelocation;
    private Button bookmarkVacation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Intent intent = getIntent();
        final int userId = intent.getIntExtra("userId", 0);

        relocationMode = findViewById(R.id.relocationMode);
        vacationMode = findViewById(R.id.vacationMode);
        bookmarkRelocation = findViewById(R.id.bookmark_relocation);
        bookmarkVacation = findViewById(R.id.bookmark_vacation);

        relocationMode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, RelocationActivity.class);
                intent.putExtra("userId", userId);
                System.out.println("Pauras task user: " + userId);
                startActivity(intent);
            }
        });


        vacationMode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, VacationActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        bookmarkRelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, Bookmark.class);
                intent.putExtra("userId", userId);
                intent.putExtra("type", "relocation");
                startActivity(intent);
            }
        });

        bookmarkVacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this, Bookmark.class);
                intent.putExtra("userId", userId);
                intent.putExtra("type", "vacation");
                startActivity(intent);
            }
        });
    }
}
