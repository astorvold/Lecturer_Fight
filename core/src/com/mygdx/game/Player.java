package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Player extends Entity {
    private int score;

    public Player(String texture, float x, float y, float width, float height){
        super(texture, x, y, width, height);
        score = 0;
    }

    public int getScore(){ return score;}
    public void increaseScore(int add){
        score+=add;
    }
}
