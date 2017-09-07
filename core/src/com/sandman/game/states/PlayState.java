package com.sandman.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.sandman.game.Dragonball;
import com.sandman.game.Extra.ScoreSystem;
import com.sandman.game.sprites.Goten;
import com.sandman.game.sprites.Items;
import com.sandman.game.sprites.Tube;


/**
 * Created by Sandeep on 6/17/2016.
 */
//Easy Level Playstate
public class PlayState extends State{
    //initialize and declare variables
    private static final int TUBE_SPACING = 275;
    private static final int ITEM_SPACING = 275;
    private static  final int TUBE_COUNT = 4;
    private static final int ITEM_COUNT = 4;
    private static final int GROUND_OFFSET = -50;

    //initialize the objects
    private Goten goten;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private int count;
    private int tempcount;
    private int progressiontemp;
    private Tube temp; //testing scoresystem
    private ScoreSystem scoreSystem; //testing scoresystem
    private Items tmp;

    private Array<Tube> tubes;
    private  Array<Items> items;

    //Create the parameters for the playstate
    public PlayState(GameStateManager gam, float x, float y, int score, int progression){
        super(gam);
        goten = new Goten(x, y);
        //Setting the camera
        cam.setToOrtho(false, Dragonball.WIDTH/ 2, Dragonball.HEIGHT / 2);
        bg = new Texture("firstbg.png");
        ground = new Texture("testing1.jpe");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth /2, GROUND_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_OFFSET);

        tubes = new Array<Tube>();
        items = new Array<Items>();

        // Adds Tubes with respect to tube spacing and tube width
        for(float i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH) + 250));
            items.add(new Items(i * (ITEM_SPACING + Items.ITEM_WIDTH) + 300));
        }
        //Adds items with respect to item spacing and tube width
        /*for(float i = 1; i <= ITEM_COUNT; i++){
            items.add(new Items(i * (ITEM_SPACING + Items.ITEM_WIDTH) + 300));
        }*/
        count = score;
        tempcount = 0;
        progressiontemp = progression;

        this.temp = tubes.get(1); //testing scoresystem
        this.tmp = items.get(1);
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
                gam.set(new ScoreScreen(gam, count, 1));
            }
            Items item = items.get(i);
            if(cam.position.x - (cam.viewportWidth / 2) > item.getPosItem().x + item.getItem().getWidth()){
                item.reposition(item.getPosItem().x + ((Items.ITEM_WIDTH + ITEM_SPACING) * ITEM_COUNT));
            }
            if(item.collides(goten.getBounds())){
                isTouched(item);
                item.reposition((item.getPosItem().x + ((Items.ITEM_WIDTH + ITEM_SPACING) * ITEM_COUNT)));
            }
            if(tube.collides(item.getBounds())){
                item.reposition((item.getPosItem().x + (Items.ITEM_WIDTH + ITEM_SPACING) * ITEM_COUNT));
            }
        //Updates the items so when it gets out of camera view it relocates the item ahead of where the camera is viewing
        }/*for(int i = 0; i < items.size; i++){
            Items item = items.get(i);
            if(cam.position.x - (cam.viewportWidth / 2) > item.getPosItem().x + item.getItem().getWidth()){
                item.reposition(item.getPosItem().x + ((Items.ITEM_WIDTH + ITEM_SPACING) * ITEM_COUNT));
            }
            if(item.collides(goten.getBounds())){
                isTouched(item);
                item.reposition((item.getPosItem().x + ((Items.ITEM_WIDTH + ITEM_SPACING) * ITEM_COUNT)));
            }
        }*/
        //When goten collides with the ground it goes back to MenuState
        if(goten.getPosition().y <= ground.getHeight() + GROUND_OFFSET){
            gam.set(new ScoreScreen(gam, count, 1));
        }
        if(tempcount > 20){
            gam.set(new AsteroidState(gam, 2, count, progressiontemp));
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
        for(Items item : items){
            ab.draw(item.getItem(), item.getPosItem().x, item.getPosItem().y);
        }
        ab.draw(scoreSystem.zeroth(), goten.getPosition().x + 15, cam.viewportHeight - scoreSystem.zeroth().getHeight() - 1);
        ab.draw(scoreSystem.first(), goten.getPosition().x + 20 + scoreSystem.first().getWidth(), cam.viewportHeight - scoreSystem.zeroth().getHeight() - 1);
        ab.draw(scoreSystem.second(), goten.getPosition().x + 25 + scoreSystem.first().getWidth() * 2, cam.viewportHeight - scoreSystem.zeroth().getHeight() - 1);
        ab.draw(scoreSystem.thirth(), goten.getPosition().x + 30 + scoreSystem.first().getWidth() * 3, cam.viewportHeight - scoreSystem.zeroth().getHeight() - 1);
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
        tempcount++;
        this.scoreSystem.splitScore(count);
        return true;
    }
    private boolean isTouched(Items item){
        this.tmp = item;
        count = count + 2;
        tempcount = tempcount +2;
        this.scoreSystem.splitScore(count);
        return true;
    }
}
