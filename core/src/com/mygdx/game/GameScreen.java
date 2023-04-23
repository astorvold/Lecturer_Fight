package com.mygdx.game;

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

public class GameScreen implements Screen{
    public static final int OBSTACLES_PER_SCREEN = 8;
    public static final int COINS_PER_SCREEN = 3;
    public GameState state;
    private int backgroundPos = 0;
    private Texture background;
    private Texture player2Texture;
    private final int speed = 3;
    private final Lecturer_fight game;
    private final float screenHeight = Gdx.graphics.getHeight();
    private final float screenWidth = Gdx.graphics.getWidth();
    private OrthographicCamera camera;
    private SpriteBatch batch = new SpriteBatch();
    private BitmapFont font = new BitmapFont();


    private Image buttonPause, buttonResume, buttonQuit;

    public ArrayList<Entity> obstacles;
    public ArrayList<Coin> coins;
    private int highestObstacle;
    long startTime;
    private boolean multiplayer;
    private Stage stage;
    private Stage pausedStage;
    private int player2Score = 0;
    private int player2Score2 = -1;


    //// nuevo
    private PlayerCharacter playerCharacter;
    private OpponentCharacter opponentCharacter;


    public GameScreen(final Lecturer_fight game,boolean multiplayer) {
        this.game = game;
        // create the camera and the SpriteBatch
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        //Initializing objects
        initializeObstacles();
        initializeCoins();
        initializeButtons();
        highestObstacle = 7;
        this.multiplayer = multiplayer;
        playerCharacter = new PlayerCharacter(Configuration.getInstance().getPlayerTexture(), screenWidth/2, screenHeight/2, 96,96);

        player2Texture = new Texture("opponent_avatar.png");


        //play music
        if (Configuration.getInstance().isMusic_on()){
            Configuration.getInstance().playMusic();
        }
        if(multiplayer == false){
            state = GameState.RUNNING_SINGLEPLAYER;
        }
        else {
            playerCharacter.getPlayerController().setReady(true);
            opponentCharacter = new OpponentCharacter(player2Texture, 500, screenHeight/3, 96,96);

            if(!opponentCharacter.getPlayerModel().isReady()) {
                state = GameState.WAITING;
            }
            else {
                state = GameState.RUNNING_MULTIPLAYER;
            }
        }
    }
    private void initializeButtons(){
        Texture imagePause;
        if(!multiplayer) {
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

    private float generateRandomNumber(int from, int to){
        return (float)Math.floor(Math.random() * (to - from + 1) + from);
    }
    private void initializeObstacles(){
        obstacles = new ArrayList<>();
        for(int i = 1; i <= OBSTACLES_PER_SCREEN; i++) {
            float aux = screenWidth;
            int random_int = (int)Math.floor(Math.random() * (1 + 1) + 0);
            if(random_int == 0) aux = aux * random_int;
            obstacles.add(new Obstacle(new Texture("obstacles/BUILDING1.png"), aux, screenHeight + screenHeight * (float)(i*1.5)/OBSTACLES_PER_SCREEN, screenWidth*0.3f, screenHeight*0.05f));
        }
    }
    private void initializeCoins(){
        coins = new ArrayList<>();
        for(int i = 0; i < COINS_PER_SCREEN; i++){
            int pos = i*OBSTACLES_PER_SCREEN/COINS_PER_SCREEN;
            float x = generateRandomNumber(100, (int)screenWidth-100);
            float y = generateRandomNumber((int)(obstacles.get(pos).getY() + obstacles.get(pos).getHeight()) +70 , (int) obstacles.get(pos).getY()+(64+70));
            coins.add(new Coin(new Texture("new_images/COIN.png"), x, y, 64,64));
        }
    }
    public void movementControl(){
        if (Gdx.input.isTouched()) {
            if((Gdx.input.getX() >= buttonPause.getX() && Gdx.input.getX() <= buttonPause.getX() + buttonPause.getWidth() ) &&
                    ( screenHeight- Gdx.input.getY() >= buttonPause.getY() && screenHeight - Gdx.input.getY() <= buttonPause.getY() + buttonPause.getHeight()) ) {
                if(state == GameState.RUNNING_SINGLEPLAYER) state = GameState.PAUSED;
                else if(state == GameState.RUNNING_MULTIPLAYER) game.setScreen(new MainMenuScreen(game));
            }
            if(Gdx.input.getX() >= screenWidth/2)

                if (playerCharacter.getPlayerModel().getX() < screenWidth - playerCharacter.getPlayerModel().getWidth()) playerCharacter.getPlayerController().changePos(10);
                else playerCharacter.getPlayerController().changePos(-10);
            else
                if (playerCharacter.getPlayerModel().getX() > 0) playerCharacter.getPlayerController().changePos(-10);
                else playerCharacter.getPlayerController().changePos(10);
        }
    }
    private void running(){
        batch.begin();
        batch.draw(background,0, backgroundPos, screenWidth,background.getHeight()*screenWidth/background.getWidth());
        batch.end();
        createEntities();
        movementControl();
        checkCollisions();
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
        if(-backgroundPos >= background.getHeight()) backgroundPos = -3000;
        else backgroundPos -= speed;

    }
    private void waiting(){
        batch.begin();
        font.getData().setScale(6);
        font.setColor(Color.BLACK);
        font.draw(batch, "Waiting for your opponent!!", Gdx.graphics.getWidth()*0.02f, Gdx.graphics.getHeight()/2);
        batch.end();
        if(opponentCharacter.getPlayerModel().isReady()){
            state = GameState.RUNNING_MULTIPLAYER;
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

        switch (state){
            case RUNNING_SINGLEPLAYER:
                running();
                break;
            case RUNNING_MULTIPLAYER:
                game.api.getInfoRival(opponentCharacter.getPlayerModel());
                game.api.setInfoPlayer(playerCharacter.getPlayerModel());
                running();
                break;
            case WAITING:
                game.api.setInfoPlayer(playerCharacter.getPlayerModel());
                game.api.getInfoRival(opponentCharacter.getPlayerModel());
                waiting();
                break;
            case PAUSED:
                pause();
                break;
        }
    }
    private void createEntities(){


        // begin a new batch and draw the bucket and all drops
        batch.begin();

        playerCharacter.getPlayerView().draw(batch);

        for(int i = 0; i < OBSTACLES_PER_SCREEN; i++)
            batch.draw(obstacles.get(i).getTexture(), obstacles.get(i).getX(), obstacles.get(i).getY(), obstacles.get(i).getWidth(), obstacles.get(i).getHeight());
        for(int i = 0; i < COINS_PER_SCREEN; i++)
            if(coins.get(i).getTexture() != null)
                batch.draw(coins.get(i).getTexture(), coins.get(i).getX(), coins.get(i).getY(), coins.get(i).getWidth(), coins.get(i).getHeight());
        font.getData().setScale(3);
        font.setColor(Color.BLACK);
        // send position to DB
        game.api.setInfoPlayer(playerCharacter.getPlayerModel());
        // player gets 1 point every second
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        if(multiplayer == true){
            player2Score = opponentCharacter.getPlayerModel().getScore();
            if (elapsedTime > 1000){
                if (player2Score == player2Score2){
                    player2Texture = new Texture("opponent_avatar_dead.png");
                    opponentCharacter.getPlayerModel().setTexture(player2Texture);
                    opponentCharacter.getPlayerModel().isDead();
                }
                player2Score2 = opponentCharacter.getPlayerModel().getScore();
                player2Score = opponentCharacter.getPlayerModel().getScore();
                startTime = TimeUtils.millis();
        }
        }

        playerCharacter.getPlayerController().increaseScore(10);
        if (multiplayer == true){
            //my game gets ready to multiplayer
            showRivalScore();
        }
        else{
            showMyScore();
        }
        batch.end();


    }
    private void showMyScore(){
        font.draw(batch, "Score: " + playerCharacter.getPlayerModel().getScore(), screenWidth*0.45f, screenHeight*0.97f);
    }
    private void showRivalScore(){
        if ( opponentCharacter.getPlayerModel().isAlive()){
            batch.draw(opponentCharacter.getPlayerModel().getTexture(), playerCharacter.getPlayerModel().getX(), playerCharacter.getPlayerModel().getY()-100, opponentCharacter.getPlayerModel().getWidth(), opponentCharacter.getPlayerModel().getHeight());
        }
        else{
            batch.draw(opponentCharacter.getPlayerModel().getTexture(), screenWidth*0.5f, screenHeight*0.1f, opponentCharacter.getPlayerModel().getWidth(), opponentCharacter.getPlayerModel().getHeight());
        }
        font.draw(batch, "Player 1: " + playerCharacter.getPlayerModel().getScore() + " - Player2: " + opponentCharacter.getPlayerModel().getScore(), screenWidth/3, screenHeight*0.97f);
        game.api.getInfoRival(opponentCharacter.getPlayerModel());
    }
    public void checkCollisions() {
        //Checks if any obstacle is at the same position that the player
        for(int i = 0; i < OBSTACLES_PER_SCREEN; i++) {
            obstacles.get(i).changePos(-speed);
            if (playerCharacter.getPlayerModel().checkColisions(obstacles.get(i))) {

                //plays die sound if turned on in settings
                Configuration.getInstance().dieMusic();

                // send score to DB and set player as non-ready
                game.api.setScore(playerCharacter.getPlayerModel().getScore());
                playerCharacter.getPlayerController().setReady(false);
                game.api.setInfoPlayer(playerCharacter.getPlayerModel());
                if(multiplayer == false){
                    game.setScreen(new HighScoreScreen(this.game,true,true,playerCharacter.getPlayerModel().getScore(), 0, null));

                }else{
                    if(opponentCharacter.getPlayerModel().isAlive()){
                        game.setScreen(new HighScoreScreen(this.game,true,true,playerCharacter.getPlayerModel().getScore(), 999999, opponentCharacter.getPlayerModel()));
                    }
                    else{
                        game.setScreen(new HighScoreScreen(this.game,true,true,playerCharacter.getPlayerModel().getScore(), opponentCharacter.getPlayerModel().getScore(),opponentCharacter.getPlayerModel()));
                    }

                }

            }
            //If the obstacle is getting out the bounds it will be put again
            if(obstacles.get(i).getY()<0){
                obstacles.get(i).setY(screenHeight*(float)1.5);
                highestObstacle=i;
            }
        }
        //Checks if any coin is at the same position that the player
        for(int i = 0; i < COINS_PER_SCREEN; i++){
            coins.get(i).changePos(-speed);
            if (playerCharacter.getPlayerModel().checkColisions(coins.get(i))) {
                playerCharacter.getPlayerController().increaseScore(100);
                Configuration.getInstance().pointMusic(); //play point sound if it is turned on in settings
                coins.get(i).disappear();
            }
            //If the coin is getting out the bounds it will be put again.
            //It will be put on top of the highest obstacle
            if(coins.get(i).getY()<0){
                float x = generateRandomNumber(100, (int)screenWidth-100);
                float y = obstacles.get(highestObstacle).getY() + obstacles.get(highestObstacle).getHeight() +70;
                if(coins.get(i).getTexture() == null){
                    coins.get(i).setTexture(new Texture("new_images/COIN.png"));
                }
                coins.get(i).setX(x);
                coins.get(i).setY(y);
            }
        }
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
                if(state == GameState.RUNNING_SINGLEPLAYER) state = GameState.PAUSED;
                else game.setScreen(new MainMenuScreen(game));
            }
        });
        buttonQuit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(state == GameState.PAUSED) game.setScreen(new MainMenuScreen(game));
            }
        });
        buttonResume.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if(state == GameState.PAUSED) state = GameState.RUNNING_SINGLEPLAYER;
            }
        });
    }
    @Override
    public void hide() {
        playerCharacter.getPlayerController().setReady(false);
        game.api.removePlayer(playerCharacter.getPlayerModel());

    }
    @Override
    public void resume() {
    }
    @Override
    public void dispose() {
        playerCharacter.getPlayerModel().getTexture().dispose();
        stage.dispose();
        pausedStage.dispose();
        batch.dispose();
        for(int i = 0; i < OBSTACLES_PER_SCREEN; i++) obstacles.get(i).getTexture().dispose();
        for(int i = 0; i < COINS_PER_SCREEN; i++) coins.get(i).getTexture().dispose();
    }
}
