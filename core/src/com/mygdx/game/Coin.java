package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Coin extends Entity{

    public Coin(String texture, float x, float y, float width, float height) {
        super(texture);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    @Override
    public void changePos(int newPos) {
        y+=newPos;
    }


}
