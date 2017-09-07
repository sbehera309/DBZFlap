package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Sandeep on 2/12/2017.
 */

public class Blast {
    //Initialize the variables
    private Vector3 position;
    public Vector3 velocity;
    // private Texture assGoku;
    private static final int GRAVITY = 0;
    private static final int MOVEMENT = 450;
    private Rectangle bounds;
    private Texture text;
    private animation blast;

    public Blast(float x, float y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);
        text = new Texture("kiblast.png");
        blast = new animation(new TextureRegion(text), 4, 0.5f);
        bounds = new Rectangle(x, y, text.getWidth()/4, text.getHeight());
    }
    public void update(float dt){
        //Sets how goten falls automatically unless intervened
        blast.update(dt);
        if(position.y >0){
            velocity.add(0,GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(0, MOVEMENT * dt, 0);
        if(position.y < 0){
            position.y = 0;
        }
        if(position.x > 1550){
            position.x = 1550;
        }

        //reset velocity soo it can be used again for the next frame
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y - 525);
        //System.out.println("Bound of Goku is : " + position.x + " " + position.y + " " + bounds.getWidth() + " " + bounds.getHeight());
    }

    public TextureRegion getTexture(){
        return blast.getFrame();
    }
    public Vector3 getPosition(){
        return position;
    }
    public void setPosition(Vector3 position){
        this.position = position;
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public boolean collides(Rectangle player){
        return player.overlaps(bounds);
    }
    public void dispose(){
        text.dispose();
    }
}
