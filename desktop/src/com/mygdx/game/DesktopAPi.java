package com.mygdx.game;

import java.util.ArrayList;

public class DesktopAPi implements API{
    @Override
    public void getHighScores(ArrayList<Score> dataHolder) {
        System.out.println("Desktop API");
    }
}
