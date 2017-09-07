package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by Sandeep on 6/17/2016.
 */
public class Tube {
    //Initialize the variables and assign specific values to them
    public static final int TUBE_WIDTH = 90;
    private static final int FLUCTUATION = 250;
    private static final int TUBE_GAP = 340;
    private static final int LOWEST_OPENING = 120;
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBottomTube;
    private Rectangle boundsTop, boundsBottom;
    private Random rand;

    //Set the parameters needed for the class
    //For this specific class it just requires the x position of where the tube is going to be at.
    public Tube(float x){
        topTube = new Texture("top.png");
        bottomTube = new Texture("bottom.png");
        rand = new Random();
        //Places the top tube at random y positions and then places the bottom tube according to the top tube along with the tube gap and lowest opening
        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING + 200 );
        posBottomTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        //Creates bounds for the tube such that if it overlaps we can distinguish it.
        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBottom = new Rectangle(posBottomTube.x, posBottomTube.y, bottomTube.getWidth(), bottomTube.getHeight());
    }
    public Texture getTopTube(){
        return topTube;
    }
    public Texture getBottomTube(){
        return bottomTube;
    }
    public Vector2 getPosTopTube(){
        return posTopTube;
    }
    public Vector2 getPosBottomTube(){
        return posBottomTube;
    }
    //Repositions the tube using the random again to make sure it changes y positions.
    public void reposition(float x){
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING + 260);
        posBottomTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBottom.setPosition(posBottomTube.x, posBottomTube.y);
    }
    //Method that returns true or false if the player's bounds overlaps with the tube's bounds
    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBottom);
    }
    public void dispose(){
        topTube.dispose();
        bottomTube.dispose();
    }
}
