package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Player extends Entity {
    private int score;
    public Player(String texture, float x, float y, float width, float height){
        super(texture);
        score = 0;
        score = 0;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

    }
    public int getScore(){ return score;}
    public void increaseScore(int add){
        score+=add;
    }
    @Override
    public void changePos(int newPos) {
        x+=newPos;
    }
    public String getCoord(){
        return Float.toString(x) + Float.toString(y);
    }

}
