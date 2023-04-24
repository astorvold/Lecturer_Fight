package com.mygdx.game.Model;

import com.badlogic.gdx.graphics.Texture;

public class Coin extends Entity {

    public Coin(Texture texture, float x, float y, float width, float height) {
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
