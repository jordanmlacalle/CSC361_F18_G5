package com.packtpub.libgdx.canyonbunny.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.canyonbunny.game.Assets;
import com.packtpub.libgdx.canyonbunny.util.Constants;

public class BunnyHead extends AbstractGameObject {
	
	public static final String TAG = BunnyHead.class.getName();
	
	private final float JUMP_TIME_MAX = 0.3f;
	private final float JUMP_TIME_MIN = 0.1f;
	private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;
	
	//defined viewing direction
	public enum VIEW_DIRECTION { LEFT, RIGHT }
	
	//states for the bunny
	public enum JUMP_STATE {
		GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING;
	}
	
	private TextureRegion regHead;
	
	public VIEW_DIRECTION viewDirection;
	public float timeJumping;
	public JUMP_STATE jumpState;
	public boolean hasFeatherPowerup;
	public float timeLeftFeatherPowerup;
	
	/**
	 * Creates and initializes a new BunnyHead object.
	 */
	public BunnyHead() {
		init();
	}
	
	/**
	 * Initializes the bunny, and sets up the camera for play.
	 */
	public void init() {
		 dimension.set(1, 1);
	     regHead = Assets.instance.bunny.head;
	     // Center image on game object
	     origin.set(dimension.x / 2, dimension.y / 2);
	     // Bounding box for collision detection
	     bounds.set(0, 0, dimension.x, dimension.y);
	     // Set physics values
	     terminalVelocity.set(3.0f, 4.0f);
	     friction.set(12.0f, 0.0f);
	     acceleration.set(0.0f, -25.0f);
	     // View direction
	     viewDirection = VIEW_DIRECTION.RIGHT;
	     // Jump state
	     jumpState = JUMP_STATE.FALLING;
	     timeJumping = 0;
	     // Power-ups
	     hasFeatherPowerup = false;
	     timeLeftFeatherPowerup = 0;
	}
	
	/**
	 * Causes the bunny to jump
	 * @param jumpKeyPressed boolean which is true if the key to jump is pressed
	 */
	public void setJumping (boolean jumpKeyPressed) {
		
		switch (jumpState) {
	       case GROUNDED: // Character is standing on a platform
	         if (jumpKeyPressed) {
	           // Start counting jump time from the beginning
	           timeJumping = 0;
	           jumpState = JUMP_STATE.JUMP_RISING;
	         }
	         break;
	       case JUMP_RISING: // Rising in the air
	         if (!jumpKeyPressed)
	         jumpState = JUMP_STATE.JUMP_FALLING;
	         break;
	       case FALLING:// Falling down
	       case JUMP_FALLING: // Falling down after jump
	    	   if (jumpKeyPressed && hasFeatherPowerup) {
	               timeJumping = JUMP_TIME_OFFSET_FLYING;
	               jumpState = JUMP_STATE.JUMP_RISING;
	    	   }
	    break; 
	    }
	}
	
	/**
	 * Gives the bunny a power up
	 * @param pickedUp boolean which is true if bunny has picked up a powerup
	 */
	public void setFeatherPowerup (boolean pickedUp) {
		hasFeatherPowerup = pickedUp;
	    if (pickedUp) {
	       timeLeftFeatherPowerup = Constants.ITEM_FEATHER_POWERUP_DURATION;
	    } 
	}
	   
	/**
	 * Getter to tell if the bunny has an active power up
	 * @return boolean which is true if the bunny has an active power up
	 */
	public boolean hasFeatherPowerup () {
	     return hasFeatherPowerup && timeLeftFeatherPowerup > 0;
	}

	
	@Override
	public void update (float deltaTime) {
		super.update(deltaTime);
	    if (velocity.x != 0) {
	    	viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT :VIEW_DIRECTION.RIGHT;
	    }
	    if (timeLeftFeatherPowerup > 0) {
	    	timeLeftFeatherPowerup -= deltaTime;
	        if (timeLeftFeatherPowerup < 0) {
	    	   // disable power-up
	    	   timeLeftFeatherPowerup = 0;
	    	   setFeatherPowerup(false);
	    	 }
	    } 
	} 
	       
}
