package com.example.gradedapp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ScheduleActivity extends AppCompatActivity {

    private TextView label;
    private Button gradesButton;
    private Button scheduleButton;
    private Button transcriptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        label = (TextView)findViewById(R.id.labelSchedule);
        gradesButton = (Button)findViewById(R.id.gradesButton);
        scheduleButton = (Button)findViewById(R.id.scheduleButton);
        transcriptButton = (Button)findViewById(R.id.transcriptButton);

        gradesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, GradesActivity.class);
                startActivity(intent);
            }
        });

        transcriptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, TranscriptActivity.class);
                startActivity(intent);
            }
        });
    }
}
