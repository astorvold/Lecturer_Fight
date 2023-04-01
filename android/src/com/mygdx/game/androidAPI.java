package com.mygdx.game;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class androidAPI implements API{

    FirebaseDatabase database;
    DatabaseReference usersRef;
    DatabaseReference scoresRef;

    public androidAPI() {
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference();
    }

    @Override
    public void getHighScores(ArrayList<Score> dataHolder) {
        scoresRef = database.getReference("scores");
    }

    @Override
    public void addScore(int score) {
        scoresRef = database.getReference("scores");

        if(this.scoresRef != null){
            scoresRef.child("pepe").setValue(score);
        }
    }

    @Override
    public void setCoor(String coor) {

    }

    @Override
    public void getCoor() {

    }


}
