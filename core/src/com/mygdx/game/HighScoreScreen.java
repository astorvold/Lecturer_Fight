package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class HighScoreScreen implements Screen {

    final Lecturer_fight game;
    OrthographicCamera camera;
    Texture startButton;
    Texture settingsButton;
    Texture scoreButton;

    public HighScoreScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
