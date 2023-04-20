package com.mygdx.game;

import java.util.Comparator;

public class Score {
    int score;
    private String name;
    public Score(int score, String name) {
        this.score = score;
        this.name = name;
    }
    public String toString() {
        return this.name + ": " + this.score;
    }
}

class SortScore implements Comparator<Score> {

    @Override
    public int compare(Score score, Score t1) {
        return t1.score - score.score;
    }
}
