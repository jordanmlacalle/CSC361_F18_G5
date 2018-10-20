package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.packtpub.libgdx.canyonbunny.game.Assets;

public class GoldCoin extends AbstractGameObject
{

    /**
     * regGoldCoin - texture region with gold coin image 
     * collected - flag marking this coin as collected or not collected
     */
    public boolean collected;

    public GoldCoin()
    {
        init();
    }

    /**
     * Initialize GoldCoin dimensions, texture region, bounding box, and collected
     * state
     */
    private void init()
    {
        dimension.set(0.5f, 0.5f);

        setAnimation(Assets.instance.goldCoin.animGoldCoin);
        // Set coin to random frame in animation
        stateTime = MathUtils.random(0.0f, 1.0f);

        // Set bounding box for collision detection
        bounds.set(0, 0, dimension.x, dimension.y);

        collected = false;
    }

    /**
     * Render the GoldCoin
     * 
     * @param batch
     */
    public void render(SpriteBatch batch)
    {
        if (collected)
        {
            return;
        }

        TextureRegion reg = null;
        reg = animation.getKeyFrame(stateTime, true);
        batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
                scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
                false, false);
    }

    /**
     * Get the score value for GoldCoin
     * 
     * @return score value
     */
    public int getScore()
    {
        return 100;
    }
}
