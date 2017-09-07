package com.sandman.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sandman.game.Dragonball;
import com.sandman.game.Extra.ScoreSystem;
import com.sandman.game.states.State;

/**
 * Created by Sandeep on 6/18/2016.
 */
public class ScoreScreen extends State {
    private Texture background;
    private Texture gameover;
    private int count;
    private ScoreSystem scoreSys;
    private ScoreSystem highscore;
    private final int Y_OFFSET = 100;
    //private String ScoreName;
    //private BitmapFont score;
    private Texture playbtn;
    private Texture menubtn;
    //int level corresponds to which level is it coming from
    //easy is 1
    //normal is 2
    //hard is 3
    //gravity level is 0

    public ScoreScreen (GameStateManager gam, int count, int level){
        super(gam);
        this.count = count;
        cam.setToOrtho(false, Dragonball.WIDTH / 2, Dragonball.HEIGHT / 2);
        if(level == 1){
            background = new Texture("firstbg.png");
        }
        if(level == 2){
            background = new Texture("cell1.jpg");
        }
        if(level == 3){
            background = new Texture("buu.png");
        }
        if(level == 4){
            background = new Texture("astriodFreeza.jpg");
        }
        if(level == 5){
            background = new Texture("assbg.png");
        }
        if(level == 6){
            background = new Texture("astroidHardest.jpg");
        }
        if(level == 0){
            background = new Texture("kingkai.jpeg");
        }
        //background = new Texture("endbg.png");
        gameover = new Texture("gameover.png");
        playbtn = new Texture("playbtn.png");
        menubtn = new Texture("menubtn.png");
        //ScoreName = "Score: " + count;
        //score = new BitmapFont();
        scoreSys = new ScoreSystem();
        highscore = new ScoreSystem();
        scoreSys.splitScore(count);

        if(highscore.getHighScore() == 0){
            highscore.setHighScore(count);
            highscore.saveHigh();
        }
        if(highscore.getHighScore() < count){
            highscore.setHighScore(count);
            highscore.saveHigh();
        }

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            System.out.println(Gdx.input.getX() + " , " + Gdx.input.getY());
            Rectangle playBounds = new Rectangle( 463, 1460, 452, 298);
            if(playBounds.contains(Gdx.input.getX(), Gdx.input.getY())){
                gam.set(new LevelState(gam));
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb){
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0 ,0, cam.viewportWidth, cam.viewportHeight);
        sb.draw(gameover, 115,600, 500,500);
        sb.draw(playbtn, 300, 500, 150,80);
        //sb.draw(menubtn, 0,0);

        //Score should be 30 x coord difference
        if(count >= 1000){
        sb.draw(scoreSys.zeroth(), 460, 800);}
        if(count >= 100){
        sb.draw(scoreSys.first(), 490, 800);}
        if(count >= 10){
        sb.draw(scoreSys.second(), 520, 800);}

        sb.draw(scoreSys.thirth(), 550, 800);

        highscore.splitScore(highscore.getHighScore());
        if(highscore.getHighScore() >= 1000){
        sb.draw(highscore.zeroth(), 460, 700);}

        if(highscore.getHighScore() >= 100){
        sb.draw(highscore.first(), 490, 700);}

        if(highscore.getHighScore() >= 10){
        sb.draw(highscore.second(), 520, 700);}

        sb.draw(highscore.thirth(), 550, 700);
        //score.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        //score.draw(sb, ScoreName, 25, 150);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playbtn.dispose();
        //score.dispose();

    }
}
