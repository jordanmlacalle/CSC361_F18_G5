package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.packtpub.libgdx.canyonbunny.game.Assets;

public class Mountains extends AbstractGameObject
{
    /**
     * regMountainLeft - texture region for the left side of the Mountain...
     * regMountainRight - texture region for the right side of the Mountain...
     */
    private TextureRegion regMountainLeft;
    private TextureRegion regMountainRight;

    private int length;

    public Mountains(int length)
    {
        this.length = length;
        init();
    }

    /**
     * Initializes the texture regions, dimension, origin, and length.
     */
    private void init()
    {
        dimension.set(10, 2);

        regMountainLeft = Assets.instance.levelDecoration.mountainLeft;
        regMountainRight = Assets.instance.levelDecoration.mountainRight;

        // Shift mountain and extend length
        origin.x = -dimension.x * 2;
        length += dimension.x * 2;
    }
    
    /**
     * Updates the position of the mountains with respect to the camera
     * @param camPosition the position of the camera
     */
    public void updateScrollPosition (Vector2 camPosition) {
    	   position.set(camPosition.x, position.y);
    }

    /**
     * Draws a single Mountain
     * 
     * @param batch     SpriteBatch used to draw the Mountain
     * @param offsetX   the offset along the x axis
     * @param offsetY   the offset along the y axis
     * @param tintColor the tint of the mountain
     */
    private void drawMountain(SpriteBatch batch, float offsetX, float offsetY, float tintColor, float parallaxSpeedX)
    {
        TextureRegion reg = null;
        batch.setColor(tintColor, tintColor, tintColor, 1);
        float xRel = dimension.x * offsetX;
        float yRel = dimension.y * offsetY;

        // Mountains span the whole level
        int mountainLength = 0;
        mountainLength += MathUtils.ceil(length / (2 * dimension.x) * (1-parallaxSpeedX));
        mountainLength += MathUtils.ceil(0.5f + offsetX);
        for (int i = 0; i < mountainLength; i++)
        {
            // Mountain left
            reg = regMountainLeft;
            batch.draw(reg.getTexture(), origin.x + xRel + position.x *parallaxSpeedX, position.y + origin.y + yRel, origin.x, origin.y,
                    dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(), false, false);
            xRel += dimension.x;

            // Mountain right
            reg = regMountainRight;
            batch.draw(reg.getTexture(), origin.x + xRel, position.y + origin.y + yRel, origin.x, origin.y,
                    dimension.x + 0.1f, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(), false, false);
            xRel += dimension.x;
        }
        // Reset color to white
        batch.setColor(1, 1, 1, 1);
    }

    /**
     * Implementation of the inherited render method (from AbstractGameObject).
     * Renders a set of three Mountains with different tints and offsets.
     * 
     * @param batch SpriteBatch used to draw the Mountains
     */
    @Override
    public void render(SpriteBatch batch)
    {
        // Distant mountains (dark gray)
        drawMountain(batch, 0.5f, 0.5f, 0.5f, 0.8f);
        // Distant mountains (gray)
        drawMountain(batch, 0.25f, 0.25f, 0.7f, 0.5f);
        // Distant mountains (light gray)
        drawMountain(batch, 0.0f, 0.0f, 0.9f, 0.3f);
    }
}
