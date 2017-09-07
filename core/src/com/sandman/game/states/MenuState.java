package com.sandman.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sandman.game.Dragonball;
import com.sandman.game.sprites.animation;

/**
 * Created by Sandeep on 6/17/2016.
 */
public class MenuState extends State {
    private Texture background;
    private Texture playbtn;
    private Texture musictoggle;
    private Rectangle textureBounds;
    private Sprite btn;
    private Rectangle testbound;
    private Rectangle musicbound;
    private animation bag;

    public MenuState(GameStateManager gam){
        super(gam);
        cam.setToOrtho(false, Dragonball.WIDTH / 2 , Dragonball.HEIGHT / 2);
        //cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //cam.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        background = new Texture("testbg.jpg");
        bag = new animation(new TextureRegion(background), 4, 6.0f);
        playbtn = new Texture("playbtn.png");
        musictoggle = new Texture("musictoggle.png");

        textureBounds = new Rectangle(143, 2080, 350, 250);
        testbound = new Rectangle(0,0, 450, 300);
        musicbound = new Rectangle(1110 ,2200,450,300);

        //textureBounds = new Rectangle(cam.combined.getScaleX() / 2, cam.combined.getScaleY() / 2, 452, 298);

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            //Creates a bounds where if touched it will begin play state
            System.out.println(Gdx.input.getX() + "," + Gdx.input.getY());
            if(textureBounds.contains(Gdx.input.getX(), Gdx.input.getY())){
                gam.set(new LevelState(gam));
            }
            if(testbound.contains(Gdx.input.getX(), Gdx.input.getY())){
                gam.set(new AsteroidState(gam, 2, 0, 1));
            }
            if(musicbound.contains(Gdx.input.getX(), Gdx.input.getY())){
                if(Dragonball.music.isPlaying()){
                    Dragonball.music.pause();
                }else{
                    Dragonball.music.play();
                }
            }
            //Tests to see where it is being touched at on the screen
            //System.out.println(Gdx.input.getX() + "," + Gdx.input.getY());
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        bag.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        //Drawing the Textures
        sb.draw(bag.getFrame(), 0,0);
        sb.draw(playbtn, 80 , 185, 150, 80);
        sb.draw(musictoggle,540, 50 ,100 , 100);
        //sb.draw(playbtn, (Dragonball.WIDTH / 2) - (playbtn.getWidth() / 2), Dragonball.HEIGHT / 2);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        playbtn.dispose();

    }

}
