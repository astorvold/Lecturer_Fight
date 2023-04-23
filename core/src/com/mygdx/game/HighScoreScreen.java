package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Collections;

public class HighScoreScreen implements Screen {

    final Lecturer_fight game;
    OrthographicCamera camera;
    ArrayList<Score> scoreList;
    boolean backButton;
    boolean playAgainButton;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont font2;
    private BitmapFont font3;
    private BitmapFont font4;

    private Stage stage;

    private Texture imageStart;
    private Texture imageName;
    private Texture imagePlayAgain;
    private Texture backgroundImage;
    private Texture textBoxImage;
    private Image buttonPlayAgain, buttonStart, buttonName;

    private SettingsScreen settings;

    private int screenHeight = Gdx.graphics.getHeight();
    private int screenWidth = Gdx.graphics.getWidth();

    private int playerScore;
    private int opponentScore;

    public HighScoreScreen(final Lecturer_fight game, boolean backButton, boolean playAgainButton, int myScore, int opponentScore) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false,800,400);
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font2 = new BitmapFont();
        this.font3 = new BitmapFont();
        this.font4 = new BitmapFont();
        this.backButton = backButton;
        this.playAgainButton = playAgainButton;
        //this.settings = new SettingsScreen(game);
        this.settings = SettingsScreen.getInstance(game);

        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui

        imageStart = new Texture(Gdx.files.internal("new_images/ARROW_LEFT.png"));
        backgroundImage = new Texture(Gdx.files.internal("new_images/LIGHT_BG.png"));
        textBoxImage = new Texture(Gdx.files.internal("new_images/box.png"));
        buttonStart = ButtonFactory.createButton(imageStart,-0.1f*imageStart.getWidth(), screenHeight *0.87f,imageStart.getWidth()/2,imageStart.getHeight()/2);
        imageName= new Texture(Gdx.files.internal("new_images/name3.png"));
        buttonName = ButtonFactory.createButton(imageName,screenWidth*0.35f, screenHeight *0.89f,imageStart.getWidth()/3,imageStart.getHeight()/3);
        stage.addActor(buttonName);
        stage.addActor(buttonStart);
        if (playAgainButton){
            imagePlayAgain= new Texture(Gdx.files.internal("new_images/PLAY_AGAIN.png"));
            buttonPlayAgain = ButtonFactory.createButton(imagePlayAgain,screenWidth*0.45f - 1f*imagePlayAgain.getWidth()/5, screenHeight *0.05f,imagePlayAgain.getWidth()/2,imagePlayAgain.getHeight()/2);
            stage.addActor(buttonPlayAgain);
        }
        Gdx.input.setInputProcessor(stage);

        //play music
        if (Configuration.getInstance().isMusic_on()){
            Configuration.getInstance().playMusic();
        }
        if (myScore != 0){
            this.playerScore = myScore;
        }
        if (opponentScore != 0){
            this.opponentScore = opponentScore;
        }

    }

    @Override
    public void show() {

        scoreList = new ArrayList<>();
        game.api.getScores(scoreList);    // retrieves the scores from the DB and saves them in scoreList

        buttonStart.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainMenuScreen(game));
                System.out.println("Back Button");
            }
        });
        buttonName.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                AddNameListener listener = new AddNameListener();
                Gdx.input.getTextInput(listener, "Enter your name", "", "");

            }
        });
        if (playAgainButton){
            buttonPlayAgain.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y){
                    game.setScreen(new GameScreen(game,false));
                    System.out.println("Play again");
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
        font.getData().setScale(4);
        font.setColor(Color.BLACK);
        font2.getData().setScale(5);
        font2.setColor(Color.BLACK);
        font3.getData().setScale(8);
        font3.setColor(Color.BLACK);
        font4.getData().setScale(10);
        font4.setColor(Color.GREEN);

        Collections.sort(scoreList,new SortScore());

        batch.begin();
        batch.draw(backgroundImage,0,0,screenWidth,backgroundImage.getHeight()*screenWidth/backgroundImage.getWidth());

        int flag = 0;
        font3.draw(batch, "High score", screenWidth *0.24f, screenHeight *0.85f);
        for(Score score: scoreList){
            font2.draw(batch, score.toString(), screenWidth *0.20f, screenHeight *0.75f);

            screenHeight = screenHeight - 200;
            flag++;
            if (flag == 5 ){
                if (playerScore != 0){
                    font.draw(batch, "Your score was: " + playerScore, screenWidth *0.01f, screenHeight *0.6f);
                }
                if (this.opponentScore != 0){
                    if (this.opponentScore == 999999){
                        font.draw(batch, "Opponent is still playing ", screenWidth *0.01f, screenHeight *0.6f-150);
                    }
                    else{
                        font.draw(batch, "Opponent's score was: " + opponentScore, screenWidth *0.01f, screenHeight *0.6f-150);

                    }
                    if (playerScore > opponentScore){
                        font4.draw(batch, "Win!", screenWidth *0.57f, screenHeight *0.65f);
                    }else{
                        font4.setColor(Color.RED);
                        font4.draw(batch, "Lose!", screenWidth *0.57f, screenHeight *0.65f);

                    }

                }
                break;
            }
        }
        screenHeight = Gdx.graphics.getHeight();
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
        stage.dispose();

    }

    @Override
    public void dispose() {

    }
}
