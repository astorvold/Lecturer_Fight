package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

    final Lecturer_fight game;
    OrthographicCamera camera;
    Texture startButton;
    Texture settingsButton;
    Texture scoreButton;
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();;

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

    float viewportWidth = Gdx.graphics.getWidth();
    float viewportHeight = Gdx.graphics.getHeight();



    public MainMenuScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);

        imageStart = new Texture(Gdx.files.internal("start.png"));
        regionStart = new TextureRegion(imageStart);
        drawableRegionStart = new TextureRegionDrawable(regionStart);
        buttonStart = new ImageButton(drawableRegionStart); //Set the button up
        buttonStart.setPosition(viewportWidth*0.3f,viewportHeight*0.8f);
        //buttonStart.setSize(viewportWidth*0.7f, viewportHeight*0.7f );
        buttonStart.setSize(200,200);

        imageSettings = new Texture(Gdx.files.internal("settings.png"));
        regionSettings = new TextureRegion(imageSettings);
        drawableRegionSettings = new TextureRegionDrawable(regionSettings);
        buttonSettings = new ImageButton(drawableRegionSettings);
        buttonSettings.setPosition(viewportWidth*0.4f,viewportHeight*0.5f);



        imageScore = new Texture(Gdx.files.internal("score.png"));
        regionScore = new TextureRegion(imageScore);
        drawableRegionScore = new TextureRegionDrawable(regionScore);
        buttonScore = new ImageButton(drawableRegionScore);
        buttonScore.setPosition(viewportWidth*0.4f,viewportHeight*0.2f);
        buttonScore.setSize(200,200);




        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(buttonStart); //Add the button to the stage to perform rendering and take input.
        stage.addActor(buttonSettings);
        stage.addActor(buttonScore);
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
    }


    @Override
    public void show() {

        buttonStart.addListener(new ClickListener()
        {
            public boolean handle(Event event) {
                if(event.toString() == "touchDown"){
                    System.out.println(("clickeo start"));
                }
                return false;
            }
        });
        buttonSettings.addListener(new ClickListener()
        {
            public boolean handle(Event event) {
                if(event.toString() == "touchDown"){
                    System.out.println(("clickeo settings"));
                }
                return false;
            }
        });
        buttonScore.addListener(new ClickListener()
        {
            public boolean handle(Event event) {
                if(event.toString() == "touchDown"){
                    System.out.println(("clickeo score"));
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

        batch.begin();
        font.draw(batch, "Welcome to Lecturer fight!!! ", 200, 380);
        font.draw(batch, "Click on start to begin!", 200, 370);
        batch.end();


        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui

        //&& Gdx.graphics.getHeight()
        //when startButton is touched -> go to gameScreen
        if (Gdx.input.isTouched()) {
            //System.out.println(Gdx.input.getX() + " "+ Gdx.input.getY());
            //if(Gdx.input.getX() > 0 && Gdx.input.getX() < 300 && Gdx.input.getY() > 0 && Gdx.input.getY() < 300){
            //    dispose();
            //    game.setScreen(new GameScreen(game));
            //}


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
