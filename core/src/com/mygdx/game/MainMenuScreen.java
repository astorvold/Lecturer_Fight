package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    final Lecturer_fight game;
    OrthographicCamera camera;
    Texture startButton;
    Texture settingsButton;
    Texture scoreButton;

    public MainMenuScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);

        startButton = new Texture(Gdx.files.internal("start.png"));
        settingsButton = new Texture(Gdx.files.internal("settings.png"));
        scoreButton = new Texture(Gdx.files.internal("score.png"));
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Lecturer fight!!! ", 200, 380);
        game.font.draw(game.batch, "Click on start to begin!", 200, 370);
        game.batch.draw(startButton, 150, 250);
        game.batch.draw(settingsButton, 500, 250);
        game.batch.draw(scoreButton, 150, 100);
        game.batch.end();


        //&& Gdx.graphics.getHeight()
        //when startButton is touched -> go to gameScreen
        if (Gdx.input.isTouched()) {
            System.out.println(Gdx.input.getX() + " "+ Gdx.input.getY());
            if(Gdx.input.getX() > 130 && Gdx.input.getX() < 300 && Gdx.input.getY() > 70 && Gdx.input.getY() < 180){
                game.setScreen(new GameScreen(game));
                dispose();
            }

        }
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
