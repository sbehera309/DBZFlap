package com.sandman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sandman.game.states.GameStateManager;
import com.sandman.game.states.MenuState;

public class Dragonball extends ApplicationAdapter {
	public static final int WIDTH = 1440 ;
	//public static final int WIDTH = Gdx.graphics.getWidth();
	//public static final int HEIGHT = Gdx.graphics.getHeight();
	public static final int HEIGHT = 2560;
	//public static final String TITLE = "DBZ Flappy Bird Edition";

	private GameStateManager gam;
	private SpriteBatch batch;
	public static Music music;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gam = new GameStateManager();
		music = Gdx.audio.newMusic(Gdx.files.internal("SSJ3 Goku Theme Extended.mp3"));
		music.setLooping(true);
		music.setVolume(0.8f);
		music.pause();
		Gdx.gl.glClearColor(1,0,0,1);
		//Starts the game state
		gam.push(new MenuState(gam));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gam.update(Gdx.graphics.getDeltaTime());
		gam.render(batch);
	}

	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
	}
}
