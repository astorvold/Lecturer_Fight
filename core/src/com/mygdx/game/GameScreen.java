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
import com.badlogic.gdx.utils.TimeUtils;

import org.w3c.dom.Text;

public class GameScreen implements Screen{
    public static final int OBSTACLES_PER_SCREEN = 8;
    public static final int COINS_PER_SCREEN = 3;
    private int backgroundPos = 0;
    private Texture background;
    private final int speed = 3;
    private final Lecturer_fight game;
    private final float screenHeight = Gdx.graphics.getHeight();
    private final float screenWidth = Gdx.graphics.getWidth();
    private OrthographicCamera camera;
    private SpriteBatch batch = new SpriteBatch();
    private BitmapFont font = new BitmapFont();

    private SettingsScreen settings;

    public Player player;
    public Player player2;
    public ArrayList<Entity> obstacles;
    public ArrayList<Coin> coins;
    private int highestObstacle;

    long startTime;

    private boolean multiplayer;



    public GameScreen(final Lecturer_fight game,boolean multiplayer) {
        this.game = game;
        System.out.println("game screen");
        // create the camera and the SpriteBatch
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        //this.settings = new SettingsScreen(game);
        this.settings = SettingsScreen.getInstance(game);
        //Initializing objects
        background = new Texture("new_images/BG.png");
        player = new Player("alfinge_avatar.png", screenWidth/2, screenHeight/2, 96,96);
        initializeObstacles();
        initializeCoins();
        highestObstacle = 7;
        this.multiplayer = multiplayer;

        //play music
        if (Configuration.isMusic_on()){
            Configuration.playMusic();
        }
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
            obstacles.add(new Obstacle("obstacle.png", aux, screenHeight + screenHeight * (float)(i*1.5)/OBSTACLES_PER_SCREEN, 256, 96));
        }
    }
    private void initializeCoins(){
        coins = new ArrayList<>();
        for(int i = 0; i < COINS_PER_SCREEN; i++){
            int pos = i*OBSTACLES_PER_SCREEN/COINS_PER_SCREEN;
            float x = generateRandomNumber(100, (int)screenWidth-100);
            float y = generateRandomNumber((int)(obstacles.get(pos).getY() + obstacles.get(pos).getHeight()) +70 , (int) obstacles.get(pos).getY()+(64+70));
            coins.add(new Coin("coin.png", x, y, 64,64));
        }
    }
    public void movementControl(){

        if (Gdx.input.isTouched()) {
            if(Gdx.input.getX() >= screenWidth/2)
                if (player.getX() < screenWidth -player.getWidth()) player.changePos(10);
                else player.changePos(-10);
            else
                if (player.getX() > 0) player.changePos(-10);
                else player.changePos(10);
        }
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

        // checks coordinates and isReady of players
        if(multiplayer){
            game.api.getInfoRival(player2);
            game.api.setInfoPlayer(player);
        }

        // starts creating obstacles
        if(multiplayer == false || (multiplayer && player2.isReady())){
            batch.begin();
            batch.draw(background,0, backgroundPos, screenWidth,background.getHeight()*screenWidth/background.getWidth());
            batch.end();
            createEntities();
            movementControl();
            checkCollisions();
            if(-backgroundPos >= background.getHeight()) backgroundPos = -3000;
            else backgroundPos -= speed;
        }
        else{
            batch.begin();
            font.getData().setScale(6);
            font.setColor(Color.BLACK);
            font.draw(batch, "Waiting for you opponent!!", Gdx.graphics.getWidth()*0.1f, Gdx.graphics.getHeight()/2);
            batch.end();
        }

    }
    private void createEntities(){
        // begin a new batch and draw the bucket and all drops
        batch.begin();
        batch.draw(player.getTexture(), player.getX(), player.getY(), player.getWidth(), player.getHeight());
        for(int i = 0; i < OBSTACLES_PER_SCREEN; i++)
            batch.draw(obstacles.get(i).getTexture(), obstacles.get(i).getX(), obstacles.get(i).getY(), obstacles.get(i).getWidth(), obstacles.get(i).getHeight());
        for(int i = 0; i < COINS_PER_SCREEN; i++)
            if(coins.get(i).getTexture() != null)
                batch.draw(coins.get(i).getTexture(), coins.get(i).getX(), coins.get(i).getY(), coins.get(i).getWidth(), coins.get(i).getHeight());
        font.getData().setScale(3);
        font.setColor(Color.BLACK);

        // send position to DB
        game.api.setInfoPlayer(player);

        // player gets 1 point every second
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        if (elapsedTime > 1000){
            startTime = 0;
            player.increaseScore(1);
        }
        if (multiplayer == true){
            //my game gets ready to multiplayer
            showRivalScore();
        }
        batch.end();
    }

    private void showRivalScore(){
        batch.draw(player2.getTexture(), screenWidth*0.8f, screenHeight*0.95f, player2.getWidth(), player2.getHeight());
        font.draw(batch, "Player 1: " + player.getScore() + " - Player2: " + player2.getScore(), screenWidth/3, screenHeight*0.97f);
        game.api.getInfoRival(player2);
    }

    /**
     * CheckCollisions Method
     */
    public void checkCollisions() {
        //Checks if any obstacle is at the same position that the player
        for(int i = 0; i < OBSTACLES_PER_SCREEN; i++) {
            obstacles.get(i).changePos(-speed);
            if (player.checkColisions(obstacles.get(i))) {

                //plays die sound if turned on in settings
                Configuration.dieMusic();

                // send score to DB and set player as non-ready
                game.api.setScore(player.getScore());
                System.out.println("Score sent to db -> " + player.getScore() + " point");
                player.setReady(false);
                game.api.setInfoPlayer(player);
                game.setScreen(new HighScoreScreen(this.game,true,true));

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
            if (player.checkColisions(coins.get(i))) {
                player.increaseScore(100);
                Configuration.pointMusic(); //play point sound if it is turned on in settings
                coins.get(i).disappear();
            }
            //If the coin is getting out the bounds it will be put again.
            //It will be put on top of the highest obstacle
            if(coins.get(i).getY()<0){
                float x = generateRandomNumber(100, (int)screenWidth-100);
                float y = obstacles.get(highestObstacle).getY() + obstacles.get(highestObstacle).getHeight() +70;
                if(coins.get(i).getTexture() == null){
                    coins.get(i).setTexture(new Texture("coin.png"));
                }
                coins.get(i).setX(x);
                coins.get(i).setY(y);
            }
        }
    }

    private int getRelativePosition(int pos){
        int i = 0;
        while(i <= OBSTACLES_PER_SCREEN/2){
            if(pos == OBSTACLES_PER_SCREEN-1) pos = 1;
            else pos ++;
            i++;
        }
        return pos;
    }

    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown

        startTime = TimeUtils.millis();

        if (multiplayer == true){
            player.setReady(true);
            player2 = new Player("bird2.png", 500, screenHeight/3, 96,96);

        }
    }
    @Override
    public void hide() {
        player.setReady(false);

    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() {
    }
    @Override
    public void dispose() {
        player.getTexture().dispose();
        for(int i = 0; i < OBSTACLES_PER_SCREEN; i++) obstacles.get(i).getTexture().dispose();
        for(int i = 0; i < COINS_PER_SCREEN; i++) coins.get(i).getTexture().dispose();
    }
}
