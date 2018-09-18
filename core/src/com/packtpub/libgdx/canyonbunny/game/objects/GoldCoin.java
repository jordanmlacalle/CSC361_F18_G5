package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.game.Assets; 

public class GoldCoin {
    
    /**
     * regGoldCoin - texture region with gold coin image
     * collected - flag marking this coin as collected or not collected
     */
    private TextureRegion regGoldCoin;
    
    public boolean collected;
    
    public GoldCoin () {
        init();
    }
    
    /**
     * Initialize GoldCoin dimensions, texture region, bounding box, and collected state
     */
    private void init () {
        dimension.set(0.5f, 0.5f);
        
        regGoldCoin = Assets.instance.goldCoin.goldCoin;
        
        // SEt bounding boc for collision detection
        bounds.set(0, 0, dimension.x, dimension.y);
        
        collected = false;
    }

    /**
     * Render the GoldCoin
     * @param batch
     */
    public void render (SpriteBatch batch) {
        if (collected) {
            return;
        }
        
        TextureRegion reg = null;
        reg = regGoldCoin;
        batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    }
    
    /**
     * Get the current score
     * @return current score
     */
    public int getScore () {
        return 100;
    }
}
