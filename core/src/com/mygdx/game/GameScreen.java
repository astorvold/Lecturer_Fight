package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
public class GameScreen implements Screen{

    final Lecturer_fight game;

    OrthographicCamera camera;

    Texture lossButton;

    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();;

    public GameScreen(final Lecturer_fight game) {
        this.game = game;
        System.out.println("game screen");


        // create the camera and the SpriteBatch
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);


        //click on if you lost
        lossButton = new Texture(Gdx.files.internal("badlogic.jpg"));

    }


    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        batch.begin();
        font.draw(batch, "Playing ", 0, 480);

        batch.draw(lossButton, 150, 100);

        batch.end();

        // If user touch, race is finished

        if (Gdx.input.isTouched()) {
            System.out.println(Gdx.input.getX() + " "+ Gdx.input.getY());

            if(Gdx.input.getX() > 530 && Gdx.input.getX() < 700 && Gdx.input.getY() > 70 && Gdx.input.getY() < 180) {
                dispose();
                game.setScreen(new FinishScreen(game));

            }
        }
    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        lossButton.dispose();
    }

}
