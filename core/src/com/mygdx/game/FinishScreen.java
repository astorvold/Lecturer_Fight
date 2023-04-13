package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class FinishScreen implements Screen {

    final Lecturer_fight game;
    OrthographicCamera camera;
    Texture finishButton;
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();
    public FinishScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);

        finishButton = new Texture(Gdx.files.internal("start.png"));
        System.out.println("this is finish screen");

    }
    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();

        batch.begin();
        font.draw(batch, "Finish screen!!! ", 200, 380);
        batch.draw(finishButton, 150, 250);
        batch.end();

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
