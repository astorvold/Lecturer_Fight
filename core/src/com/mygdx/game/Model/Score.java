package com.mygdx.game.Model;

public class Score {
    private final int score;
    private final String name;
    public Score(int score, String name) {
        this.score = score;
        this.name = name;
    }
    public String toString() {
        return this.name + ": " + this.score;
    }
    public int getScore(){return score;}
}

