package com.mygdx.game;

import java.util.ArrayList;

public class DesktopAPI implements API {

    @Override
    public void getHighScores(ArrayList<Score> dataHolder) {
        System.out.println("Desktop API");

    }

    @Override
    public void addScore(int score) {
        System.out.println("Desktop API");

    }

    @Override
    public void setCoor(String coor) {

    }

    @Override
    public void getCoor() {

    }
}
