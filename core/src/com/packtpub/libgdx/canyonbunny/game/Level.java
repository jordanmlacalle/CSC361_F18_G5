package com.packtpub.libgdx.canyonbunny.game;

import com.packtpub.libgdx.canyonbunny.game.objects.Carrot;
import com.packtpub.libgdx.canyonbunny.game.objects.Goal;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.game.objects.AbstractGameObject;
import com.packtpub.libgdx.canyonbunny.game.objects.Clouds;
import com.packtpub.libgdx.canyonbunny.game.objects.Mountains;
import com.packtpub.libgdx.canyonbunny.game.objects.Rock;
import com.packtpub.libgdx.canyonbunny.game.objects.WaterOverlay;
import com.packtpub.libgdx.canyonbunny.game.objects.BunnyHead;
import com.packtpub.libgdx.canyonbunny.game.objects.Feather;
import com.packtpub.libgdx.canyonbunny.game.objects.GoldCoin;

public class Level
{
    public static final String TAG = Level.class.getName();


    public Array<Carrot> carrots;
    public Goal goal;
    
    /**
     * BLOCK_TYPE is used to identify different objects in a Level. Each object has
     * a unique RGBA color value.
     * 
     * @author Jordan
     *
     */
    public enum BLOCK_TYPE
    {
        EMPTY(0, 0, 0), // Black
        GOAL(255, 0, 0), // red
        ROCK(0, 255, 0), // Green
        PLAYER_SPAWNPOINT(255, 255, 255), // White
        ITEM_FEATHER(255, 0, 255), // Purple
        ITEM_GOLD_COIN(255, 255, 0); // Yellow

        private int color;

        /**
         * Sets the RGBA color as a single 32-bit integer.
         * 
         * @param r Red value
         * @param g Green value
         * @param b Blue value
         */
        private BLOCK_TYPE(int r, int g, int b)
        {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        /**
         * Compares the given color to the block color.
         * 
         * @param color The color used for comparison
         * @return Returns the boolean result of comparison
         */
        public boolean sameColor(int color)
        {
            return this.color == color;
        }

        /**
         * Gets the block color
         * 
         * @return Returns color
         */
        public int getColor()
        {
            return color;
        }
    }

    /**
     * rocks - array of all Rock objects in a level 
     * bunnyHead - the single BunnyHead object in a level (the player character) 
     * goldCoins - array of all GoldCoin objects in a level feathers - array of a Feather objects in a level
     */
    public Array<Rock> rocks;
    public BunnyHead bunnyHead;
    public Array<GoldCoin> goldcoins;
    public Array<Feather> feathers;

    // decoration
    public Clouds clouds;
    public Mountains mountains;
    public WaterOverlay waterOverlay;

    public Level(String filename)
    {
        init(filename);
    }

    /**
     * Initializes the current level. The level file is scanned and objects are
     * loaded according to the RGB values of the current pixel scanned.
     * 
     * @param filename The path to the image file containing the current level
     */
    private void init(String filename)
    {
        // player character
        bunnyHead = null;
        // objects
        rocks = new Array<Rock>();
        goldcoins = new Array<GoldCoin>();
        feathers = new Array<Feather>();
        carrots = new Array<Carrot>();

        // Load image file that represents the level data
        Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
        // Scan pixels from top-left to bottom-right
        int lastPixel = -1;
        for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++)
        {
            for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++)
            {
                AbstractGameObject obj = null;
                float offsetHeight = 0;
                // height grows from bottom to top
                float baseHeight = pixmap.getHeight() - pixelY;
                // Get color of current pixel as 32-bit RGBA value
                int currentPixel = pixmap.getPixel(pixelX, pixelY);
                /*
                 * Find matching color value to identify block type at (x, y) point and create
                 * the corresponding game object if there is a match
                 */
                // Empty space
                if (BLOCK_TYPE.EMPTY.sameColor(currentPixel))
                {
                    // do nothing
                } 
                else if (BLOCK_TYPE.ROCK.sameColor(currentPixel))
                {
                    if (lastPixel != currentPixel)
                    {
                        obj = new Rock();
                        float heightIncreaseFactor = 0.25f;
                        offsetHeight = -2.5f;
                        obj.position.set(pixelX, baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
                        rocks.add((Rock) obj);
                    } 
                    else
                    {
                        rocks.get(rocks.size - 1).increaseLength(1);
                    }
                } 
                else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel))
                {
                    obj = new BunnyHead();
                    offsetHeight = -3.0f;
                    obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
                    bunnyHead = (BunnyHead) obj;
                } 
                else if (BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel))
                {
                    obj = new Feather();
                    offsetHeight = -1.5f;
                    obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
                    feathers.add((Feather) obj);
                } 
                else if (BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel))
                {
                    obj = new GoldCoin();
                    offsetHeight = -1.5f;
                    obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
                    goldcoins.add((GoldCoin) obj);
                    //goal
                } else if (BLOCK_TYPE.GOAL.sameColor(currentPixel)) {
                	   obj = new Goal();
                	   offsetHeight = -7.0f;
                	   obj.position.set(pixelX, baseHeight + offsetHeight);
                	   goal = (Goal)obj;
                else
                {
                    // decode currentPixel color
                    int r = 0xff & (currentPixel >>> 24); // red color channel
                    int g = 0xff & (currentPixel >>> 16); // green color channel
                    int b = 0xff & (currentPixel >>> 8); // blue color channel
                    int a = 0xff & currentPixel; // alpha channel
                    Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r + "> g<" + g
                            + "> b<" + b + "> a<" + a + ">");
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

    /**
     * Draws all objects in the current level.
     * 
     * @param batch The SpriteBatch used to draw the objects
     */
    public void render(SpriteBatch batch)
    {
        // Draw Mountains
        mountains.render(batch);
        
        // Draw Goal
        goal.render(batch);

        // Draw Rocks
        for (Rock rock : rocks)
            rock.render(batch);

        // Draw Gold Coins
        for (GoldCoin goldCoin : goldcoins)
            goldCoin.render(batch);

        // Draw Feathers
        for (Feather feather : feathers)
            feather.render(batch);
        
        // Draw Carrots
        for (Carrot carrot : carrots)
        	carrot.render(batch);

        // Draw Player Character
        bunnyHead.render(batch);

        // Draw Water Overlay
        waterOverlay.render(batch);

        // Draw clouds
        clouds.render(batch);
    }

    /**
     * Update level objects according to deltaTime
     * 
     * @param deltaTime The time passed since the last frame was drawn
     */
    public void update(float deltaTime)
    {
        bunnyHead.update(deltaTime);
        for (Rock rock : rocks)
            rock.update(deltaTime);
        for (GoldCoin goldCoin : goldcoins)
            goldCoin.update(deltaTime);
        for (Feather feather : feathers)
            feather.update(deltaTime);
        for (Carrot carrot : carrots) 
        	carrot.update(deltaTime);
        clouds.update(deltaTime);
    }

}