package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sandman.game.Dragonball;

import java.util.Random;

/**
 * Created by Sahil on 10/22/16.
 */

public class Roids {
    //Initialize the variables and assign specific values to them
    public static final int DEBRIS_HEIGHT = 122;
    private Texture debris;
    private Vector2 posDebris;
    private Rectangle debrisBound;
    private Random rand;

    //Set the parameters needed for the class
    //For this specific class it just requires the x position of where the tube is going to be at.
    public Roids(float y){
        debris = new Texture("onepunch.png");
        rand = new Random();

        //Places the debris at random y positions
        posDebris = new Vector2(rand.nextInt(Dragonball.WIDTH/3), y);

        //Creates bounds for the debris such that if it overlaps we can distinguish it.
        debrisBound = new Rectangle(posDebris.x, y, 75, 122);
    }

    public Texture getRoids(){
        return debris;
    }
    public Vector2 getPosDebris(){
        return posDebris;
    }

    //Repositions the debris using the random again to make sure it changes y positions.
    public void reposition(float y){
        posDebris.set(rand.nextInt(Dragonball.WIDTH/3), y);
        debrisBound.setPosition(posDebris.x, posDebris.y);
    }

    //Method that returns true or false if the player's bounds overlaps with the tube's bounds
    public boolean collides(Rectangle player){
        return player.overlaps(debrisBound);
    }

    public void dispose(){
        debris.dispose();
    }
}