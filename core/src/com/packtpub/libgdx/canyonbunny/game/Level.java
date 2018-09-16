package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.game.objects.AbstractGameObject;
import com.packtpub.libgdx.canyonbunny.game.objects.Clouds;
import com.packtpub.libgdx.canyonbunny.game.objects.Mountains;
import com.packtpub.libgdx.canyonbunny.game.objects.Rock;
import com.packtpub.libgdx.canyonbunny.game.objects.WaterOverlay;


public class Level {
    public static final String TAG = Level.class.getName();
    
    /**
     * BLOCK_TYPE is used to identify different objects in a Level.
     * Each object has a unique RGBA color value.
     * @author Jordan
     *
     */
    public enum BLOCK_TYPE {
        EMPTY(0, 0, 0), // Black
        ROCK(0, 255, 0), // Green
        PLAYER_SPAWNPOINT(255, 255, 255), // White
        ITEM_FEATHER(255, 0, 255), // Purple
        ITEM_GOLD_COIN(255, 255, 0); // Yellow
        
        private int color;
        
        /**
         * Sets the RGBA color as a single 32-bit integer.
         * @param r Red value
         * @param g Green value
         * @param b Blue value
         */
        private BLOCK_TYPE (int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }
        
        /**
         * Compares the given color to the block color. 
         * @param color the color used for comparison
         * @return Returns the boolean result of comparison
         */
        public boolean sameColor (int color) {
            return this.color == color;
        }
        
        /**
         * Gets the block color
         * @return Returns color
         */
        public int getColor () {
            return color;
        }
    }
    
    //objects
    public Array <rock> rocks;
    
    //decoration
    public Clouds clouds;
    public Mountains mountains;
    public WaterOverlay waterOverlay;
    
    public Level (String filename) {
        init(filename);
    }
    
    private void init (String filename) {}
    
    private void render (SpriteBatch batch) {}

}