package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AvatarScreen implements Screen {
    final Lecturer_fight game;
    OrthographicCamera camera;
    Texture alfIngeAvatar, schauAvatar, backButton, backgroundImage;
    String alfinge_text, schau_text;

    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();

    private final int screenHeight = Gdx.graphics.getHeight();
    private final int screenWidth = Gdx.graphics.getWidth();


    public AvatarScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);

        alfIngeAvatar = new Texture(Gdx.files.internal("alfinge_avatar.png"));
        schauAvatar = new Texture(Gdx.files.internal("schau_avatar.png"));
        backButton = new Texture(Gdx.files.internal("new_images/ARROW_LEFT.png"));
        backgroundImage = new Texture(Gdx.files.internal("new_images/BG.png"));

        font.setColor(new Color(0x023D8Bff));
        font.getData().setScale(6,6);
        alfinge_text = "Chosen";
        schau_text = "Bought";
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        //page items
        batch.draw(backgroundImage,0,0,screenWidth,backgroundImage.getHeight()*screenWidth/backgroundImage.getWidth());
        font.draw(batch,"Avatar Selection",screenWidth*1/4-20, screenHeight-50);
        batch.draw(backButton, -50, screenHeight-150,300,backButton.getHeight()*300/backButton.getWidth());
        // avatars
        batch.draw(alfIngeAvatar,screenWidth/8,screenHeight/2);
        batch.draw(schauAvatar,screenWidth*4/7,screenHeight/2,alfIngeAvatar.getWidth(),schauAvatar.getHeight()*alfIngeAvatar.getWidth()/schauAvatar.getWidth()+10);

        //text
        font.draw(batch, alfinge_text,screenWidth/8+30,screenHeight/2-20);
        font.draw(batch, schau_text, screenWidth*4/7+30, screenHeight/2-20);

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

