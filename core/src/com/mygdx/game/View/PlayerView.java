package com.mygdx.game.View;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Model.Player;

public class PlayerView {

    //private Texture texture;
    //private Sprite sprite;
    private final Player model;


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
