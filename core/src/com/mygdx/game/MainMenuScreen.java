package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import org.w3c.dom.Text;

public class MainMenuScreen implements Screen {

    //Distance between buttons
    private final int distance = 250;
    final Lecturer_fight game;
    OrthographicCamera camera;
    Texture playButton, settingsButton, scoreButton, avatarButton, toggleONButton, toggleOFFButton,tutorialButton,titleImage,backgroundImage;

    Preferences prefs = Gdx.app.getPreferences("Lecturer-Fight");
    private final float screenHeight = Gdx.graphics.getHeight();
    private final float screenWidth = Gdx.graphics.getWidth();

    private final float buttonWidth = screenWidth/2;
    private float buttonHeight;
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();


    public MainMenuScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);

        /*
        prefs.putString("Avatar", "alfinge_avatar.png");
        prefs.flush();*/


        playButton = new Texture(Gdx.files.internal("new_images/PLAY.png"));
        settingsButton = new Texture(Gdx.files.internal("new_images/SETTINGS.png"));
        scoreButton = new Texture(Gdx.files.internal("new_images/HIGHSCORE.png"));
        toggleONButton = new Texture(Gdx.files.internal("new_images/TOGGLE_ON.png"));
        toggleOFFButton = new Texture(Gdx.files.internal("new_images/TOGGLE_OFF.png"));
        tutorialButton = new Texture(Gdx.files.internal("new_images/TUTORIAL.png"));

        avatarButton = new Texture(Gdx.files.internal(prefs.getString("Avatar")));
        titleImage = new Texture(Gdx.files.internal("new_images/TITLE.png"));
        backgroundImage = new Texture(Gdx.files.internal("new_images/BG.png"));

        buttonHeight = playButton.getHeight()*buttonWidth/playButton.getWidth();

        font.getData().setScale(5,5);
        font.setColor(new Color(0x023D8Bff));

        prefs.putInteger("AvatarHeight", 522);
        prefs.putInteger("AvatarWidth", avatarButton.getWidth()*prefs.getInteger("AvatarHeight")/avatarButton.getHeight());
        prefs.flush();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        float playButtonX = screenWidth/2;
        float playButtonY = screenHeight/2;
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //design
        batch.draw(backgroundImage,0,0,screenWidth,backgroundImage.getHeight()*screenWidth/backgroundImage.getWidth());
        batch.draw(titleImage,screenWidth/6,screenHeight-250,screenWidth*2/3,titleImage.getHeight()*(screenWidth*2/3)/titleImage.getWidth());

        //text
        font.draw(batch, "Made by Group 16", screenWidth/2-distance*7/6, 150);
        font.draw(batch, "Multiplayer",(screenWidth-buttonWidth)/2+20,((screenHeight-buttonHeight)/2)+(buttonHeight-distance));

        //square buttons
        batch.draw(playButton, (screenWidth-buttonWidth)/2,(screenHeight-buttonHeight)/2, buttonWidth, buttonHeight);
        batch.draw(scoreButton, (screenWidth-buttonWidth)/2,(screenHeight-buttonHeight)/2-distance*4/3, buttonWidth, buttonHeight);
        batch.draw(tutorialButton,(screenWidth-buttonWidth)/2,(screenHeight-buttonHeight)/2-distance*7/3,buttonWidth, buttonHeight);

        //other buttons
        batch.draw(settingsButton, screenWidth-230, screenHeight-150,300,settingsButton.getHeight()*300/settingsButton.getWidth());
        batch.draw(toggleONButton, screenWidth/2+35,((screenHeight-buttonHeight)/2)-distance*4/9,300,toggleONButton.getHeight()*300/toggleONButton.getWidth());
        batch.draw(avatarButton, (screenWidth-prefs.getInteger("AvatarWidth"))/2, playButtonY+(float)avatarButton.getWidth()/2, prefs.getInteger("AvatarWidth"), prefs.getInteger("AvatarHeight"));

        batch.end();


        //&& Gdx.graphics.getHeight()
        //when playButton is touched -> go to gameScreen
        if (Gdx.input.isTouched()) {
            System.out.println(Gdx.input.getX() + " "+ Gdx.input.getY());
            if(Gdx.input.getX() > 0 && Gdx.input.getX() < 300 && Gdx.input.getY() > 0 && Gdx.input.getY() < 300){
                dispose();
                game.setScreen(new GameScreen(game));
            }

            //used to return to avatarscreen to check if selection is saved
            else if(Gdx.input.getX()>screenWidth/2){
                dispose();
                game.setScreen(new AvatarScreen(game));
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
        camera = null;
    }


}
