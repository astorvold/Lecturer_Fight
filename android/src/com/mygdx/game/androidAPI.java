package com.mygdx.game;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class androidAPI implements API{

    FirebaseDatabase database;
    DatabaseReference myRef;

    public androidAPI() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    @Override
    public void getHighScores(ArrayList<Score> dataHolder) {
        if(this.myRef != null){
            myRef.setValue("hello world");
        }
    }
}
