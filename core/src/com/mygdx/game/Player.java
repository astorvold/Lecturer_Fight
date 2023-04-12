package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Player extends Entity {
    private int score;
    private boolean ready;
    public Player(Texture texture, float x, float y, float width, float height){
        super(texture);
        score = 0;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.ready = false;

    }
    public int getScore(){ return score;}
    public void increaseScore(int add){
        score+=add;
    }
    @Override
    public void changePos(int newPos) {
        x+=newPos;
    }
    public void setScore(int score){
        this.score = score;
    }

    public void setReady(boolean ready){ this.ready = ready;}

    public boolean isReady(){ return this.ready; }

}
