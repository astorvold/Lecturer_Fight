package com.mygdx.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Controller.ButtonFactory;
import com.mygdx.game.Controller.Configuration;
import com.mygdx.game.Lecturer_fight;

public class SettingsScreen implements Screen {
    final Lecturer_fight game;
    OrthographicCamera camera;
    Texture imageBack, backgroundImage, imageToggleON, imageToggleOFF, settingsTxt;
    private Image buttonBack;
    private CheckBox musicCheckBox, soundCheckBox;
    private final Stage stage;
    private final float screenHeight = Gdx.graphics.getHeight();

    private final float screenWidth = Gdx.graphics.getWidth();
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();


    SettingsScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);
        font.getData().setScale(10,10);

        //sound = new Texture(Gdx.files.internal("sound-icon.png"));
        //noSound = new Texture(Gdx.files.internal("sound-off-icon.png"));

        //create Textures
        backgroundImage = new Texture(Gdx.files.internal("new_images/LIGHT_BG.png"));
        settingsTxt = new Texture(Gdx.files.internal("new_images/SettingsTxt.png"));

        //edit font
        font.setColor(new Color(0x023D8Bff));
        font.getData().setScale(6,6);

        Configuration.getInstance().getMusic().setVolume(0.5f);
        Configuration.getInstance().getPoint().setVolume(0.5f);
        Configuration.getInstance().getDie().setVolume(0.5f);
        if (Configuration.getInstance().isMusic_on()){
            Configuration.getInstance().playMusic();
        }
        initializeButtons();
        stage = new Stage(new ScreenViewport());
        stage.addActor(buttonBack);
        stage.addActor(musicCheckBox);
        stage.addActor(soundCheckBox);
        Gdx.input.setInputProcessor(stage);
    }

    private void initializeButtons(){


        imageBack = new Texture(Gdx.files.internal("new_images/ARROW_LEFT.png"));
        buttonBack = ButtonFactory.createButton(imageBack,0.1f*screenWidth -0.35f*screenWidth/2, screenHeight *0.9f,0.35f*screenWidth,0.08f*screenHeight);


        imageToggleON = new Texture(Gdx.files.internal("new_images/TOGGLE_ON.png"));
        imageToggleOFF = new Texture(Gdx.files.internal("new_images/TOGGLE_OFF.png"));
        CheckBox.CheckBoxStyle checkboxStyle = new CheckBox.CheckBoxStyle();
        font.setColor(Color.WHITE);
        font.getData().setScale(7);
        checkboxStyle.font = font;
        checkboxStyle.checkboxOn = new TextureRegionDrawable(new TextureRegion(imageToggleON));
        checkboxStyle.checkboxOff = new TextureRegionDrawable(new TextureRegion(imageToggleOFF));
        musicCheckBox = new CheckBox("", checkboxStyle);
        soundCheckBox = new CheckBox("", checkboxStyle);
        musicCheckBox.setBounds(screenWidth*2/3,screenHeight*3/4-110,350,imageToggleON.getHeight()*350f/imageToggleON.getWidth());
        soundCheckBox.setBounds(screenWidth*2/3,screenHeight*2/3-110,350,imageToggleON.getHeight()*350f/imageToggleON.getWidth());
    }

    @Override
    public void show() {
        buttonBack.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainMenuScreen(game));
            }
        });
        musicCheckBox.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(Configuration.getInstance().isMusic_on()) {
                    Configuration.getInstance().changeMusic();
                }
                else{
                    Configuration.getInstance().changeMusic();
                }
            }
        });
        soundCheckBox.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                Configuration.getInstance().changeSound();
            }
        });
    }

    @Override
    public void render(float delta) {
        //ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.begin();
        //page items
        batch.draw(backgroundImage,0,0,screenWidth,backgroundImage.getHeight()*screenWidth/backgroundImage.getWidth());

        //texts
        batch.draw(settingsTxt,screenWidth/3, screenHeight*0.85f, screenWidth/2, screenHeight/12);
        //font.draw(batch,"Settings",screenWidth*1/3+20, screenHeight-50);
        font.draw(batch,((Configuration.getInstance().isMusic_on()) ? "Turn music off:" : "Turn music on:"),100, screenHeight*3/4);
        font.draw(batch,((Configuration.getInstance().isSound_on()) ? "Turn sound off:" : "Turn sound on:"),100, screenHeight*2/3);

        //sound
        //batch.draw(sound, screenWidth-sound.getWidth()-20, screenHeight-sound.getHeight()-20);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
        if(Configuration.getInstance().isMusic_on()){
            musicCheckBox.setChecked(true);
        }
        if(Configuration.getInstance().isSound_on()){
            soundCheckBox.setChecked(true);
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
        batch.dispose();
    }
}

