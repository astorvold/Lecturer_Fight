package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public abstract class Configuration {

    private static Music music = Gdx.audio.newMusic(Gdx.files.internal("Dumb Ways To Die.mp3"));
    private static Music point = Gdx.audio.newMusic(Gdx.files.internal("point.mp3"));
    private static Music die = Gdx.audio.newMusic(Gdx.files.internal("die.mp3"));
    private static boolean music_on = true, sound_on = true;

    public static Music getMusic(){
        return music;
    }
    public static Music getPoint(){
        return point;
    }
    public static Music getDie(){
        return die;
    }
    public static boolean isMusic_on(){
        return music_on;
    }
    public static void playMusic(){
        music.setLooping(true);
        music.play();
        music_on = true;
    }

    public static  void changeMusic(){
        if(music_on){
            music.pause();
            music_on = false;
        }
        else{
            music.play();
            music_on = true;
        }
    }
    public static boolean isSound_on(){
        return sound_on;
    }
    public static void changeSound(){
        sound_on = !sound_on;
    }
    public static void pointMusic(){
        if (sound_on){
            point.play();
        }
    }
    public static void dieMusic(){
        if(sound_on){
            die.play();
        }
    }

}
