package com.example.gradedapp;

import com.example.gradedapp.hac.ClassRoom;

public class StaticClassHolder {

    private static ClassRoom heldClass;

    public static void setClassRoom(ClassRoom classR){
        heldClass = classR;
    }

    public static ClassRoom getClassRoom(){
        return heldClass;
    }

}
