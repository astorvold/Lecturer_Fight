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

    private Stage stage;
    private Texture imageStart;
    private TextureRegion regionStart;
    private TextureRegionDrawable drawableRegionStart;
    private ImageButton buttonStart;


    private Texture imageSettings;
    private TextureRegion regionSettings;
    private TextureRegionDrawable drawableRegionSettings;
    private ImageButton buttonSettings;


    private Texture imageScore;
    private TextureRegion regionScore;
    private TextureRegionDrawable drawableRegionScore;
    private ImageButton buttonScore;

    private Texture imageTutorial;
    private TextureRegion regionTutorial;
    private TextureRegionDrawable drawableRegionTutorial;
    private ImageButton buttonTutorial;

    private float screenWidth = Gdx.graphics.getWidth();
    private float screenHeight = Gdx.graphics.getHeight();

    private Texture imageCheckboxOff;
    private Texture imageCheckboxOn;

    private TextureRegion regionCheckboxOn;
    private TextureRegion regionCheckboxOff;

    private CheckBox checkBox;
    private boolean multiplayer;




    public MainMenuScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);

        imageStart = new Texture(Gdx.files.internal("start.png"));
        regionStart = new TextureRegion(imageStart);
        drawableRegionStart = new TextureRegionDrawable(regionStart);
        buttonStart = new ImageButton(drawableRegionStart); //Set the button up
        buttonStart.setPosition(screenWidth *0.3f, screenHeight *0.5f);
        buttonStart.setSize(screenWidth *0.5f, screenHeight*0.5f );
        //buttonStart.setSize(200,200);

        imageSettings = new Texture(Gdx.files.internal("settings.png"));
        regionSettings = new TextureRegion(imageSettings);
        drawableRegionSettings = new TextureRegionDrawable(regionSettings);
        buttonSettings = new ImageButton(drawableRegionSettings);
        buttonSettings.setPosition(screenWidth *0.8f, screenHeight *0.9f);
        buttonSettings.setSize(screenWidth *0.1f, screenHeight*0.1f );



        imageScore = new Texture(Gdx.files.internal("score.png"));
        regionScore = new TextureRegion(imageScore);
        drawableRegionScore = new TextureRegionDrawable(regionScore);
        buttonScore = new ImageButton(drawableRegionScore);
        buttonScore.setPosition(screenWidth *0.3f, screenHeight *0.3f);
        buttonScore.setSize(screenWidth *0.5f, screenHeight*0.5f );
        buttonScore.setBounds(screenWidth*0.3f,screenHeight*0.3f,screenWidth*0.3f,screenHeight*0.3f);


        imageTutorial = new Texture(Gdx.files.internal("tutorial.png"));
        regionTutorial = new TextureRegion(imageTutorial);
        drawableRegionTutorial = new TextureRegionDrawable(regionTutorial);
        buttonTutorial = new ImageButton(drawableRegionTutorial);
        buttonTutorial.setPosition(screenWidth *0.3f, screenHeight *0.1f);
        buttonTutorial.setSize(screenWidth *0.5f, screenHeight*0.5f );
        buttonTutorial.setBounds(screenWidth*0.3f,screenHeight*0.1f,screenWidth*0.3f,screenHeight*0.3f);


        Table table = new Table();
        table.setBounds(screenHeight*0.2f,screenHeight*0.5f,screenWidth*0.3f,screenHeight*0.3f);

        imageCheckboxOn = new Texture(Gdx.files.internal("checkboxOn.png"));
        imageCheckboxOff = new Texture(Gdx.files.internal("checkboxOff.png"));
        regionCheckboxOff = new TextureRegion(imageCheckboxOff);
        regionCheckboxOn = new TextureRegion(imageCheckboxOn);
        CheckBox.CheckBoxStyle checkboxStyle = new CheckBox.CheckBoxStyle();
        checkboxStyle.font = font;

        checkboxStyle.checkboxOff = new TextureRegionDrawable(regionCheckboxOff);
        checkboxStyle.checkboxOn = new TextureRegionDrawable(regionCheckboxOn);

        checkBox = new CheckBox(" ", checkboxStyle);
        checkBox.setPosition(screenWidth *0.5f, screenHeight *0.3f);
        checkBox.setSize(screenWidth *0.5f, screenHeight*0.5f );
        table.add(checkBox);

        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(buttonStart); //Add the button to the stage to perform rendering and take input.
        stage.addActor(buttonSettings);
        stage.addActor(buttonScore);
        stage.addActor(buttonTutorial);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage); //Start taking input from the ui

    }


    @Override
    public void show() {
        buttonStart.addListener(new ClickListener()
        {
            public boolean handle(Event event) {
                if(event.toString() == "touchDown"){
                    System.out.println(("clickeo start"));

                    game.setScreen(new GameScreen(game,multiplayer));
                    return true;
                }
                return false;
            }
        });
        buttonSettings.addListener(new ClickListener()
        {
            public boolean handle(Event event) {
                if(event.toString() == "touchDown"){
                    //System.out.println(("clickeo settings"));
                    game.setScreen(new SettingsScreen(game));
                    return true;
                }
                return false;
            }
        });
        buttonScore.addListener(new ClickListener()
        {
            public boolean handle(Event event) {
                if(event.toString() == "touchDown"){
                    //System.out.println(("clickeo score"));
                    game.setScreen(new HighScoreScreen(game,true,false));
                    return true;
                }
                return false;
            }
        });
        buttonTutorial.addListener(new ClickListener()
        {
            public boolean handle(Event event) {
                if(event.toString() == "touchDown"){
                    //System.out.println(("clickeo tutorial"));
                    game.setScreen(new TutorialScreen(game));
                    return true;
                }
                return false;
            }
        });
        checkBox.addListener(new ClickListener()
        {
            public boolean handle(Event event) {
                if(event.toString() == "touchDown"){
                    System.out.println(("clickeo multiplayer"));
                    boolean isChecked = checkBox.isChecked();
                    multiplayer = !isChecked;

                    return true;

                }
                return false;
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

    }

    @Override
    public void dispose() {
        camera = null;
    }


}
