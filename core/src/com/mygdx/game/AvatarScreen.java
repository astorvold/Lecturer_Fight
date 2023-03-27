package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AvatarScreen implements Screen {
    final Lecturer_fight game;
    OrthographicCamera camera;
    Texture alfIngeAvatar;
    Texture schauAvatar;
    Texture backButton;

    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();

    private final int screenHeight = Gdx.graphics.getHeight();
    private final int screenWidth = Gdx.graphics.getWidth();


    public AvatarScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);

        alfIngeAvatar = new Texture(Gdx.files.internal("alfinge_avatar.png"));
        schauAvatar = new Texture(Gdx.files.internal("alfinge_avatar.png"));
        backButton = new Texture(Gdx.files.internal("alfinge_avatar.png"));
        font.setColor(2,61,139,1);
        font.getData().setScale(2,2);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        font.draw(batch,"Avatar Selection",(screenWidth-font.getScaleX())/2,screenHeight/2);
        batch.draw(alfIngeAvatar,screenWidth/4,screenHeight/3);
        batch.draw(schauAvatar,screenWidth*3/4,screenHeight/3);
        batch.draw(backButton,10,110,100,100);
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

