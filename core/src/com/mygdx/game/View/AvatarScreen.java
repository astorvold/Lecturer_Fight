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
import com.mygdx.game.Controller.Configuration;
import com.mygdx.game.Lecturer_fight;

public class AvatarScreen implements Screen {
    //game saving content
    Preferences prefs = Gdx.app.getPreferences("Lecturer-Fight");
    final Lecturer_fight game;
    OrthographicCamera camera;
    private final int screenHeight = Gdx.graphics.getHeight();
    private final int screenWidth = Gdx.graphics.getWidth();
    private final Stage stage;
    private Image buttonBack, buttonAlfinge, buttonSchau;
    //avatar logic and textures
    Texture backgroundImage, avatarSelectionTxt;
    boolean alfinge_chosen;
    boolean schau_chosen;
    //animation
    SpriteBatch batch = new SpriteBatch();
    BitmapFont font = new BitmapFont();

    public AvatarScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,800,400);
        //create Textures
        backgroundImage = new Texture(Gdx.files.internal("new_images/LIGHT_BG.png"));
        avatarSelectionTxt = new Texture(Gdx.files.internal("new_images/AvatarSelectionTxt.png"));
        initializeButtons();
        //update avatar-logic if it has changed
        check_Avatar();
        //edit font
        font.setColor(new Color(0x023D8Bff));
        font.getData().setScale(6,6);
        stage = new Stage(new ScreenViewport());
        stage.addActor(buttonBack);
        stage.addActor(buttonAlfinge);
        stage.addActor(buttonSchau);
        Gdx.input.setInputProcessor(stage);
    }
    private void initializeButtons(){
        Texture alfIngeAvatar, schauAvatar, imageBack;
        alfIngeAvatar = new Texture(Gdx.files.internal("alfinge_avatar.png"));
        buttonAlfinge = ButtonFactory.createButton(alfIngeAvatar,0.17f*screenWidth, screenHeight *0.55f,0.2f*screenWidth,0.2f*screenHeight);


        schauAvatar = new Texture(Gdx.files.internal("schau_avatar.png"));
        buttonSchau = ButtonFactory.createButton(schauAvatar,0.62f*screenWidth, screenHeight *0.55f,0.2f*screenWidth,0.2f*screenHeight);

        imageBack = new Texture(Gdx.files.internal("new_images/ARROW_LEFT.png"));
        buttonBack = ButtonFactory.createButton(imageBack,0.1f*screenWidth -0.35f*screenWidth/2, screenHeight *0.9f,0.35f*screenWidth,0.08f*screenHeight);

    }
    private void check_Avatar() {
        String path = Configuration.getInstance().getTexturePath();
        if(path.equals("alfinge_avatar.png")) {
            alfinge_chosen = true;
            schau_chosen = false;
        } else if (path.equals("schau_avatar.png")) {
            schau_chosen = true;
            alfinge_chosen = false;
        }
    }

    @Override
    public void show() {
        buttonBack.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MainMenuScreen(game));
            }
        });
        buttonAlfinge.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                prefs.putString("Avatar", "alfinge_avatar.png");
                Configuration.getInstance().setPlayerTexture("alfinge_avatar.png");
                alfinge_chosen = true; schau_chosen = false;
            }
        });
        buttonSchau.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                prefs.putString("Avatar", "schau_avatar.png");
                Configuration.getInstance().setPlayerTexture("schau_avatar.png");
                alfinge_chosen = false; schau_chosen = true;
            }
        });

    }

    @Override
    public void render(float delta) {
        batch.begin();

        //page items
        batch.draw(backgroundImage,0,0,screenWidth,(float)backgroundImage.getHeight()*screenWidth/backgroundImage.getWidth());
        //font.draw(batch,"Avatar Selection",screenWidth*1/4-20, screenHeight-50);
        batch.draw(avatarSelectionTxt,screenWidth/3f, screenHeight*0.9f, screenWidth/2f, screenHeight/15f);

        //text
        BitmapFont font = new BitmapFont(); // initialize your font
        font.getData().setScale(5); // set the font scale factor to 2
        font.draw(batch, "Alf-Inge", screenWidth/8f,screenHeight*7/8f);
        font.draw(batch, "Christian\n  Schau", screenWidth*7/12f,screenHeight*7/8f);
        //the !updated thing is because when you first click the page it says neither avatar is chosen which is wrong
        font.draw(batch, ((alfinge_chosen) ? "Chosen" : "Not chosen"),((screenWidth / 8f)),screenHeight/2f);
        font.draw(batch, ((schau_chosen) ? "Chosen" : "Not chosen"), ((schau_chosen) ? (screenWidth*4/7f) : (screenWidth/2f)), screenHeight/2f-20);
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
        //batch.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}

