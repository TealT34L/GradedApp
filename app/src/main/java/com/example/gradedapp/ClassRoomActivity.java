package com.example.gradedapp;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.gradedapp.hac.Assignment;
import com.example.gradedapp.hac.ClassRoom;

import java.util.ArrayList;
import java.util.Arrays;

public class ClassRoomActivity extends AppCompatActivity {

    private TextView className;
    private Button backButton;
    private LinearLayout veritcalLayout;

    private Button gradesButton;
    private Button scheduleButton;
    private Button transcriptButton;

    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList<Assignment> assignments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_room);

        gradesButton = (Button)findViewById(R.id.gradesButton);
        scheduleButton = (Button)findViewById(R.id.scheduleButton);
        transcriptButton = (Button)findViewById(R.id.transcriptButton);

        gradesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassRoomActivity.this, GradesActivity.class);
                startActivity(intent);
            }
        });

        transcriptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassRoomActivity.this, TranscriptActivity.class);
                startActivity(intent);
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassRoomActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassRoomActivity.this, GradesActivity.class);
                startActivity(intent);
            }
        });

        buttons.add((Button) findViewById(R.id.dynamicButton1));
        veritcalLayout = (LinearLayout)findViewById(R.id.linearLayout);
        className = (TextView)findViewById(R.id.labelGrades);
        className.setText(StaticClassHolder.getClassRoom().getName());



        assignments = StaticClassHolder.getClassRoom().getAssignments();

        ArrayList<String> categories = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        for (int x = 0; x < assignments.size(); x++){
            Assignment a = assignments.get(x);
            if (a.getTitle().length() == 0){
                assignments.remove(x);
                x--;
            }
            if (!categories.contains(a.getCategory())){
                categories.add(a.getCategory());
                colors.add((colors.size()+1)*32);
            }
        }

        for (int x = 0; x < assignments.size(); x++){
            if (assignments.get(x).getTitle().length() != 0) {
                if (x != 0) {
                    createButton();
                }

                buttons.get(x).setText(assignments.get(x).getTitle() + " " + assignments.get(x).getPercentage());

                int c = Color.GREEN;
                if (assignments.get(x).getPercentage() == -1){
                    c = Color.WHITE;
                }else if(assignments.get(x).getPercentage() < 80){
                    c = Color.RED;
                } else if (assignments.get(x).getPercentage() < 90){
                    c = Color.YELLOW;
                }

                if (!assignments.get(x).hasScorePoints()){
                    c = Color.WHITE;
                }

                buttons.get(x).setBackgroundColor(c);
            }
        }
        if (assignments.isEmpty()){
            ViewGroup layout = (ViewGroup) buttons.get(0).getParent();
            veritcalLayout.removeView((View)buttons.get(0));
            System.out.println("removing button");
        }
        System.out.println(assignments.size() + " " + buttons.size());
        
    }

    public void createButton(){
        Button lastButton = buttons.get(buttons.size()-1);
        final Button secondButton = new Button(ClassRoomActivity.this);
        secondButton.setLayoutParams(new ConstraintLayout.LayoutParams(
                lastButton.getLayoutParams()
        ));
        //secondButton.getLayout().
        veritcalLayout.addView(secondButton);

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        buttons.add(secondButton);
    }

}
