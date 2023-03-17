package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import sun.font.TextLabel;

public class Lecturer_fight extends Game {

	public BitmapFont font;
	SpriteBatch batch;

	ShapeRenderer startButton;
	ShapeRenderer settingButton;
	ShapeRenderer scoreButton;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();


		this.setScreen(new MainMenuScreen(this));
		startButton = new ShapeRenderer();
		settingButton = new ShapeRenderer();
		scoreButton = new ShapeRenderer();


	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
