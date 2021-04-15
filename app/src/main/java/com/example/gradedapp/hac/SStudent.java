package com.example.gradedapp.hac;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

public class SStudent extends AsyncTask<String, Integer, Long> {

    @Override
    protected Long doInBackground(String... strings) {
        try {
            System.out.println(strings[0]);
            switch(strings[0]){
                case "setup":
                    setup();
                    break;
                case "validate":
                    validationReady = false;
                    System.out.println("validity isL " + validate(strings[1], strings[2]));
                    validationReady = true;
                    break;
                case "refresh":
                    refresh();
                    break;
                case "close":
                    closeThread();
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (long)0;
    }


    protected void onPostExecute(Long result) {

    }



    private static boolean validationReady = false;
    private static boolean refreshReady = false;
    private static Client cl;

    public static void setup() throws IOException {
        cl = new Client();
    }

    public static void closeThread() throws IOException {
        cl.closeThread();
    }

    public static boolean validate(String user, String pass) throws IOException {
        return cl.validate(user, pass);
    }

    public static boolean isValidationReady(){return validationReady;}

    public static boolean isRefreshReady(){return refreshReady;}

    public static boolean getValidity() throws IOException, ClassNotFoundException {

        System.out.println("checking validity (getValidity)");
        validationReady = false;
        return cl.getValidity();
    }

    public static void refresh() throws IOException, ClassNotFoundException {
        cl.refresh();
        refreshReady = true;
    }

    public static ArrayList<ClassRoom> getClasses(){return cl.getClasses();}

    public static ArrayList<ClassRoom> getOldClasses(){return cl.getClasses();}


}
