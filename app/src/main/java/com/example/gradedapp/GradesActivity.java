package com.example.gradedapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.gradedapp.hac.ClassRoom;

import java.util.ArrayList;

public class GradesActivity extends AppCompatActivity {

    private TextView label;
    private Button gradesButton;
    private Button scheduleButton;
    private Button transcriptButton;

    private LinearLayout veritcalLayout;
    private LinearLayout horizontalLayout;
    private ConstraintLayout myLayout;

    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList<ClassRoom> classes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        myLayout = (ConstraintLayout)findViewById(R.id.myLayout);
        veritcalLayout = (LinearLayout)findViewById(R.id.linearLayout);
        horizontalLayout = (LinearLayout)findViewById(R.id.bottomLayout);


        label = (TextView)findViewById(R.id.labelGrades);
        gradesButton = (Button)findViewById(R.id.gradesButton);
        scheduleButton = (Button)findViewById(R.id.scheduleButton);
        transcriptButton = (Button)findViewById(R.id.transcriptButton);

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GradesActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });

        transcriptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GradesActivity.this, TranscriptActivity.class);
                startActivity(intent);
            }
        });

        buttons.add((Button) findViewById(R.id.dynamicButton1));

        classes = MainActivity.classes;

        for (int x = 0; x < classes.size(); x++){
            if (x!= 0){
                createButton();
            }

            int c = Color.GREEN;
            if (classes.get(x).getGrade() == -1){
                c = Color.WHITE;
            }else if(classes.get(x).getGrade() < 80){
                c = Color.RED;
            } else if (classes.get(x).getGrade() < 90){
                c = Color.YELLOW;
            }
            if (classes.get(x).getGrade() != -1){
                buttons.get(x).setText(classes.get(x).getName() + " " + classes.get(x).getGrade());
            } else {
                buttons.get(x).setText(classes.get(x).getName());
            }
            buttons.get(x).setBackgroundColor(c);
            Button b = buttons.get(x);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = -1;
                    for (int y = 0; y < buttons.size(); y++){
                        if (buttons.get(y).getText().equals(((Button) v).getText())){
                            pos = y;
                        }
                    }
                    changeByClass(classes.get(pos));
                }
            });
            System.out.println(classes.get(x).getName() + " " + c);
        }
    }

    public void changeByClass(ClassRoom classRoom){
        StaticClassHolder.setClassRoom(classRoom);

        Intent intent = new Intent(GradesActivity.this, ClassRoomActivity.class);
        startActivity(intent);

    }

    public void createButton(){
        Button lastButton = buttons.get(buttons.size()-1);
        final Button secondButton = new Button(GradesActivity.this);
        secondButton.setLayoutParams(new ConstraintLayout.LayoutParams(
                lastButton.getLayoutParams()
        ));
        //secondButton.getLayout().
        veritcalLayout.addView(secondButton);



        buttons.add(secondButton);
    }
}
