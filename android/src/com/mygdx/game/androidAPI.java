package com.mygdx.game;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.game.Controller.Configuration;
import com.mygdx.game.Model.Player;
import com.mygdx.game.Model.Score;

import java.util.ArrayList;
import java.util.Map;

public class androidAPI implements API {

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
        try{

        scoresRef = database.getReference("scores");
        scoresRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Object value = snapshot.getValue();
                if (value instanceof Map) { // gets the scores and save them in dataholder
                    Map<String, Object> mapValue = (Map<String, Object>) value;
                    for (Map.Entry<String, Object> entry : mapValue.entrySet()) {
                        dataHolder.add(new Score(Integer.parseInt(entry.getKey()), entry.getValue().toString()));
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("problem");
            }
        });
        }catch(Exception e){
            System.out.print(e);
        }
    }

    @Override
    public void setScore(int score) {
        scoresRef = database.getReference("scores");

        if(this.scoresRef != null){
            scoresRef.child(Integer.toString(score)).setValue(Configuration.getInstance().getName());
        }
    }

    @Override
    public void setInfoPlayer(Player player) {
        coorRef = database.getReference("Players");
        coorRef.child(Configuration.getInstance().getName()).child("score").setValue(player.getScore());
        coorRef.child(Configuration.getInstance().getName()).child("ready").setValue(player.isReady());

    }
    public void removePlayer(Player player){
        coorRef = database.getReference("Players");
        coorRef.child(Configuration.getInstance().getName()).removeValue();
    }

    @Override
    public void getInfoRival(Player player) {
        coorRef = database.getReference("Players");
        coorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //System.out.println(snapshot.getValue());
                Object value = snapshot.getValue();
                try {
                    Map<String, Object> mapValue = (Map<String, Object>) value;
                    for (Map.Entry<String, Object> entry : mapValue.entrySet()) {
                        if (!entry.getKey().equals(Configuration.getInstance().getName())) {

                            Map<String, Object> innerMap = (Map<String, Object>) entry.getValue();
                            boolean readyValue = (boolean) innerMap.get("ready");
                            int scoreValue = (int) ((long) innerMap.get("score"));
                            if (readyValue) {
                                player.setScore(scoreValue);
                                player.setReady(true);
                            }


                        }

                    }
                }catch(Exception e){
                System.out.print(e);
            }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("problem");
            }
        });
    }


}
