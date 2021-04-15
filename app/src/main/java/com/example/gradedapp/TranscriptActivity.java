package com.example.gradedapp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class TranscriptActivity extends AppCompatActivity {

    private TextView label;
    private Button gradesButton;
    private Button scheduleButton;
    private Button transcriptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transcript);

        label = (TextView)findViewById(R.id.labelTranscript);
        gradesButton = (Button)findViewById(R.id.gradesButton);
        scheduleButton = (Button)findViewById(R.id.scheduleButton);
        transcriptButton = (Button)findViewById(R.id.transcriptButton);

        gradesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TranscriptActivity.this, GradesActivity.class);
                startActivity(intent);
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TranscriptActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });

    }
}
