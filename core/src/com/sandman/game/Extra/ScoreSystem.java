package com.sandman.game.Extra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.sandman.game.sprites.Goten;
import com.sandman.game.sprites.Tube;

/**
 * Created by Sandeep on 6/21/2016.
 */
public class ScoreSystem {
    public Preferences pref;

    private Texture[] numbers = {new Texture("0.png"),new Texture("1.png"),new Texture("2.png"),new Texture("3.png"),new Texture("4.png"),new Texture("5.png"),new Texture("6.png"),new Texture("7.png"),new Texture("8.png"),new Texture("9.png"), };

    private int zeroth = 0, first = 0, second = 0, third = 0;

    public ScoreSystem(){
        this.pref = Gdx.app.getPreferences("DBZ Flap Preferences");
    }

    public Texture zeroth(){
        return numbers[this.zeroth];
    }
    public Texture first(){
        return numbers[this.first];
    }
    public Texture second(){
        return numbers[this.second];
    }
    public Texture thirth(){
        return numbers[this.third];
    }
    public void splitScore(int score){
        int[] scorePartial = new int[4];
        int i = 3;
        int rem;
        int temp = score;

        while(temp != 0){
            rem = temp % 10;
            temp /= 10;
            scorePartial[i--] = rem;
        }
        zeroth = scorePartial[0];
        first = scorePartial[1];
        second = scorePartial[2];
        third = scorePartial[3];
    }

    public void setHighScore(int HighScore){
        this.pref.putInteger("High Score", HighScore);
    }
    public int getHighScore(){
        return this.pref.getInteger("High Score");
    }
    public void saveHigh(){
        this.pref.flush();
    }
}
