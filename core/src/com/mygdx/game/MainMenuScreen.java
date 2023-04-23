package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    private Preferences prefs = Gdx.app.getPreferences("Lecturer-Fight");
    private final float screenHeight = Gdx.graphics.getHeight();
    private final float screenWidth = Gdx.graphics.getWidth();
    private SpriteBatch batch = new SpriteBatch();
    private BitmapFont font = new BitmapFont();
    private SettingsScreen settings;
    private final Lecturer_fight game;
    private OrthographicCamera camera;
    private final Stage stage;
    Texture titleImage, backgroundImage, madeByTxt, multiplayerImage;
    private Image buttonStart, buttonSettings, buttonScore, buttonTutorial, buttonAvatar;
    private TextureRegion regionCheckboxOn, regionCheckboxOff;
    private CheckBox checkBox;
    private boolean multiplayer;


    public MainMenuScreen(final Lecturer_fight game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.settings = new SettingsScreen(game);
        camera.setToOrtho(false,800,400);
        multiplayerImage = new Texture("new_images/MultiplayerTxt.png");
        titleImage = new Texture(Gdx.files.internal("new_images/TITLE.png"));
        backgroundImage = new Texture(Gdx.files.internal("new_images/BG.png"));

        madeByTxt = new Texture(Gdx.files.internal("new_images/madebyTxt.png"));

        font.getData().setScale(5,5);
        font.setColor(new Color(0x023D8Bff));

        prefs.putBoolean("Multiplayer", true);
        prefs.flush();
        //play music
        System.out.println("musikk er på: " + Configuration.getInstance().isMusic_on());
        if (Configuration.getInstance().isMusic_on()){
            Configuration.getInstance().playMusic();
        }
        initializeButtons();
        stage = new Stage(new ScreenViewport()); //Set up a stage for the ui
        stage.addActor(buttonStart); //Add the button to the stage to perform rendering and take input.
        stage.addActor(buttonSettings);
        stage.addActor(buttonAvatar);
        stage.addActor(buttonScore);
        stage.addActor(buttonTutorial);
        stage.addActor(checkBox);
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
    }

    private void initializeButtons(){
        Texture imageStart, imageSettings, imageScore, imageTutorial, imageCheckboxOff, imageCheckboxOn, imageAvatar;
        imageSettings = new Texture("new_images/SETTINGS.png");
        buttonSettings = ButtonFactory.createButton(imageSettings,0.1f*screenWidth -0.35f*screenWidth/2, screenHeight *0.9f,0.35f*screenWidth,0.08f*screenHeight);

        if(Configuration.getInstance().getPlayerTexture() == null) Configuration.getInstance().setPlayerTexture("alfinge_avatar.png");
        imageAvatar = Configuration.getInstance().getPlayerTexture();
        buttonAvatar =  ButtonFactory.createButton(imageAvatar,0.9f*screenWidth - 0.15f*screenWidth/2, screenHeight *0.9f,0.15f*screenWidth,0.08f*screenHeight);

        imageStart = new Texture("new_images/PLAY.png");
        buttonStart = ButtonFactory.createButton(imageStart,0.5f*screenWidth - 0.6f*screenWidth/2, screenHeight *0.6f,0.6f*screenWidth,0.12f*screenHeight);

        imageScore = new Texture(Gdx.files.internal("new_images/HIGHSCORE.png"));
        buttonScore = ButtonFactory.createButton(imageScore,0.5f*screenWidth - 0.6f*screenWidth/2, screenHeight *0.3f,0.6f*screenWidth,0.12f*screenHeight);

        imageTutorial = new Texture(Gdx.files.internal("new_images/TUTORIAL.png"));
        buttonTutorial = ButtonFactory.createButton(imageTutorial,0.5f*screenWidth - 0.6f*screenWidth/2, screenHeight *0.15f,0.6f*screenWidth,0.12f*screenHeight);

        imageCheckboxOn = new Texture(Gdx.files.internal("new_images/TOGGLE_ON.png"));
        imageCheckboxOff = new Texture(Gdx.files.internal("new_images/TOGGLE_OFF.png"));
        regionCheckboxOff = new TextureRegion(imageCheckboxOff);
        regionCheckboxOn = new TextureRegion(imageCheckboxOn);
        CheckBox.CheckBoxStyle checkboxStyle = new CheckBox.CheckBoxStyle();
        font.setColor(Color.WHITE);
        font.getData().setScale(5);
        checkboxStyle.font = font;
        checkboxStyle.checkboxOff = new TextureRegionDrawable(regionCheckboxOff);
        checkboxStyle.checkboxOn = new TextureRegionDrawable(regionCheckboxOn);
        checkBox = new CheckBox("", checkboxStyle);
        checkBox.setBounds(0.15f*screenWidth , screenHeight *0.5f, screenWidth*0.05f,screenHeight*0.05f);
    }
    @Override  // Add listeners to check when user clicks on buttons
    public void show() {
       buttonStart.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new GameScreen(game, multiplayer));
                System.out.println("Start Button");
            }
        });
        buttonSettings.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new SettingsScreen(game));
                System.out.println("Settings Button");
            }
        });

        buttonAvatar.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new AvatarScreen(game));
                System.out.println("Avatar Button");
            }
        });

        buttonScore.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new HighScoreScreen(game,true,false, 0, 0,null ));
                System.out.println("Score Button");
            }
        });
        buttonTutorial.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new TutorialScreen(game));
                System.out.println("Tutorial Button");
            }
        });
        checkBox.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                    System.out.println(("clickeo multiplayer"));
                    boolean isChecked = checkBox.isChecked();
                    multiplayer = isChecked;
                    System.out.println(multiplayer);
                }
        });
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //design
        batch.draw(backgroundImage,0,0,screenWidth,backgroundImage.getHeight()*screenWidth/backgroundImage.getWidth());
        batch.draw(titleImage,screenWidth/2 - 0.9f*screenWidth/2, 0.67f*screenHeight, 0.9f*screenWidth, 0.25f*screenHeight);
        batch.draw(multiplayerImage,checkBox.getX() + screenWidth*0.6f/3, screenHeight *0.5f, screenWidth*0.6f,screenHeight*0.06f);
        //text
        //font.draw(batch, "Made by Group 16", 0.3f*screenWidth/2, 0.1f*screenHeight);
        batch.draw(madeByTxt,0,-50,screenWidth,madeByTxt.getHeight()*screenWidth/madeByTxt.getWidth());
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
        batch.dispose();
        stage.dispose();
    }

    @Override
    public void dispose() {
        camera = null;
        stage.dispose();
        batch.dispose();
    }

}
