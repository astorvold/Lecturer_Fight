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
        game.font.draw(game.batch, "Welcome to Drop!!! ", 100, 150);
        game.font.draw(game.batch, "Tap anywhere to begin!", 100, 100);
        game.batch.draw(startButton, 0, 0);

        game.batch.end();

        //must be changed for a "start button" image
        game.startButton.begin(ShapeRenderer.ShapeType.Filled);
        game.startButton.setColor(0, 1, 0, 1);
        game.startButton.rect(300,400,200,50);
        game.startButton.end();

        //must be changed for a "start button" image
        game.startButton.begin(ShapeRenderer.ShapeType.Filled);
        game.startButton.setColor(0, 1, 0, 1);
        game.startButton.rect(300,400,200,50);
        game.startButton.end();

        //must be changed for a "start button" image
        game.startButton.begin(ShapeRenderer.ShapeType.Filled);
        game.startButton.setColor(0, 1, 0, 1);
        game.startButton.rect(300,400,200,50);
        game.startButton.end();


        //when startButton is touched -> go to gameScreen
        if (Gdx.input.isTouched()) {
            if(Gdx.input.getX() - 100 < 20 && Gdx.graphics.getHeight() - Gdx.input.getY() - 100 < 10){
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
