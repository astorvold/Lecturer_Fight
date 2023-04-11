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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


import org.w3c.dom.Text;

public class MainMenuScreen implements Screen {

    //Distance between buttons
    private final int distance = 250;

    Texture avatarButton, titleImage,backgroundImage;

    Preferences prefs = Gdx.app.getPreferences("Lecturer-Fight");
    private final float screenHeight = Gdx.graphics.getHeight();
    private final float screenWidth = Gdx.graphics.getWidth();

    private final float buttonWidth = screenWidth/2;
    private float buttonHeight;
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();

    private SettingsScreen settings;



    final Lecturer_fight game;
    OrthographicCamera camera;



    // all the buttons

    private final Stage stage;
    private Texture imageStart;
    private final ImageButton buttonStart;
    private Texture imageSettings;
    private final ImageButton buttonSettings;
    private Texture imageScore;
    private final ImageButton buttonScore;
    private Texture imageTutorial;
    private final ImageButton buttonTutorial;

    private Texture imageCheckboxOff;
    private Texture imageCheckboxOn;

    private TextureRegion regionCheckboxOn;
    private TextureRegion regionCheckboxOff;

    private final CheckBox checkBox;
    private boolean multiplayer;


    public MainMenuScreen(final Lecturer_fight game) {

        this.game = game;
        this.camera = new OrthographicCamera();
        this.settings = new SettingsScreen(game);

        camera.setToOrtho(false,800,400);

        prefs.putString("Avatar", "alfinge_avatar.png");
        prefs.flush();


        avatarButton = new Texture(Gdx.files.internal(prefs.getString("Avatar")));
        titleImage = new Texture(Gdx.files.internal("new_images/TITLE.png"));
        backgroundImage = new Texture(Gdx.files.internal("new_images/BG.png"));


        font.getData().setScale(5,5);
        font.setColor(new Color(0x023D8Bff));

        prefs.putInteger("AvatarHeight", 522);
        prefs.putInteger("AvatarWidth", avatarButton.getWidth()*prefs.getInteger("AvatarHeight")/avatarButton.getHeight());
        prefs.putBoolean("Multiplayer", true);
        prefs.flush();


        imageSettings = new Texture(Gdx.files.internal("new_images/SETTINGS.png"));
        buttonSettings = new ImageButton( new TextureRegionDrawable( new TextureRegion( imageSettings)));
        buttonSettings.setBounds(buttonSettings.getX(),buttonSettings.getY(),buttonSettings.getWidth(),buttonSettings.getHeight());
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        buttonSettings.setBounds(screenWidth /2 - 0.7f*imageSettings.getWidth()/2, screenHeight *0.9f,imageSettings.getWidth()*0.7f,imageSettings.getHeight()*0.7f);

        imageStart = new Texture(Gdx.files.internal("new_images/PLAY.png"));
        buttonStart = new ImageButton(new TextureRegionDrawable(new TextureRegion(imageStart))); //Set the button up
        buttonStart.setBounds(screenWidth /2 - 1f*imageStart.getWidth()/2, screenHeight *0.7f,imageStart.getWidth(),imageStart.getHeight());

        imageScore = new Texture(Gdx.files.internal("new_images/HIGHSCORE.png"));
        buttonScore = new ImageButton(new TextureRegionDrawable( new TextureRegion(imageScore)));
        buttonScore.setBounds(screenWidth /2 - 0.7f*imageScore.getWidth()/2, screenHeight *0.3f,imageScore.getWidth()*0.7f,imageScore.getHeight()*0.7f);


        imageTutorial = new Texture(Gdx.files.internal("new_images/TUTORIAL.png"));
        buttonTutorial = new ImageButton(new TextureRegionDrawable(new TextureRegion(imageTutorial)));
        buttonTutorial.setBounds(screenWidth /2 - 0.7f* imageTutorial.getWidth()/2, screenHeight *0.1f, imageTutorial.getWidth()*0.7f, imageTutorial.getHeight()*0.7f);


        imageCheckboxOn = new Texture(Gdx.files.internal("new_images/TOGGLE_ON.png"));
        imageCheckboxOff = new Texture(Gdx.files.internal("new_images/TOGGLE_OFF.png"));
        regionCheckboxOff = new TextureRegion(imageCheckboxOff);
        regionCheckboxOn = new TextureRegion(imageCheckboxOn);
        CheckBox.CheckBoxStyle checkboxStyle = new CheckBox.CheckBoxStyle();
        font.setColor(Color.WHITE);
        font.getData().setScale(7);
        checkboxStyle.font = font;

        checkboxStyle.checkboxOff = new TextureRegionDrawable(regionCheckboxOff);
        checkboxStyle.checkboxOn = new TextureRegionDrawable(regionCheckboxOn);

        checkBox = new CheckBox("Multiplayer? ", checkboxStyle);
        checkBox.setBounds(screenWidth /2 - 1f*imageCheckboxOn.getWidth()/2, screenHeight *0.5f,imageCheckboxOn.getWidth(),imageCheckboxOn.getHeight());

        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(buttonStart); //Add the button to the stage to perform rendering and take input.
        stage.addActor(buttonSettings);
        stage.addActor(buttonScore);
        stage.addActor(buttonTutorial);
        stage.addActor(checkBox);
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui


    }


    @Override  // Add listeners to check when user clicks on buttons
    public void show() {

        //play music
        if (settings.isMusic_on()){
            settings.playMusic();
        }
       buttonStart.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new GameScreen(game, multiplayer));
                System.out.println("Start Button");
            }
        });
        buttonSettings.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                //game.setScreen(new SettingsScreen(game));
                game.setScreen(settings);
                System.out.println("Settings Button");
            }
        });
        buttonScore.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new HighScoreScreen(game,true,false));
                System.out.println("Score Button");
            }
        });
        buttonTutorial.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new TutorialScreen(game));
                System.out.println("Tutorial Button");
            }
        });
        checkBox.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                    System.out.println(("clickeo multiplayer"));
                    boolean isChecked = checkBox.isChecked();
                    multiplayer = isChecked;
                    System.out.println(multiplayer);
                }
        });
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


        //other buttons
        batch.draw(avatarButton, (screenWidth-prefs.getInteger("AvatarWidth"))/2, playButtonY+(float)avatarButton.getWidth()/2, prefs.getInteger("AvatarWidth"), prefs.getInteger("AvatarHeight"));

        batch.end();




        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        settings.pause();

    }

    @Override
    public void resume() {
        settings.resume();

    }

    @Override
    public void hide() {
        stage.dispose();
        settings.stopMusic();
    }

    @Override
    public void dispose() {
        camera = null;
        stage.dispose();
    }


}
