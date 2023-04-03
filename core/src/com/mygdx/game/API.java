package com.mygdx.game;

import java.util.ArrayList;

public interface API {
    void getScores(ArrayList<Score> dataHolder);

    void setScore(int score);

    void setInfoPlayer(Player player);

    void getInfoRival(Player player);




}