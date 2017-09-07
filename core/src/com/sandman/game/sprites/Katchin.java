package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sandman.game.Dragonball;

import java.util.Random;

/**
 * Created by Sahil on 10/22/16.
 */

public class Katchin {
    //Initialize the variables and assign specific values to them
    public static final int KATCHING_WIDTH = 52;
    private Texture katchin;
    private Vector2 posKatchin;
    private Rectangle debrisBound;
    private Random rand;

    //Set the parameters needed for the class
    //For this specific class it just requires the x position of where the tube is going to be at.
    public Katchin(float x){
        katchin = new Texture("backbtn.png");
        rand = new Random();

        //Places the debris at random y positions
        posKatchin = new Vector2(x, rand.nextInt(Dragonball.HEIGHT/3));

        //Creates bounds for the debris such that if it overlaps we can distinguish it.
        debrisBound = new Rectangle(posKatchin.x, posKatchin.y, katchin.getWidth(), katchin.getHeight());
    }

    public Texture getRoids(){
        return katchin;
    }
    public Vector2 getPosDebris(){
        return posKatchin;
    }

    //Repositions the debris using the random again to make sure it changes y positions.
    public void reposition(float x){
        posKatchin.set(x, rand.nextInt(Dragonball.HEIGHT));
        debrisBound.setPosition(posKatchin.x, posKatchin.y);
    }

    //Method that returns true or false if the player's bounds overlaps with the tube's bounds
    public boolean collides(Rectangle player){
        return player.overlaps(debrisBound);
    }

    public void dispose(){
        katchin.dispose();
    }
}