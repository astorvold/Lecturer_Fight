package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Obstacle extends Entity{
    public Obstacle(Texture texture, float x, float y, float width, float height) {
        super(texture);
        this.width = width+generateRandomNumber(128,512);
        this.height = height;
        if(x==0) this.x = 0;
        else this.x = x-this.width;
        this.y = y;
    }

    @Override
    public void changePos(int newPos) {
        y+=newPos;
    }
}
