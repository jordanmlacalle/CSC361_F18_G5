package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

public abstract class AbstractGameObject
{
    /**
     * position  - vector representing an objects coordinates in game world
     * dimension - the dimensions of the object
     * origin    - positon of the objects origin
     * scale     - scale of the object
     * rotation  - angle at which the object is currently oriented
     * body      - objects Box2D body
     */
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;
    public Body body;
    
    // objects current speed in m/s
    public Vector2 velocity;
    // objects positive and negative max speed in m/s
    public Vector2 terminalVelocity;
    // opposing force, slows object until velocity=0. If 0, objects velocity will
    // not decrease
    public Vector2 friction;

    // objects constant acceleration in m/s^2
    public Vector2 acceleration;
    // the physical body that will be used for collision detection w other objects
    public Rectangle bounds;
    
    /**
     * stateTime - where we are in the current animation
     * animation - the current animation
     */
    public float stateTime;
    public Animation animation;

    public AbstractGameObject()
    {
        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;

        velocity = new Vector2();
        terminalVelocity = new Vector2(1, 1);
        friction = new Vector2();
        acceleration = new Vector2();
        bounds = new Rectangle();
    }

    /**
     * Updates the object based on the time passed since the last frame
     * 
     * @param deltaTime - the time passed since the last frame
     */
    public void update(float deltaTime)
    {
        stateTime += deltaTime;
        
    	if (body == null)
    	{
    		updateMotionX(deltaTime);
    		updateMotionY(deltaTime);
    		// move to new position
    		position.x += velocity.x * deltaTime;
    		position.y += velocity.y * deltaTime;
    	} 
    	else 
    	{
		   position.set(body.getPosition());
		   rotation = body.getAngle() * MathUtils.radiansToDegrees;
    	}
    }

    public abstract void render(SpriteBatch batch);

    /**
     * Updates the object's X axis motion based on the time passed since the last frame
     * 
     * @param deltaTime - the time passed since the last frame
     */
    protected void updateMotionX(float deltaTime)
    {
        if (velocity.x != 0)
        {
            // Apply friction
            if (velocity.x > 0)
            {
                velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
            } 
            else
            {
                velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
            }
        }
        // Apply acceleration
        velocity.x += acceleration.x * deltaTime;
        // Make sure the object's velocity does not exceed the positive or negative
        // terminal today.
        velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
    }

    /**
     * Updates the object's Y axis motion based on the time passed since the last frame
     * 
     * @param deltaTime - the time passed since the last frame
     */
    public void updateMotionY(float deltaTime)
    {
        if (velocity.y != 0)
        {
            // Apply friction
            if (velocity.y > 0)
            {
                velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
            } 
            else
            {
                velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
            }
        }
        // Apply acceleration
        velocity.y += acceleration.y * deltaTime;
        // Make sure the object's velocity does not exceed the positive
        // or negative terminal velocity.
        velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
    }
    
    /**
     * Sets this object's current animation to the given animation
     * Resets state time to 0.
     * 
     * @param animation - the desired animation
     */
    public void setAnimation(Animation animation)
    {
        this.animation = animation;
        stateTime = 0;
    }
}