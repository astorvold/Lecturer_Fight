package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class HighScoreScreen implements Screen {

    final Lecturer_fight game;
    OrthographicCamera camera;
    ArrayList<Score> scoreList;
    boolean scoreUpdated;
    private SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont font2;

    private int screenHeight = Gdx.graphics.getHeight();
    private int screenWidth = Gdx.graphics.getWidth();

    public HighScoreScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font2 = new BitmapFont();
        scoreUpdated = false;
    }

    @Override
    public void show() {
        scoreList = new ArrayList<Score>();
        game.api.getScores(scoreList);    // retrieves the scores from the DB and saves them in scoreList

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        font.getData().setScale(5);
        font.setColor(Color.WHITE);
        font2.getData().setScale(5);
        font2.setColor(Color.WHITE);

        //System.out.println(scoreList);


        batch.begin();
        for(Score score: scoreList){
            font2.draw(batch, score.toString(), screenWidth /2, screenHeight /2);
            screenHeight = screenHeight + 500;
        }
        screenHeight = Gdx.graphics.getHeight();
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
