package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class GameController {

    public static final int OBSTACLES_PER_SCREEN = 8;
    public static final int COINS_PER_SCREEN = 3;
    private final int speed = 3;
    public Player player;
    public Player player2;
    public ArrayList<Entity> obstacles;
    public ArrayList<Entity> coins;
    private int highestObstacle;
    public GameState state;
    private final boolean multiplayer;
    private final Lecturer_fight game;
    private final float screenHeight = Gdx.graphics.getHeight();
    private final float screenWidth = Gdx.graphics.getWidth();

    public GameController(final Lecturer_fight game, final boolean multiplayer){
        this.game = game;
        initializeObstacles();
        initializeCoins();
        highestObstacle = 7;
        this.multiplayer = multiplayer;
        player = new Player(Configuration.getInstance().getPlayerTexture(), screenWidth/2, screenHeight/2, 96,96);
        if (Configuration.getInstance().isMusic_on()){
            Configuration.getInstance().playMusic();
        }
        if(!multiplayer){
            state = GameState.RUNNING_SINGLEPLAYER;
        }
        else {
            player.setReady(true);
            Texture player2Texture = new Texture("opponent_avatar.png");
            player2 = new Player(player2Texture, 500, screenHeight/3, 96,96);
            if(!player2.isReady()) {
                state = GameState.WAITING;
            }
            else {
                state = GameState.RUNNING_MULTIPLAYER;
            }
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
    public void checkCollisions() {
        //Checks if any obstacle is at the same position that the player
        for(int i = 0; i < OBSTACLES_PER_SCREEN; i++) {
            obstacles.get(i).changePos(-speed);
            if (player.checkColisions(obstacles.get(i))) {
                //plays die sound if turned on in settings
                Configuration.getInstance().dieMusic();
                // send score to DB and set player as non-ready
                game.api.setScore(player.getScore());
                player.setReady(false);
                game.api.setInfoPlayer(player);
                if(!multiplayer){
                    game.setScreen(new HighScoreScreen(this.game,true,true,player.getScore(), 0, null));
                }else{
                    if(player2.isAlive()){
                        game.setScreen(new HighScoreScreen(this.game,true,true,player.getScore(), 999999, player2));
                    }
                    else{
                        game.setScreen(new HighScoreScreen(this.game,true,true,player.getScore(), player2.getScore(),player2 ));
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
            if (player.checkColisions(coins.get(i))) {
                player.increaseScore(100);
                Configuration.getInstance().pointMusic(); //play point sound if it is turned on in settings
                coins.get(i).disappear();
            }
            //If the coin is getting out the bounds it will be put again.
            //It will be put on top of the highest obstacle
            if(coins.get(i).getY()<0){
                float x = generateRandomNumber(100, Gdx.graphics.getWidth()-100);
                float y = obstacles.get(highestObstacle).getY() + obstacles.get(highestObstacle).getHeight() +70;
                if(coins.get(i).getTexture() == null){
                    coins.get(i).setTexture(new Texture("new_images/COIN.png"));
                }
                coins.get(i).setX(x);
                coins.get(i).setY(y);
            }
        }
    }

    public boolean getMultiplayer(){return multiplayer;}
    public GameState getState(){return state;}
    public int getSpeed(){return speed;}
    public Player getPlayer1(){return player;}
    public Player getPlayer2(){return player2;}
    public ArrayList<Entity> getObstacles(){return obstacles;}
    public ArrayList<Entity> getCoins(){return coins;}
    public void setGameState(GameState state){this.state = state;}

    public void playerController(){
        if(Gdx.input.getX() >= screenWidth/2) {
            if (getPlayer1().getX() < screenWidth - getPlayer1().getWidth())
                getPlayer1().changePos(10);
            else getPlayer1().changePos(-10);
        }
        else {
            if (getPlayer1().getX() > 0) getPlayer1().changePos(-10);
            else getPlayer1().changePos(10);
        }
    }
}
