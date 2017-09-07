package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Sandeep on 6/17/2016.
 */
public class assGoku{
    //Initialize the variables
    private Vector3 position;
    public Vector3 velocity;
    // private Texture assGoku;
    private static final int GRAVITY = 0;
    private static final int MOVEMENT = 150;
    private Rectangle bounds;
    private gokuAnimation assGokuAnimation;
    private Texture text;

    //Create the parameters that are needed for the class
    //Requires the position of where Goku is going to be at
    public assGoku(float x, float y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);
        text = new Texture("assgoku1.png");
        //Causes Goku to be slightly animated by splitting the picture into different frames of Goku
        assGokuAnimation = new gokuAnimation(new TextureRegion(text), 3, 0.5f);
        bounds = new Rectangle(x, y, text.getWidth(), text.getHeight()/3);
    }
    public void update(float dt){
        //Sets how goten falls automatically unless intervened
        assGokuAnimation.update(dt);
        if(position.y >0){
            velocity.add(0,GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(0, MOVEMENT * dt, 0);
        if(position.y < 0){
            position.y = 0;
        }
        if(position.x >750){
            position.x = 750;
        }

        //reset velocity soo it can be used again for the next frame
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y - 525);
        //System.out.println("Bound of Goku is : " + position.x + " " + position.y + " " + bounds.getWidth() + " " + bounds.getHeight());
    }
    public TextureRegion getTexture(){
        return assGokuAnimation.getFrame();
    }
    public Vector3 getPosition(){
        return position;
    }
    public void moveLeft() { //velocity.x = -200;
        position.x = -2;
    }
    public void moveRight() { //velocity.x = 200;
        position.x = 5;
    }
    public void setPosition(Vector3 position){
        this.position = position;
    }
    public Rectangle getBounds(){
        return bounds;
    }
    public void dispose(){
        text.dispose();
    }
}
