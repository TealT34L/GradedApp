package com.example.gradedapp;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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
                    validateLogin(userName.getText().toString(), password.getText().toString());
                } catch (IOException | ClassNotFoundException | InterruptedException e) {
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
                        validateLogin(userName.getText().toString(), password.getText().toString());
                    } catch (IOException | ClassNotFoundException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });

    }

    private void validateLogin(String userNameText, String passwordText) throws IOException, ClassNotFoundException, InterruptedException {
        loginButton.setText("Loading");
        Thread t = new Thread(new Runnable() {
            private String userNameText;
            private String passwordText;
            private Button button;

            public Runnable setVals(String userNameText, String passwordText, Button button){
                this.userNameText = userNameText;
                this.passwordText = passwordText;
                this.button = button;
                return this;
            }

            public void run(){
                try {
                    cl = new Client(userNameText, passwordText);
                    ClientThreader clientThread = new ClientThreader(cl);
                    clientThread.start();

                    Runnable updateButton = new Runnable(){
                        String text;
                        public Runnable setText(String text){
                            this.text = text;
                            return this;
                        }

                        public void run(){
                            loginButton.setText(text);
                        }
                    }.setText("checking login validity");



                    long time = System.nanoTime();
                    int num = 1;
                    runOnUiThread(updateButton);
                    while(!cl.getValidity()){

                    }

                    updateButton = new Runnable(){
                        String text;
                        public Runnable setText(String text){
                            this.text = text;
                            return this;
                        }

                        public void run(){
                            loginButton.setText(text);
                        }
                    }.setText("refreshing grades");
                    runOnUiThread(updateButton);
                    while(!cl.hasRefreshed()){
                        //loginButton.setText("Refreshing");
                    }

                    updateButton = new Runnable(){
                        String text;
                        public Runnable setText(String text){
                            this.text = text;
                            return this;
                        }

                        public void run(){
                            loginButton.setText(text);
                        }
                    }.setText("loading grades");
                    runOnUiThread(updateButton);
                    if (cl.getValidity()){
                        System.out.println("Login worked");

                        classes = cl.getClasses();
                        Intent intent = new Intent(MainActivity.this, GradesActivity.class);
                        startActivity(intent);
                    } else {
                        updateButton = new Runnable(){
                            String text;
                            public Runnable setText(String text){
                                this.text = text;
                                return this;
                            }

                            public void run(){
                                loginButton.setText(text);
                            }
                        }.setText("Login failed, try again");
                        runOnUiThread(updateButton);
                        System.out.println("Login failed, try again");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.setVals(userNameText, passwordText, loginButton)
        );
        t.start();

    }
}
