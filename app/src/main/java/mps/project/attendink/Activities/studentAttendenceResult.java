package mps.project.attendink.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mps.project.attendink.R;

public class studentAttendenceResult extends AppCompatActivity {

    String pStudent = "0";
    String aStudent = "0";
    private TextView totalStudents, presentStudents, absentStudents;
    private String tStudent = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendence_result);

        totalStudents = findViewById(R.id.totalStudent);
        presentStudents = findViewById(R.id.presentStudent);
        absentStudents = findViewById(R.id.absentStudent);

        tStudent = getIntent().getStringExtra("total");
        pStudent = getIntent().getStringExtra("present");
        aStudent = getIntent().getStringExtra("absent");

        totalStudents.append(tStudent);
        presentStudents.append(pStudent);
        absentStudents.append(aStudent);

    }
}