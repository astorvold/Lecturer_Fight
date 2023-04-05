package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

public class SettingsScreen implements Screen {
    final Lecturer_fight game;
    OrthographicCamera camera;
    Music music = Gdx.audio.newMusic(Gdx.files.internal("Dumb Ways To Die.mp3"));
    Music point = Gdx.audio.newMusic(Gdx.files.internal("point.mp3"));
    Music die = Gdx.audio.newMusic(Gdx.files.internal("die.mp3"));
    Texture backButton, backgroundImage, toggleONButton, toggleOFFButton;


    private final float screenHeight = Gdx.graphics.getHeight();

    private final float screenWidth = Gdx.graphics.getWidth();
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();
    boolean music_on = true, sound_on = true;
    Preferences prefs = Gdx.app.getPreferences("Lecturer_Fight");


    public SettingsScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);
        font.getData().setScale(10,10);

        //sound = new Texture(Gdx.files.internal("sound-icon.png"));
        //noSound = new Texture(Gdx.files.internal("sound-off-icon.png"));

        //create Textures
        backButton = new Texture(Gdx.files.internal("new_images/ARROW_LEFT.png"));
        backgroundImage = new Texture(Gdx.files.internal("new_images/LIGHT_BG.png"));
        toggleONButton = new Texture(Gdx.files.internal("new_images/TOGGLE_ON.png"));
        toggleOFFButton = new Texture(Gdx.files.internal("new_images/TOGGLE_OFF.png"));

        //handle on/off logic and be able to use it elsewhere
        /*
        prefs.putInteger("Music",1);
        prefs.putInteger("Sound",1);
        prefs.flush();*/


        //edit font
        font.setColor(new Color(0x023D8Bff));
        font.getData().setScale(6,6);

        //changing the sound settings on the android device
        //AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC);

        //music_on = true;
        music.setVolume(0.5f);
        if (music_on){
            playMusic();
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //ScreenUtils.clear(0, 0, 0.2f, 1);
        batch.begin();
        //page items
        batch.draw(backgroundImage,0,0,screenWidth,backgroundImage.getHeight()*screenWidth/backgroundImage.getWidth());
        batch.draw(backButton, -50, screenHeight-150,300,backButton.getHeight()*300/backButton.getWidth());

        //texts
        font.draw(batch,"Settings",screenWidth*1/3+20, screenHeight-50);
        font.draw(batch,((music_on) ? "Turn music off:" : "Turn music on:"),100, screenHeight*3/4);
        font.draw(batch,((sound_on) ? "Turn sound off:" : "Turn sound on:"),100, screenHeight*2/3);

        //toggle-buttons
        batch.draw(((prefs.getInteger("Music") == 1) ? toggleONButton : toggleOFFButton),screenWidth*2/3,screenHeight*3/4-110,350,toggleONButton.getHeight()*350/toggleONButton.getWidth());
        batch.draw(((prefs.getInteger("Sound") == 1) ? toggleONButton : toggleOFFButton),screenWidth*2/3,screenHeight*2/3-110,350,toggleONButton.getHeight()*350/toggleONButton.getWidth());

        //sound
        //batch.draw(sound, screenWidth-sound.getWidth()-20, screenHeight-sound.getHeight()-20);
        batch.end();


        if (Gdx.input.isTouched()) {
            //Return to main-menu
            if (Gdx.input.getX() < 150 && Gdx.input.getY() < 150) {
                dispose();
                game.setScreen(new MainMenuScreen(game));
                stopMusic();
            }

            //turn music on/off
            if((Gdx.input.getX()<970 && Gdx.input.getX()>810) && (Gdx.input.getY()<550 && Gdx.input.getY()>470)){
                int current_state = prefs.getInteger("Music");
                prefs.putInteger("Music", ((current_state == 1) ? 0 : 1));
                if(current_state == 1){
                    //mekk å skru av music
                    //music.pause(); kanskje?
                    stopMusic();
                }else{
                    //mekk å skru på music
                    playMusic();
                }
            }
            //turn sound on/off
            else if((Gdx.input.getX()<970 && Gdx.input.getX()>810) && (Gdx.input.getY()<700 && Gdx.input.getY()>620)){
                int current_state = prefs.getInteger("Sound");
                prefs.putInteger("Sound", ((current_state == 1) ? 0 : 1));
                // trenger kanskje ikke å gjøre noe under her siden play-koden kan sjekke om prefs("sound")==1 og så lager lyd
                if(current_state == 1){
                    //mekk å skru av sound
                    sound_on = false;
                }else{
                    //mekk å skru på sound
                    sound_on = true;

                }
            }
            prefs.flush();


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

    public boolean isMusic_on(){
        return this.music_on;
    }

    public boolean isSound_on(){
        return this.sound_on;
    }

    public void playMusic(){
            music.setLooping(true);
            music.play();
            music_on = true;
    }

    public void startMusic(){
        music.play();
    }

    public void stopMusic(){
        if(music_on){
            music.pause();
            music_on = false;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        music.dispose();

    }
}

