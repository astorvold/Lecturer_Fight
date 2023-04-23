package com.mygdx.game;

public class PlayerController {

    private Player model;

    public PlayerController(Player model) {
        this.model = model;
    }

    public void changePos(int newPosition){
        this.model.changePos(newPosition);
    }
    public void setReady(boolean ready){
        this.model.setReady(ready);
    }
    public void increaseScore(int score){
        this.model.increaseScore(score);
    }




}
