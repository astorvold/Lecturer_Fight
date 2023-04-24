package com.mygdx.game.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Controller.ButtonFactory;
import com.mygdx.game.Lecturer_fight;


import java.util.ArrayList;

public class TutorialScreen implements Screen {
    final Lecturer_fight game;
    OrthographicCamera camera;
    Texture backgroundImage, tutorialTxt;
    private Image buttonBackMenu, buttonLeft, buttonRight;
    private final Stage stage;
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();
    Preferences prefs = Gdx.app.getPreferences("Lecturer_Fight");
    private final ArrayList<Texture> image_list = new ArrayList<>();
    private int current_image = 0;
    private final int screenHeight = Gdx.graphics.getHeight();
    private final int screenWidth = Gdx.graphics.getWidth();

    public TutorialScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);

        //create Textures
        //tutorial1al1 = new Texture(Gdx.files.internal("new_images/1.png"));

        backgroundImage = new Texture(Gdx.files.internal("new_images/LIGHT_BG.png"));
        for(int i = 1; i <= 4; i++){
            image_list.add(new Texture("new_images/"+i+".png"));
        }
        tutorialTxt = new Texture(Gdx.files.internal("new_images/TutorialTxt.png"));
        initializeButtons();

        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(buttonBackMenu); //Add the button to the stage to perform rendering and take input.
        stage.addActor(buttonLeft);
        stage.addActor(buttonRight);

        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
        //edit font
        font.setColor(new Color(0x023D8Bff));
        font.getData().setScale(6,6);

        //re-start tutorial image logic at 0 every time we visit this page
        prefs.putInteger("Tutorial_CurrentImage",0);
        prefs.flush();

    }

    private void initializeButtons() {
        Texture backup, left, right;

        backup = new Texture("new_images/ARROW_LEFT.png");
        buttonBackMenu = ButtonFactory.createButton(backup, 0.1f*screenWidth -0.35f*screenWidth/2, screenHeight *0.9f,0.35f*screenWidth,0.08f*screenHeight);

        left = new Texture("new_images/ARROW_LEFT.png");
        buttonLeft = ButtonFactory.createButton(left, 0.2f*screenWidth -0.35f*screenWidth/2, screenHeight *0.15f,0.35f*screenWidth,0.08f*screenHeight);

        right = new Texture("new_images/ARROW_RIGHT.png");
        buttonRight = ButtonFactory.createButton(right, 0.8f*screenWidth -0.35f*screenWidth/2, screenHeight *0.15f,0.35f*screenWidth,0.08f*screenHeight);
    }

        @Override
    public void show() {
        buttonBackMenu.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainMenuScreen(game));
            }
        });

        buttonLeft.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(current_image>0) current_image--;
            }
        });
        buttonRight.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                if(current_image<3) current_image++;
            }
        });
    }

    @Override
    public void render(float delta) {
        batch.begin();
        //page items
        batch.draw(backgroundImage,0,0,screenWidth,(float)backgroundImage.getHeight()*screenWidth/backgroundImage.getWidth());
        //text
        //font.draw(batch,"Tutorial",screenWidth*1/3+20, screenHeight-50);
        batch.draw(tutorialTxt,screenWidth/3f, screenHeight*0.85f, screenWidth/2f, screenHeight/12f);
        //tutorial-image showing, currently a placeholder
        batch.draw(image_list.get(current_image),screenWidth/6f,screenHeight/4f,screenWidth*2/3f,screenHeight*7/12f);
        //forward and backward arrows

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

    }

    @Override
    public void dispose() {
    }
}

