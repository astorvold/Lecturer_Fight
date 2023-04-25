package com.mygdx.game.View;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Controller.ButtonFactory;
import com.mygdx.game.Model.Entity;
import com.mygdx.game.Controller.GameState;
import com.mygdx.game.Lecturer_fight;
import com.mygdx.game.Controller.GameController;

public class GameScreen implements Screen{

    private int backgroundPos = 0;
    private Texture background;
    private final Lecturer_fight game;
    private final float screenHeight = Gdx.graphics.getHeight();
    private final float screenWidth = Gdx.graphics.getWidth();

    private final OrthographicCamera camera;
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont font = new BitmapFont();
    private Image buttonPause, buttonResume, buttonQuit;
    private int player2Score2 = -1;
    long startTime;
    private Stage stage;
    private Stage pausedStage;
    private final GameController gameController;


    public GameScreen(final Lecturer_fight game, boolean multiplayer) {
        this.game = game;
        gameController = new GameController(game, multiplayer);
        // create the camera and the SpriteBatch
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        //Initializing objects
        initializeButtons();
        //play music
    }
    private void initializeButtons(){
        Texture imagePause;
        if(!gameController.getMultiplayer()) {
            imagePause  = new Texture("new_images/PAUSE.png");
        }
        else{
            imagePause = new Texture("new_images/QUIT_BOX.png");
        }
        buttonPause = ButtonFactory.createButton(imagePause, screenWidth-imagePause.getWidth()*0.24f, screenHeight - imagePause.getHeight()*0.5f, imagePause.getWidth()*0.2f, imagePause.getHeight()*0.2f);

        Texture imageResume = new Texture("new_images/RESUME.png");
        buttonResume = ButtonFactory.createButton(imageResume,0.5f*screenWidth - 0.6f*screenWidth/2, screenHeight *0.6f,0.6f*screenWidth,0.12f*screenHeight);

        Texture imageQuit = new Texture("new_images/QUIT.png");
        buttonQuit = ButtonFactory.createButton(imageQuit,0.5f*screenWidth - 0.6f*screenWidth/2, screenHeight *0.4f,0.6f*screenWidth,0.12f*screenHeight);
        background = new Texture("new_images/BG.png");

        stage = new Stage(new ScreenViewport()); //Set up a stage for the
        stage.addActor(buttonPause);
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
        pausedStage = new Stage(new ScreenViewport());
        pausedStage.addActor(buttonResume);
        pausedStage.addActor(buttonQuit);
        Gdx.input.setInputProcessor(pausedStage);

    }
    public void movementControl(){
        if (Gdx.input.isTouched()) {
            if((Gdx.input.getX() >= buttonPause.getX() && Gdx.input.getX() <= buttonPause.getX() + buttonPause.getWidth() ) &&
                    ( screenHeight- Gdx.input.getY() >= buttonPause.getY() && screenHeight - Gdx.input.getY() <= buttonPause.getY() + buttonPause.getHeight()) ) {
                if(gameController.getState() == GameState.RUNNING_SINGLEPLAYER) gameController.setGameState(GameState.PAUSED);
                else if(gameController.getState() == GameState.RUNNING_MULTIPLAYER) game.setScreen(new MainMenuScreen(game));
            }
            gameController.getPlayerController().move();
        }
    }
    private void running(){
        batch.begin();
        batch.draw(background,0, backgroundPos, screenWidth,background.getHeight()*screenWidth/background.getWidth());
        batch.end();
        showMap();
        movementControl();
        gameController.checkCollisions();
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
        if(-backgroundPos >= background.getHeight()) backgroundPos = -3000;
        else backgroundPos -= gameController.getSpeed();

    }
    private void waiting(){
        batch.begin();
        font.getData().setScale(6);
        font.setColor(Color.BLACK);
        font.draw(batch, "Waiting for your opponent!!", Gdx.graphics.getWidth()*0.02f, Gdx.graphics.getHeight()/2f);
        batch.end();
        if(gameController.getPlayer2().getPlayerModel().isReady()){
            gameController.setGameState(GameState.RUNNING_MULTIPLAYER);
        }
    }

    @Override
    public void pause() {
        batch.begin();
        batch.draw(background,0,0,screenWidth,background.getHeight()*screenWidth/background.getWidth());
        batch.end();
        pausedStage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        pausedStage.draw(); //Draw the ui
    }

