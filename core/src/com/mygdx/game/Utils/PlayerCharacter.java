package com.mygdx.game.Utils;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Controller.PlayerController;
import com.mygdx.game.Model.Player;
import com.mygdx.game.View.PlayerView;

public class PlayerCharacter {
    private final Player model;
    private final PlayerView view;
    private final PlayerController controller;

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
