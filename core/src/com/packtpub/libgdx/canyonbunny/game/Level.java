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
    public Array <Rock> rocks;
    
    //decoration
    public Clouds clouds;
    public Mountains mountains;
    public WaterOverlay waterOverlay;
    
    public Level (String filename) {
        init(filename);
    }
    
    private void init (String filename) {
        // objects
        rocks = new Array<Rock>();
        
        // Load image file that represents the level data
        Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
        // Scan pixels from top-left to bottom-right
        int lastPixel = -1;
        for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
            for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
                AbstractGameObject = null;
                float offsetHeight = 0;
                // height grows from bottom to top
                float baseHeight = pixmap.getHeight() - pixelY;
                // Get color of current pixel as 32-bit RGBA value
                int currentPixel = pixmap.getPixel(pixelX, pixelY);
                /* Find matching color value to identify block type at (x, y)
                 * point and create the corresponding game object if there is 
                 * a match
                 */
                // Empty space
                if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
                    //do nothing
                } else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) {
                    if (lastPixel != currentPixel) {
                        obj = new Rock();
                        float heightIncreaseFactor = 0.25f;
                        offsetHeight = -2.5f;
                        obj.position.set(pixelX, baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
                        rocks.add((Rock)obj);
                    } else {
                        rocks.get(rocks.size - 1).increaseLength(1);
                    }
                } else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) {
                    //handle case
                } else if (BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel)) {
                  //handle case
                } else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) {
                  //handle case
                } else {
                    // decode currentPixel color
                    int r = 0xff & (currentPixel >>> 24); //red color channel
                    int g = 0xff & (currentPixel >>> 16); //green color channel
                    int b = 0xff & (currentPixel >>> 8);  //blue color channel
                    int a = 0xff & currentPixel;          //alpha channel
                    Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r + "> g<" + g + "> b<" + b + "> a<" + a + ">");
                }
                lastPixel = currentPixel;
            }
        }
        
        // decoration
        clouds = new Clouds(pixmap.getWidth());
        clouds.position.set(0, 2);
        mountains = new Mountains(pixmap.getWidth());
        mountains.position.set(-1, -1);
        waterOverlay = new WaterOverlay(pixmap.getWidth());
        waterOverlay.position.set(0, -3.75f);
        
        // Free memory
        pixmap.dispose();
        Gdx.app.debug(TAG, "level '" + filename + "' loaded");
    }
    
    private void render (SpriteBatch batch) {
        // Draw Mountains
        mountains.render(batch);
        
        // Draw Rocks
        for (Rock rock : rocks)
            rock.render(batch);
        
        // Draw Water Overlay
        waterOverlay.render(batch);
    }

}