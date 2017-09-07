package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
/**
 * Created by Sandeep on 6/17/2016.
 */
public class Items {
    public static final int ITEM_WIDTH = 20;
    private static final int FLUCTUATION = 100;
    private Random rand;
    private Random itemChoice;
    private int choice;
    private Texture badItem;
    private Rectangle boundsItem;
    private Texture goodItem;
    private Vector2 posItem;
    private Texture item;

    public Items(float x) {
        goodItem = new Texture("4star.png");
        //badItem = new Texture("goodItem.png");
        rand = new Random();
        //itemChoice = new Random();
        //choice = itemChoice.nextInt(10) + 1;
        //if(choice >= 4){
        //    item = goodItem;
        //}else{
        //  item = badItem;
        //}

        posItem = new Vector2(x , rand.nextInt(FLUCTUATION) + 600);

        boundsItem = new Rectangle(posItem.x, posItem.y, goodItem.getWidth(), goodItem.getHeight());
    }
    public Texture getItem(){
        return goodItem;
    }
    public Vector2 getPosItem(){
        return posItem;
    }
    //public boolean gItem(){
    //  if(choice >= 4){
    //    return true;
    //}
    //return false;
    //}
    public void reposition(float x){
        posItem.set(x, rand.nextInt(FLUCTUATION) + 600);
        boundsItem.setPosition(posItem.x, posItem.y);
    }
    public boolean collides(Rectangle player){
        return player.overlaps(boundsItem);
    }
    public Rectangle getBounds(){
        return boundsItem;
    }
    public void dispose(){
        item.dispose();
    }
}

