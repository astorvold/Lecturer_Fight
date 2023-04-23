package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Player extends Entity {
    private int score;
    private boolean ready;
    private boolean isAlive = true;
    private String name;

    public Player(Texture texture, float x, float y, float width, float height){
        super(texture);
        score = 0;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.ready = false;
    }

    public int getScore(){
        return score;
    }
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

    public void setReady(boolean ready){
        this.ready = ready;
    }

    public boolean isReady(){
        return this.ready;
    }

    public void setTexture(Texture texture){
        this.texture = texture;
    }

    public boolean isAlive(){
        return this.isAlive;
    }

    public void isDead(){
        this.isAlive = false;
    }




}
