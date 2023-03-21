package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import jdk.tools.jmod.Main;

public class GameScreen implements Screen{

    final Lecturer_fight game;
    float screenHeight = Gdx.graphics.getHeight();
    float screenWidth = Gdx.graphics.getWidth();
    OrthographicCamera camera;
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    public Player player;
    public Obstacle obstacle;


    private Sprite bird;

    public GameScreen(final Lecturer_fight game) {
        this.game = game;
        System.out.println("game screen");
        // create the camera and the SpriteBatch
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //Initializing objects
        player = new Player("bird.png");
        obstacle = new Obstacle("obstacle.png");
    }


    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to clear are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);

        // begin a new batch and draw the bucket and
        // all drops
        batch.begin();
        batch.draw(player.getTexture(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
        batch.draw(obstacle.getTexture(), obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
        font.draw(batch, "Playing ", 0, 480);
        batch.end();

        // If user touch, race is finished
        if (Gdx.input.isTouched()) {
            if(Gdx.input.getX() > screenWidth/2)
                if (player.getX() < screenWidth -96) player.changePos(10);
                else player.changePos(-10);
            else
                if (player.getX() > 0) player.changePos(-10);
                else player.changePos(10);
        }
        obstacle.changePos(-1);
        if(checkColisions(player, obstacle)){
            game.setScreen(new MainMenuScreen(game));
        }
    }

    public boolean checkColisions(Player p, Obstacle o){
        float x1 = p.getX();
        float y1 = p.getY();

        float x2 = p.getX() + p.getWidth();
        float y2 = p.getY() + p.getHeight();

        float x3 = o.getX();
        float y3 = o.getY();

        float x4 = o.getX() + o.getWidth();
        float y4 = o.getY() + o.getHeight();

        return ((x1 >= x3 && x1 <= x4) && (y1 >= y3 && y1 <= y4))
                || ((x2 >= x3 && x2 <= x4) && (y2 >= y3 && y2 <= y4))
                || ((x2 >= x3 && x2 <= x4) && (y1 >= y3 && y1 <= y4))
                || ((x1 >= x3 && x1 <= x4) && (y2 >= y3 && y2 <= y4));
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
        player.getTexture().dispose();
    }
}
