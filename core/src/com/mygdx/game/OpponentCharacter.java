package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class OpponentCharacter {
    private Player model;
    private PlayerView view;
    private PlayerController controller;

    public OpponentCharacter(Texture texture, float x, float y, float width, float height) {
        model = new Player(texture, x, y, width, height);
        view = new PlayerView(model);
        controller = new PlayerController(model);
    }
}
