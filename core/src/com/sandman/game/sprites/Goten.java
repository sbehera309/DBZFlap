package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Sandeep on 6/17/2016.
 */
public class Goten {
    //Initialize the variables
    private Vector3 position;
    private Vector3 velocity;
    private Texture goten;
    private static final int GRAVITY = -30;
    private static final int MOVEMENT = 180;
    private Rectangle bounds;
    private animation gotenAnimation;
    private Texture text;

    //Create the parameters that are needed for the class
    //Requires the position of where goten is going to be at
    public Goten(float x, float y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);
        text = new Texture("gokuflying.png");
        //Causes goten to be slightly animated by splitting the picture into different frames of goten
        gotenAnimation = new animation(new TextureRegion(text), 2, 0.3f);
        bounds = new Rectangle(x,y, text.getWidth()/3, text.getHeight());
    }
    public void update(float dt){
        //Sets how goten falls automatically unless intervened
        gotenAnimation.update(dt);
        if(position.y >0){
            velocity.add(0,GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);
        if(position.y < 0){
            position.y = 0;
        }

        //reset velocity soo it can be used again for the next frame
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }
    public void setGoten(Texture goten){
        this.goten = goten;
    }
    public TextureRegion getTexture(){
        return gotenAnimation.getFrame();
    }
    public Vector3 getPosition(){
        return position;
    }
    public void setPosition(Vector3 position){
        this.position = position;
    }
    //Changes the y position by 250 to make it appear that goten is flying
    public void fly(){
        velocity.y = 600;
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public void dispose(){
        text.dispose();
    }
}
