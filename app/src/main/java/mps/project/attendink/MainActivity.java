package mps.project.attendink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import mps.project.attendink.Activities.attendanceTaker;

public class MainActivity extends AppCompatActivity {

    RelativeLayout year1, year2, year3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        year1 = findViewById(R.id.bca1);
        year2 = findViewById(R.id.bca2);
        year3 = findViewById(R.id.bca3);

        year1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, attendanceTaker.class));
            }
        });

        year2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year1.performClick();
            }
        });

        year3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year1.performClick();
            }
        });

    }
}