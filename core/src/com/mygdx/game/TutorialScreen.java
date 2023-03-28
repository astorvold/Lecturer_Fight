package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TutorialScreen implements Screen {
    final Lecturer_fight game;
    OrthographicCamera camera;
    Texture backButton, forwardButton, backgroundImage;

    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();

    private final int screenHeight = Gdx.graphics.getHeight();
    private final int screenWidth = Gdx.graphics.getWidth();

    public TutorialScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);

        //create Textures
        backButton = new Texture(Gdx.files.internal("new_images/ARROW_LEFT.png"));
        forwardButton = new Texture(Gdx.files.internal("new_images/ARROW_RIGHT.png"));
        backgroundImage = new Texture(Gdx.files.internal("new_images/LIGHT_BG.png"));

        //edit font
        font.setColor(new Color(0x023D8Bff));
        font.getData().setScale(6,6);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        //page items
        batch.draw(backgroundImage,0,0,screenWidth,backgroundImage.getHeight()*screenWidth/backgroundImage.getWidth());
        batch.draw(backButton, -50, screenHeight-150,300,backButton.getHeight()*300/backButton.getWidth());

        //text
        font.draw(batch,"Tutorial",screenWidth*1/3+20, screenHeight-50);

        //placeholder for actual tutorial-image
        batch.draw(backgroundImage,screenWidth/6,screenHeight/8,screenWidth*2/3,screenHeight*3/4);

        //forward and backward items
        batch.draw(forwardButton, screenWidth-500, 150,500,backButton.getHeight()*500/backButton.getWidth());
        batch.draw(backButton, 0, 150,500,backButton.getHeight()*500/backButton.getWidth());
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

