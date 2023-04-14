package com.mygdx.game;

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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;



import java.util.Arrays;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

public class TutorialScreen implements Screen {
    final Lecturer_fight game;
    OrthographicCamera camera;
    Texture backButton, forwardButton, backgroundImage, tutorialImage,tutorialTxt;
    private ImageButton buttonbackup, buttonleft, buttonright;
    private Stage stage;
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


        backgroundImage = new Texture(Gdx.files.internal("new_images/LIGHT_BG.png"));
        tutorialImage = new Texture(Gdx.files.internal(image_list.get(prefs.getInteger("Tutorial_CurrentImage"))));
        tutorialTxt = new Texture(Gdx.files.internal("new_images/TutorialTxt.png"));
        initializeButtons();

        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(buttonbackup); //Add the button to the stage to perform rendering and take input.
        stage.addActor(buttonleft);
        stage.addActor(buttonright);

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
        buttonbackup = ButtonFactory.createButton(backup, (screenWidth / 35f)-75f, screenHeight * 0.67f, 0.5f * screenWidth, 0.5f * screenHeight);

        left = new Texture("new_images/ARROW_LEFT.png");
        buttonleft = ButtonFactory.createButton(left, screenWidth / 30f, screenHeight*0.01f, 0.35f * screenWidth, 0.5f * screenHeight);

        right = new Texture("new_images/ARROW_RIGHT.png");
        buttonright = ButtonFactory.createButton(right, screenWidth / 1.7f, screenHeight * 0.01f, 0.35f * screenWidth, 0.5f * screenHeight);
    }

        @Override
    public void show() {

            buttonbackup.addListener(new ClickListener(){
                public void clicked(InputEvent event, float x, float y){
                    game.setScreen(new MainMenuScreen(game));
                    System.out.println("Back Button from avatarScreen");
                }
            });

    }

    @Override
    public void render(float delta) {
        batch.begin();






        //page items
        batch.draw(backgroundImage,0,0,screenWidth,backgroundImage.getHeight()*screenWidth/backgroundImage.getWidth());


        //text
        //font.draw(batch,"Tutorial",screenWidth*1/3+20, screenHeight-50);
        batch.draw(tutorialTxt,screenWidth/3, screenHeight*0.85f, screenWidth/2, screenHeight/12);
        //tutorial-image showing, currently a placeholder
        batch.draw(tutorialImage,screenWidth/6,screenHeight/4,screenWidth*2/3,screenHeight*7/12);

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

