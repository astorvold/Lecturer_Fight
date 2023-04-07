package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class HighScoreScreen implements Screen {

    final Lecturer_fight game;
    OrthographicCamera camera;
    ArrayList<Score> scoreList;
    boolean backButton;
    boolean playAgainButton;
    private SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont font2;

    private Stage stage;

    private Texture imageStart;
    private ImageButton buttonStart;
    private Texture imagePlayAgain;
    private ImageButton buttonPlayAgain;

    private SettingsScreen settings;


    private int screenHeight = Gdx.graphics.getHeight();
    private int screenWidth = Gdx.graphics.getWidth();

    public HighScoreScreen(final Lecturer_fight game, boolean backButton, boolean playAgainButton) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false,800,400);
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font2 = new BitmapFont();
        this.backButton = backButton;
        this.playAgainButton = playAgainButton;
        this.settings = new SettingsScreen(game);

        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui

        imageStart = new Texture(Gdx.files.internal("back.png"));
        buttonStart = new ImageButton(new TextureRegionDrawable(new TextureRegion(imageStart))); //Set the button up
        buttonStart.setBounds(screenWidth/5 - 1f*imageStart.getWidth()/2, screenHeight *0.9f,imageStart.getWidth(),imageStart.getHeight());

        stage.addActor(buttonStart);

        if (playAgainButton){
            imagePlayAgain= new Texture(Gdx.files.internal("start.png"));
            buttonPlayAgain = new ImageButton(new TextureRegionDrawable(new TextureRegion(imagePlayAgain))); //Set the button up
            buttonPlayAgain.setBounds(screenWidth/5 - 1f*imagePlayAgain.getWidth()/2, screenHeight *0.4f,imagePlayAgain.getWidth(),imagePlayAgain.getHeight());

            stage.addActor(buttonPlayAgain);
        }
        Gdx.input.setInputProcessor(stage);

        //play music
        if (settings.isMusic_on()){
            settings.playMusic();
        }

    }

    @Override
    public void show() {
        scoreList = new ArrayList<Score>();
        game.api.getScores(scoreList);    // retrieves the scores from the DB and saves them in scoreList

        buttonStart.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainMenuScreen(game));
                System.out.println("Back Button");
                settings.stopMusic();
            }
        });
        if (playAgainButton){
            buttonPlayAgain.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y){
                    game.setScreen(new GameScreen(game,false));
                    System.out.println("Back Button");
                    settings.stopMusic();
                }
            });
        }

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        font.getData().setScale(5);
        font.setColor(Color.WHITE);
        font2.getData().setScale(5);
        font2.setColor(Color.WHITE);

        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui

        batch.begin();
        for(Score score: scoreList){
            font2.draw(batch, score.toString(), screenWidth /2, screenHeight /2);
            screenHeight = screenHeight + 500;
        }
        screenHeight = Gdx.graphics.getHeight();
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
        stage.dispose();

    }

    @Override
    public void dispose() {

    }
}
