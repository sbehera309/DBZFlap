package com.sandman.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sandman.game.Dragonball;

/**
 * Created by Sandeep on 6/19/2016.
 */
public class LevelState extends State{
    private Texture background;
    private Texture easy;
    private Rectangle easybound;
    private Texture normal;
    private Rectangle normalbound;
    private Texture hard;
    private Rectangle hardbound;
    private Texture menu;
    private Rectangle menubound;
    private Texture level;
    private Rectangle levelbound;
    public int hiddencount;

    public LevelState(GameStateManager gam){
        super(gam);
        cam.setToOrtho(false, Dragonball.WIDTH / 2, Dragonball.HEIGHT / 2);
        background = new Texture("LevelBg.jpg");
        level = new Texture("levelbtn.png");
        easy = new Texture("oneball.png");
        normal = new Texture("tball.png");
        hard = new Texture("threeball.png");
        menu = new Texture("backbtn.png");
        hiddencount = 0;
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            System.out.println(Gdx.input.getX() + "," + Gdx.input.getY());
            easybound = new Rectangle(90, 2041, 400, 400);
            normalbound = new Rectangle(600, 2041, 400, 400);
            hardbound = new Rectangle(1110, 2041, 400, 400);
            menubound = new Rectangle(25, 2251, 400, 400);
            levelbound = new Rectangle(396, 68, 701, 242);
            if(levelbound.contains(Gdx.input.getX(),Gdx.input.getY())){
                hiddencount++;
                if(hiddencount > 10){
                    gam.set(new HiddenPlayState(gam, 50, 300));
                }
            }
            if(easybound.contains(Gdx.input.getX(), Gdx.input.getY())){
                gam.set(new PlayState(gam, 50, 300, 1, 1));
            }
            if(normalbound.contains(Gdx.input.getX(), Gdx.input.getY())){
                gam.set(new NormalPlayState(gam, 50, 300));
            }
            if(hardbound.contains(Gdx.input.getX(), Gdx.input.getY())){
                gam.set(new HardPlayState(gam, 50, 300));
            }
            if(menubound.contains(Gdx.input.getX(), Gdx.input.getY())){
                gam.set(new MenuState(gam));
            }
            System.out.println(Gdx.input.getX() +"," + Gdx.input.getY());
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch ab) {
        ab.setProjectionMatrix(cam.combined);
        ab.begin();
        ab.draw(background, 0 ,0);
        ab.draw(level, 200, 1200, 300, 50);
        ab.draw(easy, 60, 150, 120, 120);
        ab.draw(normal, 300 , 150, 120, 120);
        ab.draw(hard, 540 , 150, 120, 120);
        ab.draw(menu, 25 , 50, 80, 60);
        ab.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        easy.dispose();
        normal.dispose();
        hard.dispose();
        menu.dispose();
        level.dispose();

    }
}
