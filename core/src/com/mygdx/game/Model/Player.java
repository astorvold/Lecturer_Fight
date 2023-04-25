package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.Texture;

public class Player extends Entity {
    private int score;
    private boolean ready;
    private boolean isAlive = true;
    private boolean busy;
    private String Name;

    public Player(Texture texture, float x, float y, float width, float height){
        super(texture);
        score = 0;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.ready = false;
        this.busy = false;
        this.Name = " ";
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
    public boolean isAlive(){
        return this.isAlive;
    }

    public void isDead(){
        this.isAlive = false;
    }
    public boolean getBusy() { return this.busy; }
    public void setBusy() {this.busy = true;}
    public String getName(){return this.Name;}
    public void setName(String name){this.Name = name;}



}
