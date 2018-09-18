package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.game.Assets;

public class Feather extends AbstractGameObject {
    
    private TextureRegion regFeather;
    
    public boolean collected;
    
    public Feather () {
        init();
    }
    
    /**
     * Initialize Feather dimensions, texture region, bounding box, and collected state
     */
    private void init () {
        dimension.set(0.5f, 0.5f);
        
        regFeather = Assets.instance.feather.feather;
        
        // Set bounding box for collision
        bounds.set(0, 0, dimension.x, dimension.y);
        
        collected = false;
    }
    
    /**
     * Render the Feather
     */
    public void render (SpriteBatch batch) {
        if (collected) {
            return;
        }
        
        TextureRegion reg = null;
        reg = regFeather;
        batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    }
    
    /**
     * Gets the score value for the Feather
     * @return score value
     */
    public int getScore () {
        return 250;
    }

}
