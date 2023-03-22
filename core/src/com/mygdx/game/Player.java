package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Player {
    private float x,y, height, width;
    Texture texture;
    float screenHeight = Gdx.graphics.getHeight();
    float screenWidth = Gdx.graphics.getWidth();

    int score;

    public Player(String texture){
        score = 0;
        width = 96;
        height = 96;
        x = screenWidth/2;
        y = screenHeight/2;
        this.texture = new Texture(texture);
    }
    public Texture getTexture() {
        return texture;
    }

    public float getX() { return x; }

    public float getY() { return y; }

    public float getHeight() { return height; }

    public float getWidth() { return width; }

    public void changePos(int newPos){
        x+=newPos;
    }

    public void increaseScore(int add){
        score+=add;
    }
}
