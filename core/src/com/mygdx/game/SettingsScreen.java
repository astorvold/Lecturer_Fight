package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

public class SettingsScreen implements Screen {
    final Lecturer_fight game;
    OrthographicCamera camera;
    TextButton musicButton;
    Texture soundButton;
    Texture sound;
    Texture noSound;
    Music music = Gdx.audio.newMusic(Gdx.files.internal("Dumb Ways To Die.mp3"));
    Music point = Gdx.audio.newMusic(Gdx.files.internal("point.mp3"));


    private final float screenHeight = Gdx.graphics.getHeight();

    private final float screenWidth = Gdx.graphics.getWidth();
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();


    public SettingsScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);
        font.getData().setScale(10,10);

        sound = new Texture(Gdx.files.internal("sound-icon.png"));
        noSound = new Texture(Gdx.files.internal("sound-off-icon.png"));


        //changing the sound settings on the android device
        //AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC);

        music.setVolume(1.0f);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.begin();
        font.draw(batch,"Settings",screenWidth/4, screenHeight-10);
        batch.draw(sound, screenWidth-sound.getWidth()-20, screenHeight-sound.getHeight()-20);
        music.setLooping(true);
        music.play();
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
        batch.dispose();
        music.dispose();

    }
}

