package com.mygdx.game;

public class Score {
    private int score;
    private String name;
    public Score(int score, String name) {
        this.score = score;
        this.name = name;
    }
    public String toString() {
        return this.name + ":" + this.score;
    }
}
