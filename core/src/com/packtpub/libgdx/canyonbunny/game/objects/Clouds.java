package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Clouds extends AbstractGameObject {
    
    private float length;
    
    private Array<TextureRegion> regClouds;
    private Array<Cloud> clouds;
    
    /**
     * Inner class
     */
    private class Cloud extends AbstractGameObject {
        /**
         * regCloud - texture region containing the sprite for a cloud
         */
        private TextureRegion regCloud;
        
        public Cloud () {}
        
        /**
         * Sets regCloud to the given texture region
         * @param region
         */
        public void setRegion (TextureRegion region) {
            regCloud = region;
        }
        
        /**
         * Implementation of the inherited render method (from AbstractGameObject).
         * Renders a single Cloud.
         * @param batch SpriteBatch used to draw the Cloud
         */
        @Override
        public void render (SpriteBatch batch) {
            TextureRegion reg = regCloud;
            batch.draw(reg.getTexture(), position.x + origin.x, position.y + origin.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
        }
    }
    
    public Clouds (float length) {
        this.length = length;
        init();
    }
    
    private void init()
    {
        
    }
}
