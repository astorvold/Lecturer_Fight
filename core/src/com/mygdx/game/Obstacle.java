package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Obstacle {
    private float x,y, height, width;
    Texture texture;
    float screenHeight = Gdx.graphics.getHeight();
    float screenWidth = Gdx.graphics.getWidth();

    public Obstacle(String texture){
        width = 256;
        height = 96;
        x = screenWidth-width;
        y = screenHeight;
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
        y+=newPos;
    }
}
