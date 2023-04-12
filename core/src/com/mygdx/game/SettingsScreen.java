package com.mygdx.game;

import static com.mygdx.game.Configuration.playMusic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import org.graalvm.compiler.nodes.TypeCheckHints;

public class SettingsScreen implements Screen {
    final Lecturer_fight game;
    private static SettingsScreen instance;
    OrthographicCamera camera;
    Texture imageBack, backgroundImage, imageToggleON, imageToggleOFF;
    private ImageButton buttonBack;
    private CheckBox musicCheckBox, soundCheckBox;

    private Stage stage;
    private final float screenHeight = Gdx.graphics.getHeight();

    private final float screenWidth = Gdx.graphics.getWidth();
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();
    Preferences prefs = Gdx.app.getPreferences("Lecturer_Fight");


    SettingsScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);
        font.getData().setScale(10,10);

        //sound = new Texture(Gdx.files.internal("sound-icon.png"));
        //noSound = new Texture(Gdx.files.internal("sound-off-icon.png"));

        //create Textures
        backgroundImage = new Texture(Gdx.files.internal("new_images/LIGHT_BG.png"));

        //handle on/off logic and be able to use it elsewhere
        /*
        prefs.putInteger("Music",1);
        prefs.putInteger("Sound",1);
        prefs.flush();*/


        //edit font
        font.setColor(new Color(0x023D8Bff));
        font.getData().setScale(6,6);

        //changing the sound settings on the android device
        //AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC);

        //music_on = true;
        Configuration.getMusic().setVolume(0.5f);
        Configuration.getPoint().setVolume(0.5f);
        Configuration.getDie().setVolume(0.5f);
        System.out.println("musikk er på: "+Configuration.isMusic_on() + Configuration.isMusic_on());
        if (Configuration.isMusic_on()){
            Configuration.playMusic();
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
        buttonBack = new ImageButton(new TextureRegionDrawable(new TextureRegion(imageBack)));
        buttonBack.setBounds(screenWidth*0.12f - 0.4f*imageBack.getWidth()/2, screenHeight *0.89f,imageBack.getWidth()*0.4f,imageBack.getHeight()*0.4f);


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
        musicCheckBox.setBounds(screenWidth*2/3,screenHeight*3/4-110,350,imageToggleON.getHeight()*350/imageToggleON.getWidth());
        soundCheckBox.setBounds(screenWidth*2/3,screenHeight*2/3-110,350,imageToggleON.getHeight()*350/imageToggleON.getWidth());
    }

    public static synchronized SettingsScreen getInstance(Lecturer_fight game) {
        if (instance == null) {
            instance = new SettingsScreen(game);
        }
        return instance;
    }

    @Override
    public void show() {
        buttonBack.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainMenuScreen(game));
                System.out.println("Back Button from avatarScreen");
            }
        });
        musicCheckBox.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                System.out.println(("clickeo stopMusic"));
                if(Configuration.isMusic_on()) {
                    Configuration.changeMusic();
                }
                else{
                    Configuration.changeMusic();
                }
            }
        });
        soundCheckBox.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                System.out.println(("clickeo stopSound"));
                Configuration.changeSound();
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
        font.draw(batch,"Settings",screenWidth*1/3+20, screenHeight-50);
        font.draw(batch,((Configuration.isMusic_on()) ? "Turn music off:" : "Turn music on:"),100, screenHeight*3/4);
        font.draw(batch,((Configuration.isSound_on()) ? "Turn sound off:" : "Turn sound on:"),100, screenHeight*2/3);

        //sound
        //batch.draw(sound, screenWidth-sound.getWidth()-20, screenHeight-sound.getHeight()-20);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
        if(Configuration.isMusic_on()){
            musicCheckBox.setChecked(true);
        }
        if(Configuration.isSound_on()){
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

