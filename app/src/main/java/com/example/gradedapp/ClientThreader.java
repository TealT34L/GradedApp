package com.example.gradedapp;

import com.example.gradedapp.Client;

import java.io.IOException;

public class ClientThreader extends Thread{

    Client cl;

    public ClientThreader(Client cl){
        this.cl = cl;
    }

    public void run(){
        try {
            System.out.println("Setting up");
            cl.setup();
            System.out.println("Validating");
            cl.validate();
            System.out.println("refreshing");
            cl.refresh();
            System.out.println("closing thread");
            cl.closeThread();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void interrupt(){

    }

}
