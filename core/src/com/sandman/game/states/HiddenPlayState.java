package com.sandman.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.sandman.game.Dragonball;
import com.sandman.game.Extra.ScoreSystem;
import com.sandman.game.sprites.GravityGoten;
import com.sandman.game.sprites.Tube;

/**
 * Created by Sandeep on 6/20/2016.
 */
public class HiddenPlayState extends State {
    //initialize and declare variables
    private static final int TUBE_SPACING = 150;
    //private static final int ITEM_SPACING = 125;
    private static  final int TUBE_COUNT = 4;
    private static final int GROUND_OFFSET = -50;
    //private static final int ITEM_COUNT = 2;

    private GravityGoten goten;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private int count = 0;
    private String Scoring;
    private BitmapFont currentScore;

    private Tube temp; //testing scoresystem
    private ScoreSystem scoreSystem; //testing scoresystem


    private Array<Tube> tubes;
    //private Array<Items> items;

    //Create the parameters for the playstate
    public HiddenPlayState(GameStateManager gam, float x, float y){
        super(gam);
        goten = new GravityGoten(x, y);
        //Setting the camera
        cam.setToOrtho(false, Dragonball.WIDTH/ 2, Dragonball.HEIGHT / 2);
        bg = new Texture("kingkai.jpeg");
        ground = new Texture("testing1.jpe");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth /2, GROUND_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_OFFSET);
        Scoring = "" + count;
        currentScore = new BitmapFont();

        tubes = new Array<Tube>();
        //items = new Array<Items>();

        // Adds Tubes with respect to tube spacing and tube width
        for(float i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
            //if(i == 2){
            //items.add(new Items ((2/5 +1) * ITEM_SPACING + Items.ITEM_WIDTH));

            // }
        }
        this.temp = tubes.get(1); //testing scoresystem
        this.scoreSystem = new ScoreSystem(); //testing scoresystem
    }

    @Override
    protected void handleInput() {
        //When touched it makes goten fly regardless of where it is touched.
        if(Gdx.input.justTouched()){
            goten.fly();
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        goten.update(dt);
        cam.position.x = goten.getPosition().x + 80;

        //Updates the tubes so when it gets out of camera view it relocates the tubes ahead of where the camera is viewing.
        for(int i = 0 ; i < tubes.size; i++){
            Tube tube = tubes.get(i);
            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
                isPassed(tube);
            }
            //When goten collides with a tube it goes back to MenuState
            if(tube.collides(goten.getBounds())){
                gam.set(new ScoreScreen(gam, count, 0));
            }
        }
        //When goten collides with the ground it goes back to MenuState
        if(goten.getPosition().y <= ground.getHeight() + GROUND_OFFSET){
            gam.set(new ScoreScreen(gam, count, 0));
        }
        cam.update();

    }

    @Override
    public void render(SpriteBatch ab) {
        ab.setProjectionMatrix(cam.combined);
        ab.begin();
        //Draw the textures
        ab.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        ab.draw(goten.getTexture(), goten.getPosition().x, goten.getPosition().y);
        for(Tube tube: tubes){
            ab.draw(tube.getTopTube(), tube.getPosTopTube().x , tube.getPosTopTube().y);
            ab.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }
        ab.draw(scoreSystem.zeroth(), goten.getPosition().x + 15, cam.viewportHeight - scoreSystem.zeroth().getHeight() - 1);
        ab.draw(scoreSystem.first(), goten.getPosition().x + 20 + scoreSystem.first().getWidth(), cam.viewportHeight - scoreSystem.zeroth().getHeight() - 1);
        ab.draw(scoreSystem.second(), goten.getPosition().x + 25 + scoreSystem.first().getWidth() * 2, cam.viewportHeight - scoreSystem.zeroth().getHeight() - 1);
        ab.draw(scoreSystem.thirth(), goten.getPosition().x + 30 + scoreSystem.first().getWidth() * 3, cam.viewportHeight - scoreSystem.zeroth().getHeight() - 1);

        //ab.draw(ground, groundPos1.x, groundPos1.y);
        //ab.draw(ground, groundPos2.x, groundPos2.y);
        ab.end();

    }
    private void updateGround(){
        //Makes sure the ground is continuous by placing the part of the ground that just past the viewpoint to in front of the viewpoint
        if(cam.position.x - cam.viewportWidth/2 > groundPos1.x + ground.getWidth()){
            groundPos1.add(ground.getWidth() *2, 0);
        }
        if(cam.position.x - cam.viewportWidth/2 > groundPos2.x + ground.getWidth()){
            groundPos2.add(ground.getWidth() *2 , 0);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        goten.dispose();
        ground.dispose();
        for(Tube tube : tubes)
            tube.dispose();

    }
    private boolean isPassed(Tube tube){
        this.temp = tube;
        count++;
        this.scoreSystem.splitScore(count);
        return true;
    }
}
