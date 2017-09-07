package com.sandman.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Sandeep on 6/17/2016.
 */
public class gokuAnimation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;

    public gokuAnimation(TextureRegion region, int frameCount, float cycletime){
        frames = new Array<TextureRegion>();
        //int frameWidth = region.getRegionWidth() / frameCount;
        int frameHeight = region.getRegionHeight() / frameCount;
        for(int i = 0; i < frameCount; i++){
            // frames.add(new TextureRegion(region, i*frameWidth, 0 , frameWidth, region.getRegionHeight()));
            frames.add(new TextureRegion(region, 0, i*frameHeight, region.getRegionWidth(), frameHeight));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycletime / frameCount;
        frame = 0;
    }

    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount){
            frame = 0;
        }
    }
    public TextureRegion getFrame(){
        return frames.get(frame);
    }
}
