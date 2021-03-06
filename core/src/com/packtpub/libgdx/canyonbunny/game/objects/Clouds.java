package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.game.Assets;

/**
 * The Clouds class acts as a container for all of the Cloud objects in a level
 * 
 * @author Jordan
 *
 */
public class Clouds extends AbstractGameObject
{

    private float length;

    private Array<TextureRegion> regClouds;
    private Array<Cloud> clouds;

    /**
     * Inner class
     */
    private class Cloud extends AbstractGameObject
    {
        /**
         * regCloud - texture region containing the sprite for a cloud
         */
        private TextureRegion regCloud;

        public Cloud()
        {
        }

        /**
         * Sets regCloud to the given texture region
         * 
         * @param region
         */
        public void setRegion(TextureRegion region)
        {
            regCloud = region;
        }

        /**
         * Implementation of the inherited render method (from AbstractGameObject).
         * Renders a single Cloud.
         * 
         * @param batch SpriteBatch used to draw the Cloud
         */
        @Override
        public void render(SpriteBatch batch)
        {
            TextureRegion reg = regCloud;
            batch.draw(reg.getTexture(), position.x + origin.x, position.y + origin.y, origin.x, origin.y, dimension.x,
                    dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
                    reg.getRegionHeight(), false, false);
        }
    }

    public Clouds(float length)
    {
        this.length = length;
        init();
    }

    /**
     * Initialize texture region array and spawn clouds.
     */
    private void init()
    {
        dimension.set(3.0f, 1.5f);
        regClouds = new Array<TextureRegion>();
        regClouds.add(Assets.instance.levelDecoration.cloud01);
        regClouds.add(Assets.instance.levelDecoration.cloud02);
        regClouds.add(Assets.instance.levelDecoration.cloud03);

        int distFac = 5;
        int numClouds = (int) (length / distFac);
        clouds = new Array<Cloud>(2 * numClouds);
        for (int i = 0; i < numClouds; i++)
        {
            Cloud cloud = spawnCloud();
            cloud.position.x = i * distFac;
            clouds.add(cloud);
        }
    }

    /**
     * Creates and initializes a new cloud object
     * 
     * @return Returns a new cloud object
     */
    private Cloud spawnCloud()
    {
        Cloud cloud = new Cloud();
        cloud.dimension.set(dimension);
        // Select random cloud image
        cloud.setRegion(regClouds.random());
        // position
        Vector2 pos = new Vector2();
        pos.x = length + 10; // position after end of level
        pos.y += 1.75; // base position
        pos.y += MathUtils.random(0.0f, 0.2f) + (MathUtils.randomBoolean() ? 1 : -1); // random additional position
        cloud.position.set(pos);
        // speed
        Vector2 speed = new Vector2();
        speed.x += 0.5f; //base speed
        // random additional speed
        speed.x += MathUtils.random(0.0f, 0.75f);
        cloud.terminalVelocity.set(speed);
        speed.x *= -1; // move left
        cloud.velocity.set(speed);
        return cloud;
    }

    /**
     * Implementation of the inherited render method (from AbstractGameObject).
     * Renders all clouds contained in the clouds array.
     * 
     * @param batch
     */
    @Override
    public void render(SpriteBatch batch)
    {
        for (Cloud cloud : clouds)
            cloud.render(batch);
    }
    
    /**
     * Updates all clouds in the level. If a cloud has moved beyond the
     * end of the level (on the left) then it is destroyed. 
     * 
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime)
    {
        for(int i = clouds.size - 1; i >= 0; i--)
        {
            Cloud cloud = clouds.get(i);
            cloud.update(deltaTime);
            
            if(cloud.position.x < -10)
            {
                // cloud moved outside of world
                // destroy and spawn new cloud at end of level
                clouds.removeIndex(i);
                clouds.add(spawnCloud());
            }
        }
    }
}
