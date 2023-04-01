package com.mygdx.game;

import java.util.ArrayList;

public interface API {
    void getHighScores(ArrayList<Score> dataHolder);

    void addScore(int score);

    void setCoor(String coor);

    void getCoor();



}