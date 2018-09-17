package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.game.Assets;

public class Rock extends AbstractGameObject{

    /**
     * regEdge - the texture region to be used for the edges (right and left) of a Rock
     * regMiddle - the texture region to be used for all sections of a Rock that are not an edge
     */
    private TextureRegion regEdge;
    private TextureRegion regMiddle;

    private int length;

    public Rock () {
        init();
    }

    private void init () {
        dimension.set(1, 1.5f);

        regEdge = Assets.instance.rock.edge;
        regMiddle = Assets.instance.rock.middle;

        // Start length of this rock
        setLength(1);
    }

    /**
     * Sets the length of the Rock
     * @param length the desired length of the Rock
     */
    public void setLength (int length) {
        this.length = length;
    }

    /**
     * Increases length by the given amount
     * @param amount the value to increase the Rock's length by
     */
    public void increaseLength (int amount) {
        setLength(length + amount);
    }

    /**
     * Implementation of the inherited render method (from AbstractGameObject).
     * Renders regEdge and regMiddle texture regions to draw this Rock.
     * @param batch SpriteBatch used to draw the Rock
     */
    @Override
    public void render (SpriteBatch batch) {
        TextureRegion reg = null;

        float relX = 0;
        float relY = 0;

        // Draw left edge
        reg = regEdge;
        relX -= dimension.x / 4;
        batch.draw(reg.getTexture(), position.x + relX, position.y + relY, 
                origin.x, origin.y, dimension.x / 4, dimension.y, scale.x, scale.y,
                rotation, reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(), false, false);

        // Draw middle
        relX = 0;
        reg = regMiddle;
        for (int i = 0; i < length; i++) {
            batch.draw(reg.getTexture(), position.x, position.y, 
                    origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                    rotation, reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(), false, false);
            relX += dimension.x;
        }

        // Draw right edge
        reg = regEdge;
        batch.draw(reg.getTexture(), position.x + relX, position.y + relY,
                origin.x + dimension.x / 8, origin.y, dimension.x / 4, dimension.y, scale.x,  scale.y,
                rotation, reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(), true, false);
    }

}