    @Override
    public void render(float delta) {
        // clear the screen with a white color
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        switch (gameController.getState()){
            case RUNNING_SINGLEPLAYER:
                running();
                break;
            case RUNNING_MULTIPLAYER:
                game.api.getInfoRival(gameController.getPlayer2().getPlayerModel());
                game.api.setInfoPlayer(gameController.getPlayer1().getPlayerModel());
                running();
                break;
            case WAITING:
                game.api.setInfoPlayer(gameController.getPlayer1().getPlayerModel());
                game.api.getInfoRival(gameController.getPlayer2().getPlayerModel());
                waiting();
                break;
            case PAUSED:
                pause();
                break;
        }
    }
    private void showMap(){
        // begin a new batch and draw the bucket and all drops
        batch.begin();
        gameController.getPlayer1().getPlayerView().draw(batch);
        ArrayList<Entity> obstacles = gameController.getObstacles();
        for(int i = 0; i < obstacles.size(); i++)
            batch.draw(obstacles.get(i).getTexture(), obstacles.get(i).getX(), obstacles.get(i).getY(), obstacles.get(i).getWidth(), obstacles.get(i).getHeight());
        ArrayList<Entity> coins = gameController.getCoins();
        for(int i = 0; i < coins.size(); i++)
            if(coins.get(i).getTexture() != null)
                batch.draw(coins.get(i).getTexture(), coins.get(i).getX(), coins.get(i).getY(), coins.get(i).getWidth(), coins.get(i).getHeight());
        font.getData().setScale(3);
        font.setColor(Color.BLACK);
        // send position to DB
        game.api.setInfoPlayer(gameController.getPlayer1().getPlayerModel());
        // player gets 1 point every second
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        if(gameController.getMultiplayer()){
            int player2Score = gameController.getPlayer2().getPlayerModel().getScore();
            if (elapsedTime > 1000){
                if (player2Score == player2Score2){
                    Texture player2Texture = new Texture("opponent_avatar_dead.png");
                    gameController.getPlayer2().getPlayerController().setTexture(player2Texture);
                    gameController.getPlayer2().getPlayerModel().isDead();
                }
                player2Score2 = gameController.getPlayer2().getPlayerModel().getScore();
                //player2Score = gameController.getPlayer2().getScore();
                startTime = TimeUtils.millis();
            }
        }
        gameController.getPlayer1().getPlayerModel().increaseScore(10);
        if (gameController.getMultiplayer()){
            //my game gets ready to multiplayer
            showRivalScore();
        }
        else{
            showMyScore();
        }
        batch.end();
    }
    private void showMyScore(){
        font.draw(batch, "Score: " + gameController.getPlayer1().getPlayerModel().getScore(), screenWidth*0.45f, screenHeight*0.97f);
    }
    private void showRivalScore(){
        if ( gameController.getPlayer2().getPlayerModel().isAlive()){
            gameController.getPlayer2().getPlayerController().setY(gameController.getPlayer1().getPlayerModel().getY()-100);
            gameController.getPlayer2().getPlayerController().setX(gameController.getPlayer1().getPlayerModel().getX());

            gameController.getPlayer2().getPlayerView().draw(batch);
            //batch.draw(gameController.getPlayer2().getTexture(), gameController.getPlayer1().getX(), gameController.getPlayer1().getY()-100, gameController.getPlayer2().getWidth(), gameController.getPlayer2().getHeight());
        }
        else{
            gameController.getPlayer2().getPlayerController().setX(screenWidth*0.5f);
            gameController.getPlayer2().getPlayerController().setY(screenHeight*0.1f);
            gameController.getPlayer2().getPlayerView().draw(batch);
            //batch.draw(gameController.getPlayer2().getTexture(), screenWidth*0.5f, screenHeight*0.1f, gameController.getPlayer2().getWidth(), gameController.getPlayer2().getHeight());
        }
        font.draw(batch, "Player 1: " + gameController.getPlayer1().getPlayerModel().getScore() + " - Player2: " + gameController.getPlayer2().getPlayerModel().getScore(), screenWidth/3, screenHeight*0.97f);
        game.api.getInfoRival(gameController.getPlayer2().getPlayerModel());
    }

    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        startTime = TimeUtils.millis();
        buttonPause.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(gameController.getState() == GameState.RUNNING_SINGLEPLAYER) gameController.setGameState(GameState.PAUSED);
                else game.setScreen(new MainMenuScreen(game));
            }
        });
        buttonQuit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(gameController.getState() == GameState.PAUSED) game.setScreen(new MainMenuScreen(game));
            }
        });
        buttonResume.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(gameController.getState() == GameState.PAUSED) gameController.setGameState(GameState.RUNNING_SINGLEPLAYER);
            }
        });
    }
    @Override
    public void hide() {
        gameController.getPlayer1().getPlayerController().setReady(false);
        game.api.removePlayer(gameController.getPlayer1().getPlayerModel());

    }
    @Override
    public void resume() {
    }
    @Override
    public void dispose() {
        gameController.getPlayer1().getPlayerModel().getTexture().dispose();
        stage.dispose();
        pausedStage.dispose();
        batch.dispose();
        ArrayList<Entity> obstacles = gameController.getObstacles();
        ArrayList<Entity> coins = gameController.getCoins();
        for(int i = 0; i < obstacles.size(); i++) obstacles.get(i).getTexture().dispose();
        for(int i = 0; i < coins.size(); i++) coins.get(i).getTexture().dispose();
    }
}
