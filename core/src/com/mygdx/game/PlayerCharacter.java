package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class PlayerCharacter {
    private Player model;
    private PlayerView view;
    private PlayerController controller;

    public PlayerCharacter(Texture texture, float x, float y, float width, float height) {
        model = new Player(texture, x, y, width, height);
        view = new PlayerView(model);
        controller = new PlayerController(model);
    }
    public PlayerController getPlayerController(){
        return controller;
    }
    public PlayerView getPlayerView(){
        return view;
    }
    public Player getPlayerModel(){
        return model;
    }
}
