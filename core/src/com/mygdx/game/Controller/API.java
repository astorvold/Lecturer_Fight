package com.mygdx.game.Controller;

import com.mygdx.game.Model.Player;
import com.mygdx.game.Model.Score;

import java.util.ArrayList;

public interface API {
    void getScores(ArrayList<Score> dataHolder);

    void setScore(int score);

    void setInfoPlayer(Player player);

    void getInfoRival(Player player);

    void removePlayer(Player player);




}