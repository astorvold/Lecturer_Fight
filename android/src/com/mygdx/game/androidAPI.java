package com.mygdx.game;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class androidAPI implements API{

    FirebaseDatabase database;

    // references to the users, scores and coordinates
    DatabaseReference usersRef;
    DatabaseReference scoresRef;
    DatabaseReference coorRef;

    public androidAPI() {
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference();
    }

    @Override
    public void getScores(ArrayList<Score> dataHolder) {
        scoresRef = database.getReference("scores");
        scoresRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Object value = snapshot.getValue();
                if (value instanceof Map){ // gets the scores and save them in dataholder
                    Map<String, Object> mapValue = (Map<String, Object>) value;
                    for (Map.Entry<String, Object> entry : mapValue.entrySet()) {
                        dataHolder.add(new Score(Integer.parseInt(entry.getKey()),entry.getValue().toString()));
                        }
                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("problem");
            }
        });
    }

    @Override
    public void setScore(int score) {
        scoresRef = database.getReference("scores");

        if(this.scoresRef != null){
            scoresRef.child(Integer.toString(score)).setValue("pepe");
        }
    }

    @Override
    public void setCoors(int coor) {
        coorRef = database.getReference("coorsPlayer1");
        coorRef.child("player1").setValue(coor);

    }


    @Override
    public void getCoors(Player player) {
        coorRef = database.getReference("coorsPlayer2");
        coorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                player.setScore(Integer.parseInt(snapshot.child("player2").getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("problem");
            }
        });
    }


}
