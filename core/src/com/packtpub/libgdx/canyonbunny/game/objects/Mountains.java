package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.packtpub.libgdx.canyonbunny.game.Assets;

public class Mountains {
    private TextureRegion regMountainLeft;
    private TextureRegion regMountainRight;
    
    private int length;
    
    public Mountains (int length) {
        this.length = length;
        init();
    }
    
    private void init () {
        dimension.set(10, 2);
        
        regMountainLeft = Assets.instance.levelDecoration.mountainLeft;
        regMountainRight = Assets.instance.levelDecoration.mountainRight;
        
        // Shift mountain and extend length
    }
}
