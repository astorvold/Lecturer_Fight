package com.mygdx.game.Controller;

import com.badlogic.gdx.Input;
import com.mygdx.game.Controller.Configuration;

public class AddNameListener implements Input.TextInputListener {
    @Override
    public void input(String name) {
        Configuration.getInstance().setName(name);
    }

    @Override
    public void canceled() {
    }
}
