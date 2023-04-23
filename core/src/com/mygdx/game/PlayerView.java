package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerView {

    //private Texture texture;
    //private Sprite sprite;
    private Player model;

    public PlayerView(Player model) {
        this.model = model;
        //texture = new Texture("character.png"); // Load texture from file
        //sprite = new Sprite(texture);
        //sprite.setPosition(model.getX(), model.getY());
    }

    public void draw(SpriteBatch batch) {
        batch.draw(model.getTexture(), model.getX(), model.getY(), model.getWidth(), model.getHeight());
    }

}
