package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

public class TutorialScreen implements Screen {
    final Lecturer_fight game;
    OrthographicCamera camera;
    Texture backButton, forwardButton, backgroundImage, tutorialImage;

    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();
    Preferences prefs = Gdx.app.getPreferences("Lecturer_Fight");
    private List<String> image_list = Arrays.asList(new String[]{"new_images/LIGHT_BG.png"});

    private final int screenHeight = Gdx.graphics.getHeight();
    private final int screenWidth = Gdx.graphics.getWidth();

    public TutorialScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);

        //create Textures
        backButton = new Texture(Gdx.files.internal("new_images/ARROW_LEFT.png"));
        forwardButton = new Texture(Gdx.files.internal("new_images/ARROW_RIGHT.png"));
        backgroundImage = new Texture(Gdx.files.internal("new_images/LIGHT_BG.png"));
        tutorialImage = new Texture(Gdx.files.internal(image_list.get(prefs.getInteger("Tutorial_CurrentImage"))));

        //edit font
        font.setColor(new Color(0x023D8Bff));
        font.getData().setScale(6,6);

        //re-start tutorial image logic at 0 every time we visit this page
        prefs.putInteger("Tutorial_CurrentImage",0);
        prefs.flush();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();

        //page items
        batch.draw(backgroundImage,0,0,screenWidth,backgroundImage.getHeight()*screenWidth/backgroundImage.getWidth());
        batch.draw(backButton, -50, screenHeight-150,300,backButton.getHeight()*300/backButton.getWidth());

        //text
        font.draw(batch,"Tutorial",screenWidth*1/3+20, screenHeight-50);

        //tutorial-image showing, currently a placeholder
        batch.draw(tutorialImage,screenWidth/6,screenHeight/4,screenWidth*2/3,screenHeight*7/12);

        //forward and backward arrows
        batch.draw(forwardButton, screenWidth-500, 150,500,backButton.getHeight()*500/backButton.getWidth());
        batch.draw(backButton, 0, 150,500,backButton.getHeight()*500/backButton.getWidth());

        batch.end();

        if (Gdx.input.isTouched()) {
            //Return to main-menu
            if(Gdx.input.getX()<150 && Gdx.input.getY()<150){
                dispose();
                game.setScreen(new MainMenuScreen(game));
            }

            //Switch back and forth between tutorial-images
            int current_img = prefs.getInteger("Tutorial_CurrentImage");
            if((Gdx.input.getX()<350 && Gdx.input.getX()>150) && (Gdx.input.getY()<1700 && Gdx.input.getY()>1500)){
                if(current_img > 0) {
                    prefs.putInteger("Tutorial_CurrentImage", current_img-1);
                }
            }else if((Gdx.input.getX()<925 && Gdx.input.getX()>725) && (Gdx.input.getY()<1700 && Gdx.input.getY()>1500)){
                if(current_img < image_list.size()-1) {
                    prefs.putInteger("Tutorial_CurrentImage", current_img+1);
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

    @Override
    public void dispose() {

    }
}

