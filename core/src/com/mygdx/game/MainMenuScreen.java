package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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


public class MainMenuScreen implements Screen {

    final Lecturer_fight game;
    OrthographicCamera camera;
    BitmapFont font = new BitmapFont();
    // all the buttons

    private final Stage stage;
    private Texture imageStart, imageSettings, imageScore, imageTutorial, imageCheckboxOff, imageCheckboxOn;
    private final ImageButton buttonStart, buttonSettings, buttonScore, buttonTutorial;

    private TextureRegion regionCheckboxOn;
    private TextureRegion regionCheckboxOff;

    private final CheckBox checkBox;
    private boolean multiplayer;

    public MainMenuScreen(final Lecturer_fight game) {

        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);

        imageSettings = new Texture(Gdx.files.internal("settings.png"));
        buttonSettings = new ImageButton( new TextureRegionDrawable( new TextureRegion( imageSettings)));
        buttonSettings.setBounds(buttonSettings.getX(),buttonSettings.getY(),buttonSettings.getWidth(),buttonSettings.getHeight());
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        buttonSettings.setBounds(screenWidth /2 - 0.7f*imageSettings.getWidth()/2, screenHeight *0.9f,imageSettings.getWidth()*0.7f,imageSettings.getHeight()*0.7f);

        imageStart = new Texture(Gdx.files.internal("start.png"));
        buttonStart = new ImageButton(new TextureRegionDrawable(new TextureRegion(imageStart))); //Set the button up
        buttonStart.setBounds(screenWidth /2 - 1f*imageStart.getWidth()/2, screenHeight *0.7f,imageStart.getWidth(),imageStart.getHeight());

        imageScore = new Texture(Gdx.files.internal("score.png"));
        buttonScore = new ImageButton(new TextureRegionDrawable( new TextureRegion(imageScore)));
        buttonScore.setBounds(screenWidth /2 - 0.7f*imageScore.getWidth()/2, screenHeight *0.3f,imageScore.getWidth()*0.7f,imageScore.getHeight()*0.7f);


        imageTutorial = new Texture(Gdx.files.internal("tutorial.png"));
        buttonTutorial = new ImageButton(new TextureRegionDrawable(new TextureRegion(imageTutorial)));
        buttonTutorial.setBounds(screenWidth /2 - 0.7f* imageTutorial.getWidth()/2, screenHeight *0.1f, imageTutorial.getWidth()*0.7f, imageTutorial.getHeight()*0.7f);


        imageCheckboxOn = new Texture(Gdx.files.internal("checkboxOn.png"));
        imageCheckboxOff = new Texture(Gdx.files.internal("checkboxOff.png"));
        regionCheckboxOff = new TextureRegion(imageCheckboxOff);
        regionCheckboxOn = new TextureRegion(imageCheckboxOn);
        CheckBox.CheckBoxStyle checkboxStyle = new CheckBox.CheckBoxStyle();
        font.setColor(Color.WHITE);
        font.getData().setScale(10);
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
       buttonStart.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new GameScreen(game, multiplayer));
                System.out.println("Start Button");
            }
        });
        buttonSettings.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new SettingsScreen(game));
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
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

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
        stage.dispose();
    }

    @Override
    public void dispose() {
        camera = null;
        stage.dispose();

    }


}
