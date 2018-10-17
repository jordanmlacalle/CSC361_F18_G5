package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.packtpub.libgdx.canyonbunny.game.Assets;

public class Rock extends AbstractGameObject
{

    /**
     * regEdge - the texture region to be used for the edges (right and left) of a Rock 
     * regMiddle - the texture region to be used for all sections of a Rock that are not an edge
     * length - length of the rock
     * floatCycleTimeLeft - the time remaining in the current float cycle
     * floatingDownwards - whether or not the Rock is floating down
     * floatTargetPosition - the target position that the Rock is floating towards
     */
    private TextureRegion regEdge;
    private TextureRegion regMiddle;
    private int length;
    private float floatCycleTimeLeft;
    private boolean floatingDownwards;
    private Vector2 floatTargetPosition;
    
    /**
     * Constants
     * FLOAT_CYCLE_TIME - time it takes for a rock to go through 1 float cycle
     * FLOAT_AMPLITUDE - the distance to move in a cycle
     */
    private final float FLOAT_CYCLE_TIME = 2.0f;
    private final float FLOAT_AMPLITUDE = 0.25f;
    
    public Rock()
    {
        init();
    }

    /**
     * Initializes the dimensions, texture regions, and length of the Rock
     */
    private void init()
    {
        dimension.set(1, 1.5f);

        regEdge = Assets.instance.rock.edge;
        regMiddle = Assets.instance.rock.middle;

        // Start length of this rock
        setLength(1);
        
        floatingDownwards = false;
        floatCycleTimeLeft = MathUtils.random(0, FLOAT_CYCLE_TIME / 2);
        floatTargetPosition = null;
    }

    /**
     * Sets the length of the Rock
     * 
     * @param length the desired length of the Rock
     */
    public void setLength(int length)
    {
        this.length = length;

        // Update bounding box for collision detection
        bounds.set(0, 0, dimension.x * length, dimension.y);
    }

    /**
     * Increases length by the given amount
     * 
     * @param amount the value to increase the Rock's length by
     */
    public void increaseLength(int amount)
    {
        setLength(length + amount);
    }

    /**
     * Implementation of the inherited render method (from AbstractGameObject).
     * Renders regEdge and regMiddle texture regions to draw this Rock.
     * 
     * @param batch SpriteBatch used to draw the Rock
     */
    @Override
    public void render(SpriteBatch batch)
    {
        TextureRegion reg = null;

        float relX = 0;
        float relY = 0;

        // Draw left edge
        reg = regEdge;
        relX -= dimension.x / 4;
        batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x, origin.y, dimension.x / 4 + 0.1f,
                dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
                reg.getRegionHeight(), false, false);

        // Draw middle
        relX = 0;
        reg = regMiddle;
        for (int i = 0; i < length; i++)
        {
            batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x, origin.y, dimension.x + 0.1f,
                    dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
                    reg.getRegionHeight(), false, false);
            relX += dimension.x;
        }

        // Draw right edge
        reg = regEdge;
        batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x + dimension.x / 8 + 0.1f, origin.y,
                dimension.x / 4, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(), true, false);
    }
    
    /**
     * Update the Rock object based on the time passed since the last frame.
     * Used to update Rock position as it progresses through a floating cycle.
     * 
     * @param deltaTime the time passed since the last frame
     */
    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        
        // decrement cycle time
        floatCycleTimeLeft -= deltaTime;
        // set target position if it has not been set yet
        if (floatCycleTimeLeft<= 0) {
        	   floatCycleTimeLeft = FLOAT_CYCLE_TIME;
        	   floatingDownwards = !floatingDownwards;
        	   body.setLinearVelocity(0, FLOAT_AMPLITUDE
        	    * (floatingDownwards ? -1 : 1));
        	     } else {
        	   body.setLinearVelocity(body.getLinearVelocity().scl(0.98f));
        	} 
        }
        //below was removed with updates from ch 11
        /*
        if(floatTargetPosition == null)
        {
            floatTargetPosition = new Vector2(position);
        }
        
        // check if a cycle has been completed
        if(floatCycleTimeLeft <= 0)
        {
            //cycle complete, so begin new cycle
            floatCycleTimeLeft = FLOAT_CYCLE_TIME;
            //change direction
            floatingDownwards = ! floatingDownwards;
            //set target position based on new direction and amplitude
            floatTargetPosition.y += FLOAT_AMPLITUDE * (floatingDownwards ? -1 : 1);
        }
        
        position.lerp(floatTargetPosition, deltaTime);
    }
    */

}
