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
    private Preferences prefs = Gdx.app.getPreferences("Lecturer-Fight");
    private final float screenHeight = Gdx.graphics.getHeight();
    private final float screenWidth = Gdx.graphics.getWidth();
    private SpriteBatch batch = new SpriteBatch();
    private BitmapFont font = new BitmapFont();
    private SettingsScreen settings;
    private final Lecturer_fight game;
    private OrthographicCamera camera;
    private final Stage stage;
    Texture imageStart, imageSettings, imageScore, imageTutorial, imageCheckboxOff, imageCheckboxOn, imageAvatar, titleImage,backgroundImage;
    private ImageButton buttonStart, buttonSettings, buttonScore, buttonTutorial, buttonAvatar;
    private TextureRegion regionCheckboxOn, regionCheckboxOff;
    private CheckBox checkBox;
    private boolean multiplayer;


    public MainMenuScreen(final Lecturer_fight game) {

        this.game = game;
        this.camera = new OrthographicCamera();
        this.settings = new SettingsScreen(game);
        camera.setToOrtho(false,800,400);

        //prefs.putString("Avatar", "alfinge_avatar.png");
        //prefs.flush();


        //imageAvatar = new Texture(Gdx.files.internal(prefs.getString("Avatar")));
        titleImage = new Texture(Gdx.files.internal("new_images/TITLE.png"));
        backgroundImage = new Texture(Gdx.files.internal("new_images/BG.png"));


        font.getData().setScale(5,5);
        font.setColor(new Color(0x023D8Bff));

        /*prefs.putInteger("AvatarHeight", 522);
        prefs.putInteger("AvatarWidth", imageAvatar.getWidth()*prefs.getInteger("AvatarHeight")/imageAvatar.getHeight());*/
        prefs.putBoolean("Multiplayer", true);
        prefs.flush();
        //play music
        System.out.println("musikk er på: " + settings.isMusic_on());
        if (settings.isMusic_on()){
            settings.playMusic();
        }

        initializeButtons();
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(buttonStart); //Add the button to the stage to perform rendering and take input.
        stage.addActor(buttonSettings);
        stage.addActor(buttonAvatar);
        stage.addActor(buttonScore);
        stage.addActor(buttonTutorial);
        stage.addActor(checkBox);
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
    }

    private void initializeButtons(){
        imageSettings = new Texture(Gdx.files.internal("new_images/SETTINGS.png"));
        buttonSettings = new ImageButton( new TextureRegionDrawable( new TextureRegion( imageSettings)));
        buttonSettings.setBounds(screenWidth*0.1f - 0.5f*imageSettings.getWidth()/2, screenHeight *0.9f,imageSettings.getWidth()*0.5f,imageSettings.getHeight()*0.5f);

        imageAvatar = new Texture(Gdx.files.internal("alfinge_avatar.png"));
        buttonAvatar = new ImageButton(new TextureRegionDrawable(new TextureRegion(imageAvatar)));
        buttonAvatar.setBounds(screenWidth*0.9f - 0.5f*imageAvatar.getWidth()/2, screenHeight *0.9f,imageAvatar.getWidth()*0.5f,imageAvatar.getHeight()*0.5f);

        imageStart = new Texture(Gdx.files.internal("new_images/PLAY.png"));
        buttonStart = new ImageButton(new TextureRegionDrawable(new TextureRegion(imageStart))); //Set the button up
        buttonStart.setBounds(screenWidth /2 - 0.7f*imageStart.getWidth()/2, screenHeight *0.6f,0.7f*imageStart.getWidth(),0.7f*imageStart.getHeight());

        imageScore = new Texture(Gdx.files.internal("new_images/HIGHSCORE.png"));
        buttonScore = new ImageButton(new TextureRegionDrawable( new TextureRegion(imageScore)));
        buttonScore.setBounds(screenWidth /2 - 0.55f*imageScore.getWidth()/2, screenHeight *0.3f,imageScore.getWidth()*0.55f,imageScore.getHeight()*0.55f);


        imageTutorial = new Texture(Gdx.files.internal("new_images/TUTORIAL.png"));
        buttonTutorial = new ImageButton(new TextureRegionDrawable(new TextureRegion(imageTutorial)));
        buttonTutorial.setBounds(screenWidth /2 - 0.55f* imageTutorial.getWidth()/2, screenHeight *0.15f, imageTutorial.getWidth()*0.55f, imageTutorial.getHeight()*0.55f);

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
    }
    @Override  // Add listeners to check when user clicks on buttons
    public void show() {
       buttonStart.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new GameScreen(game, multiplayer));
                System.out.println("Start Button");
                settings.stopMusic();
            }
        });
        buttonSettings.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new SettingsScreen(game));
                System.out.println("Settings Button");
                settings.stopMusic();
            }
        });

        buttonAvatar.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new AvatarScreen(game));
                System.out.println("Avatar Button");
                settings.stopMusic();
            }
        });

        buttonScore.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new HighScoreScreen(game,true,false));
                System.out.println("Score Button");
                settings.stopMusic();
            }
        });
        buttonTutorial.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new TutorialScreen(game));
                System.out.println("Tutorial Button");
                settings.stopMusic();
            }
        });
        checkBox.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                    System.out.println(("clickeo multiplayer"));
                    boolean isChecked = checkBox.isChecked();
                    multiplayer = isChecked;
                    System.out.println(multiplayer);
                    settings.stopMusic();
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
        batch.draw(titleImage,screenWidth/2 - titleImage.getWidth()/2, 0.73f*screenHeight, titleImage.getWidth(), titleImage.getHeight());

        //text
        font.draw(batch, "Made by Group 16", 0.45f*screenWidth/2, 150);


        //other buttons
        //batch.draw(avatarButton, (screenWidth-prefs.getInteger("AvatarWidth"))/2, playButtonY+(float)avatarButton.getWidth()/2, prefs.getInteger("AvatarWidth"), prefs.getInteger("AvatarHeight"));

        batch.end();




        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui

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
        batch.dispose();
        stage.dispose();
    }

    @Override
    public void dispose() {
        camera = null;
        stage.dispose();
        batch.dispose();

    }


}
