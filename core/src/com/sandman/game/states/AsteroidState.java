package com.sandman.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.sandman.game.Dragonball;
import com.sandman.game.Extra.ScoreSystem;
import com.sandman.game.sprites.Blast;
import com.sandman.game.sprites.Roids;
import com.sandman.game.sprites.assGoku;

/**
 * Created by halwasingh77 on 10/22/2016.
 */

public class AsteroidState extends State {
    private assGoku goku;
    private Blast blast;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Array<Roids> ass;
    private Array<Blast> blastarr;
    private static final int DEBRIS_COUNT = 100;
    private static final int DEBRIS_SPACING = 5;
    private static final int GROUND_OFFSET = -50;
    //private Ki blast;
    private ScoreSystem scoreSystem;
    private int score;
    private int tempcount;
    private int progressiontemp;
    private Rectangle exitbounds, leftbound, rightbound, hitbound;
    private int level;

    public AsteroidState(GameStateManager gam, int x, int count, int progression) {
        super(gam);
        goku = new assGoku(370, 30);
        blast = new Blast(1000, 30);
        score = count;
        tempcount = 0;
        progressiontemp = progression;

        cam.setToOrtho(false, Dragonball.WIDTH / 2, Dragonball.HEIGHT / 2);
        if (x == 1) {
            bg = new Texture("astriodFreeza.jpg");
            level = 4;
        }
        if (x == 2) {
            bg = new Texture("assbg.png");
            level = 5;
        }
        if (x == 3) {
            bg = new Texture("astroidHardest.jpg");
            level = 6;
        }
        //ground = new Texture("testing1.jpe");
        //groundPos1 = new Vector2(GROUND_OFFSET, cam.position.y - cam.viewportHeight / 2);
        //groundPos2 = new Vector2(GROUND_OFFSET, (cam.position.y - cam.viewportHeight / 2) + ground.getHeight());

        cam.setToOrtho(false, Dragonball.WIDTH / 2, Dragonball.HEIGHT / 2);
        ass = new Array<Roids>();
        blastarr = new Array<Blast>();

        //for(float i = 1)

        for (float i = 1; i <= progressiontemp * DEBRIS_COUNT; i++) {
            ass.add(new Roids(i * (DEBRIS_SPACING + Roids.DEBRIS_HEIGHT) * 2));
        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()) {
            exitbounds = new Rectangle(440, 60, 730, 210);
            leftbound = new Rectangle(0, 2260, 660, 1030);
            rightbound = new Rectangle(720, 2260, 660, 1030);
            hitbound = new Rectangle(0, 1500, 2000, 530);
            Vector3 movingleft = new Vector3(goku.getPosition().x - 8,goku.getPosition().y, 0);
            Vector3 movingright = new Vector3(goku.getPosition().x + 8,goku.getPosition().y, 0);
            Vector3 gokulocation = new Vector3(goku.getPosition().x, goku.getPosition().y - 80, 0);
            if (exitbounds.contains(Gdx.input.getX(), Gdx.input.getY())) {
                gam.set(new MenuState(gam));
            }
            //System.out.println(Gdx.input.getX() + "," + Gdx.input.getY());
            if (leftbound.contains(Gdx.input.getX(), Gdx.input.getY())){
                System.out.println("Left Bound touched");
                if(goku.getPosition().x > 0){
                goku.setPosition(movingleft);}

            }
            if (rightbound.contains(Gdx.input.getX(), Gdx.input.getY())){
                System.out.println("Right Bound touched");
                //goku.moveRight();
                if(goku.getPosition().x < 100);{
                goku.setPosition(movingright);}
            }
            if(hitbound.contains(Gdx.input.getX(), Gdx.input.getY())){
                System.out.println("hit bound is touched");
                if(blast.getPosition().y > cam.viewportHeight){

                    blast.setPosition(gokulocation);
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        //updateGround();
        goku.update(dt);
        blast.update(dt);
        cam.position.y = goku.getPosition().y + 80;

        for (int i = 0; i < ass.size; i++){
            Roids roid = ass.get(i);
            if (cam.position.y - (cam.viewportHeight / 2) > roid.getPosDebris().y + roid.getRoids().getHeight()){
                roid.reposition(roid.getPosDebris().y + ((Roids.DEBRIS_HEIGHT + DEBRIS_SPACING) * (progressiontemp * DEBRIS_COUNT * 2)));
                //System.out.println("Bounds for AnalTeroid: " + ass.get(i).getPosDebris().x + " " + ass.get(i).getPosDebris().y + " " + roid.getRoids().getWidth() + " " + roid.getRoids().getHeight());

            }
            if (roid.collides(goku.getBounds())){
                gam.set(new ScoreScreen(gam, score, level));
                dispose();
            }
            if(roid.collides(blast.getBounds())){
                score++;
                tempcount++;
                Vector3 hitposition = new Vector3(1000, goku.getPosition().y, 0);
                blast.setPosition(hitposition);
                roid.reposition(roid.getPosDebris().y + ((Roids.DEBRIS_HEIGHT + DEBRIS_SPACING) *(progressiontemp * DEBRIS_COUNT * 2)));
            }
        }
        if(tempcount > 20){
            gam.set(new PlayState(gam, 50, 300, score, progressiontemp+1));
            dispose();
            System.out.println("it has been disposed");
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch ab) {
        ab.setProjectionMatrix(cam.combined);
        ab.begin();
        ab.draw(bg, cam.position.x - (cam.viewportWidth / 2), goku.getPosition().y - 560, cam.viewportWidth, cam.viewportHeight);
        ab.draw(goku.getTexture(), goku.getPosition().x, goku.getPosition().y - 525);
        ab.draw(blast.getTexture(), blast.getPosition().x, blast.getPosition().y - 525);

        for (Roids roid : ass) {
            ab.draw(roid.getRoids(), roid.getPosDebris().x, roid.getPosDebris().y);
        }

        //ab.draw(scoreSystem.zeroth(), goku.getPosition().x + 15, cam.viewportHeight - scoreSystem.zeroth().getHeight() - 1);
        //ab.draw(scoreSystem.first(), goku.getPosition().x + 20 + scoreSystem.first().getWidth(), cam.viewportHeight - scoreSystem.zeroth().getHeight() - 1);
        //ab.draw(scoreSystem.second(), goku.getPosition().x + 25 + scoreSystem.first().getWidth() * 2, cam.viewportHeight - scoreSystem.zeroth().getHeight() - 1);
        //ab.draw(scoreSystem.thirth(), goku.getPosition().x + 30 + scoreSystem.first().getWidth() * 3, cam.viewportHeight - scoreSystem.zeroth().getHeight() - 1);
        ab.end();
    }

    /* private void updateGround(){
        //Makes sure the ground is continuous by placing the part of the ground that just past the viewpoint to in front of the viewpoint
        if(cam.position.y - cam.viewportHeight/2 > groundPos1.y + ground.getHeight()){
            groundPos1.add(0, ground.getHeight() * 2);
        }
        if(cam.position.y - cam.viewportHeight/2 > groundPos2.y + ground.getHeight()){
            groundPos2.add(0, ground.getHeight() * 2);
        }
    } */

    @Override
    public void dispose() {
        bg.dispose();
        goku.dispose();
        //ground.dispose();
        for (Roids roid: ass){
            roid.dispose();
        }
    }
}
