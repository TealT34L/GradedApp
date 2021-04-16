package com.example.gradedapp;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gradedapp.hac.ClassRoom;
//import com.example.gradedapp.com.example.gradedapp.hac.SStudent;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<ClassRoom> classes = new ArrayList<>();

    private EditText userName;
    private EditText password;
    private TextView info;
    private Button loginButton;
    private volatile Client cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Trying to setup");

        userName = (EditText)findViewById(R.id.edittextuser);
        password = (EditText)findViewById(R.id.edittextpass);
        info = (TextView)findViewById(R.id.labelLogin);
        loginButton = (Button)findViewById(R.id.loginbutton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    info.setText("Validating");
                    validataLogin(userName.getText().toString(), password.getText().toString());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    try {
                        info.setText("Validating");
                        validataLogin(userName.getText().toString(), password.getText().toString());
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

    }

    private void validataLogin(String userNameText, String passwordText) throws IOException, ClassNotFoundException {
        //if (checkpass) then:
        info.setText("Validating");

        cl = new Client(userNameText, passwordText);
        ClientThreader clientThread = new ClientThreader(cl);
        clientThread.start();



        long time = System.nanoTime();
        int num = 1;
        System.out.println("waiting for validity");
        while(!cl.getValidity()){
            info.setText("Validating");
        }
        while(!cl.hasRefreshed()){
            info.setText("Refreshing");
        }

        if (cl.getValidity()){
            System.out.println("Login worked");
            info.setText("Loading");
            classes = cl.getClasses();
            Intent intent = new Intent(MainActivity.this, GradesActivity.class);
            startActivity(intent);
        } else {
            System.out.println("Login failed, try again");
            info.setText("Login failed, try again");
        }
    }
}
