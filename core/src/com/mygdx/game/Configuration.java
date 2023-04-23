package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

import jdk.nashorn.internal.runtime.regexp.joni.Config;

public class Configuration {
    private static Music music = Gdx.audio.newMusic(Gdx.files.internal("Dumb Ways To Die.mp3"));
    private static Music point = Gdx.audio.newMusic(Gdx.files.internal("point.mp3"));
    private static Music die = Gdx.audio.newMusic(Gdx.files.internal("die.mp3"));
    private static boolean music_on = true, sound_on = true;
    private static String textureString;
    private static Texture playerTexture;
    private static String playerName = "anonymous" + (int)Math.ceil(Math.random()*1000000);
    private static Configuration configuration = new Configuration();
    private Configuration(){}
    public static Configuration getInstance(){
        return configuration;
    }
    public void setPlayerTexture(String path){
        if(playerTexture != null) {
            playerTexture.dispose();
            playerTexture = null;
        }
        textureString = path;
        playerTexture = new Texture(path);
    }
    public String getTexturePath(){
        return textureString;
    }
    public Texture getPlayerTexture(){
        return playerTexture;
    }
    public Music getMusic(){
        return music;
    }
    public Music getPoint(){
        return point;
    }
    public Music getDie(){
        return die;
    }
    public boolean isMusic_on(){
        return music_on;
    }
    public void playMusic(){
        music.setLooping(true);
        music.play();
        music_on = true;
    }

    public void changeMusic(){
        if(music_on){
            music.pause();
            music_on = false;
        }
        else{
            music.play();
            music_on = true;
        }
    }
    public boolean isSound_on(){
        return sound_on;
    }
    public void changeSound(){
        sound_on = !sound_on;
    }
    public void pointMusic(){
        if (sound_on){
            point.play();
        }
    }
    public void dieMusic(){
        if(sound_on){
            die.play();
        }
    }
    public void setName(String name){
        this.playerName = name;
    }
    public String getName(){
        return this.playerName;
    }

}
