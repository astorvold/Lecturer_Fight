package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Obstacle {
    private float x,y;
    private final float height, width;
    Texture texture;

    public Obstacle(String texture, float x, float y, float width, float height){
        this.width = width+generateRandomNumber(256,512);
        this.height = height;
        if(x==0) this.x = 0;
        else this.x = x-this.width;
        this.y = y;
        this.texture = new Texture(texture);
    }

    private float generateRandomNumber(int from, int to){
        return (float)Math.floor(Math.random() * (to - from + 1) + from);
    }

    public Texture getTexture() {
        return texture;
    }
    public float getX() { return x; }

    public float getY() { return y; }

    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }

    public float getHeight() { return height; }

    public float getWidth() { return width; }

    public void changePos(int newPos){
        y+=newPos;
    }
}
