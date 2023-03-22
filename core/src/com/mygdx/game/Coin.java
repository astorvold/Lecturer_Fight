package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Coin {

    private float x,y;
    private final float height, width;
    Texture texture;

    public Coin(String texture, float x, float y, float width, float height){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.texture = new Texture(texture);
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
