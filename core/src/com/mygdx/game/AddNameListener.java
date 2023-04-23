package com.mygdx.game;

import com.badlogic.gdx.Input;

public class AddNameListener implements Input.TextInputListener {
    @Override
    public void input(String name) {
        Configuration.getInstance().setName(name);
    }

    @Override
    public void canceled() {
    }
}
