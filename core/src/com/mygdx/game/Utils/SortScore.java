package com.mygdx.game.Utils;

import com.mygdx.game.Model.Score;

import java.util.Comparator;

public class SortScore implements Comparator<Score> {

    @Override
    public int compare(Score score, Score t1) {
        return t1.getScore() - score.getScore();
    }
}
