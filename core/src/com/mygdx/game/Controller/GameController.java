package com.mygdx.game.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Lecturer_fight;
import com.mygdx.game.Model.Coin;
import com.mygdx.game.Model.Entity;
import com.mygdx.game.Model.Obstacle;
import com.mygdx.game.Utils.OpponentCharacter;
import com.mygdx.game.Utils.PlayerCharacter;
import com.mygdx.game.View.HighScoreScreen;

import java.util.ArrayList;

public class GameController {

    public static final int OBSTACLES_PER_SCREEN = 8;
    public static final int COINS_PER_SCREEN = 3;
    private int speed = 3;
    public ArrayList<Entity> obstacles;
    public ArrayList<Entity> coins;
    private int highestObstacle;
    public GameState state;
    private final boolean multiplayer;
    private final Lecturer_fight game;
    private final float screenHeight = Gdx.graphics.getHeight();
    private final float screenWidth = Gdx.graphics.getWidth();
    private final PlayerController playerController;
    private final PlayerCharacter playerCharacter;
    private OpponentCharacter opponentCharacter;


    public GameController(final Lecturer_fight game, final boolean multiplayer){
        this.game = game;
        initializeObstacles();
        initializeCoins();
        highestObstacle = 7;
        this.multiplayer = multiplayer;
        playerCharacter = new PlayerCharacter(Configuration.getInstance().getPlayerTexture(), screenWidth/2, screenHeight/2, 96,96);
        playerController = playerCharacter.getPlayerController();
        if (Configuration.getInstance().isMusic_on()){
            Configuration.getInstance().playMusic();
        }
        if(!multiplayer){
            state = GameState.RUNNING_SINGLEPLAYER;
        }
        else {
            playerController.setReady(true);
            Texture player2Texture = new Texture("opponent_avatar.png");
            opponentCharacter = new OpponentCharacter(player2Texture, 500, screenHeight/3, 96,96);
            if(!opponentCharacter.getPlayerModel().isReady()) {
                state = GameState.WAITING;
            }
            else {
                state = GameState.RUNNING_MULTIPLAYER;
                game.api.isBusy(playerCharacter.getPlayerModel());
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
            if (playerController.checkColisions(obstacles.get(i))) {
                //plays die sound if turned on in settings
                Configuration.getInstance().dieMusic();
                // send score to DB and set player as non-ready
                game.api.setScore(playerCharacter.getPlayerModel().getScore());
                playerController.setReady(false);
                game.api.setInfoPlayer(playerCharacter.getPlayerModel());
                if(!multiplayer){
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
            if (playerController.checkColisions(coins.get(i))) {
                playerController.increaseScore(100);
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
    public PlayerCharacter getPlayer1(){return playerCharacter;}
    public OpponentCharacter getPlayer2(){return opponentCharacter;}
    public ArrayList<Entity> getObstacles(){return obstacles;}
    public ArrayList<Entity> getCoins(){return coins;}
    public void setGameState(GameState state){this.state = state;}
    public PlayerController getPlayerController(){
        return playerController;
    }
    public void increaseSpeed(){this.speed += 1;}
}
