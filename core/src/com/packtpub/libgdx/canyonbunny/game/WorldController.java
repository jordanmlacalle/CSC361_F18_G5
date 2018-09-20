package com.packtpub.libgdx.canyonbunny.game;

import com.packtpub.libgdx.canyonbunny.util.CameraHelper;
import com.packtpub.libgdx.canyonbunny.game.objects.BunnyHead;
import com.packtpub.libgdx.canyonbunny.game.objects.BunnyHead.JUMP_STATE;
import com.packtpub.libgdx.canyonbunny.game.objects.Feather;
import com.packtpub.libgdx.canyonbunny.game.objects.GoldCoin;
import com.packtpub.libgdx.canyonbunny.game.objects.Rock;
import com.packtpub.libgdx.canyonbunny.util.Constants;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Array;



public class WorldController extends InputAdapter {
	private static final String TAG =
			WorldController.class.getName();
	
	/**
	 * level - the current level
	 * lives - the number of lives remaining
	 * score - the current score
	 */
	public Level level;
	public int lives;
	public int score;
	
	public CameraHelper cameraHelper;
	
	/**
	 * timeleftGameOverDelay - the time remaining for the Game Over message 
	 */
	private float timeLeftGameOverDelay;
	/**
	 * Rectangles for collision detection
	 */
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();
	
	public WorldController () {
		init();
	}
	
	@Override
	public boolean keyUp (int keycode ) {
		// Reset game world
		if (keycode == Keys.R) {
			init();
			Gdx.app.debug(TAG,  "Game world resetted");
		}
		// Toggle camera follow
		else if (keycode == Keys.ENTER) {
		    cameraHelper.setTarget(cameraHelper.hasTarget() ? null : level.bunnyHead);
		    Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());;
		}
		return false;
	}
	
	/**
	 * Initialize the required resources for the WorldController
	 */
	private void init () {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		lives = Constants.LIVES_START;
		timeLeftGameOverDelay = 0;
		initLevel();
	}
	
	/**
	 * Initialize the current level
	 */
	private void initLevel() {
	    score = 0;
	    level = new Level(Constants.LEVEL_01);
	    cameraHelper.setTarget(level.bunnyHead);
	}
	
	/**
	 * Creates a rectangle with an X in the center. Used for debugging. 
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 * @return Returns a the created pixmap
	 */
 	private Pixmap createProceduralPixmap (int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		// Fill square with red color at 50% opacity
		pixmap.setColor(1, 0, 0, 0.5f);
		pixmap.fill();
		// Draw a yellow-colored X shape on square
		pixmap.setColor(1, 1, 0, 1);
		pixmap.drawLine(0,  0,  width,  height);
		pixmap.drawLine(width, 0, 0, height);
		// Draw a cyan-colored border around square
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0,  0,  width,  height);
		return pixmap;
	}
	
 	/**
 	 * Update the world
 	 * @param deltaTime
 	 */
	public void update (float deltaTime) {
		handleDebugInput(deltaTime);
		// Check for game over
		if (isGameOver()) {
		    // Decrement Game Over message time by deltaTime
		    timeLeftGameOverDelay -= deltaTime;
		    // If the message is done displaying, reset 
		    if (timeLeftGameOverDelay < 0) init();
		// else, if the game is not over
		} else {
		    //handle player input
		    handleInputGame(deltaTime);
		}
		//update the level
		level.update(deltaTime);
		testCollisions();
		cameraHelper.update(deltaTime);
		//if the game is not over and the player is in the water..
		if (!isGameOver() && isPlayerInWater()) {
		    //decrement player lives remaining
		    lives--;
		    //after decrementing lives, check if game is over (out of lives)
		    if (isGameOver())
		        //set game over message delay time
		        timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
		    else
		        //reset the level
		        initLevel();
		}
	}
	
	/**
	 * Check if the player has run out of lives
	 * @return true if lives < 0, false otherwise
	 */
	public boolean isGameOver () {
	    return lives < 0;
	}
	
	/**
	 * Check if the player character is in the water (results in life lost)
	 * @return true if the player is in the water, false otherwise
	 */
	public boolean isPlayerInWater () {
	    return level.bunnyHead.position.y < -5;
	}
	/**
	 * Handles user input for the player character (Bunny Head).
	 * User input is only handled if the camera target is the Bunny Head.
	 * 
	 * @param deltaTime The time that has passed since the last frame 
	 */
	private void handleInputGame (float deltaTime) {
	    if (cameraHelper.hasTarget(level.bunnyHead)) {
	        // Player movement
	        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
	            level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
	        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
	            level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
	        } else {
	            // Execute auto-forward movement on non-desktop platform
	            if (Gdx.app.getType() != ApplicationType.Desktop) {
	                level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
	            }
	        }
	        
	        // Bunny Jump
	        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) {
	            level.bunnyHead.setJumping(true);
	        } else {
	            level.bunnyHead.setJumping(false);
	        }
	    }
	}
	
	/**
	 * Handle debug input, allows testing during development. Enables control of
	 * primary (non-gui) camera.
	 * @param deltaTime time passed since the previous frame
	 */
	private void handleDebugInput (float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) return;
		
		if (!cameraHelper.hasTarget(level.bunnyHead)) {
    		// Camera Controls (move)
    		float camMoveSpeed = 5 * deltaTime;
    		float camMoveSpeedAccelerationFactor = 5;
    		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
    			camMoveSpeed *= camMoveSpeedAccelerationFactor;
    		if (Gdx.input.isKeyPressed(Keys.LEFT))
    			moveCamera(-camMoveSpeed, 0);
    		if (Gdx.input.isKeyPressed(Keys.RIGHT))
    			moveCamera(camMoveSpeed, 0);
    		if (Gdx.input.isKeyPressed(Keys.UP))
    			moveCamera(0, camMoveSpeed);
    		if (Gdx.input.isKeyPressed(Keys.DOWN))
    			moveCamera(0, -camMoveSpeed);
    		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
    			cameraHelper.setPosition(0, 0);
		}
		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
				camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD))
			cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH))
			cameraHelper.setZoom(1);
	}
	
	/**
	 * Move the camera to the coordinates specified by (x, y)
	 * @param x
	 * @param y
	 */
	private void moveCamera (float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}
	
	/**
	 * Defines how the collision between the BunnyHead with a Rock should be handled.
	 * @param rock The Rock that the BunnyHead has collided with
	 */
	private void onCollisionBunnyHeadWithRock (Rock rock) {
	    // Set bunnyHead to the current level's BunnyHead 
	    BunnyHead bunnyHead = level.bunnyHead;
	    float heightDifference = Math.abs(bunnyHead.position.y - ( rock.position.y + rock.bounds.height));
	    if (heightDifference > 0.25f) {
	        boolean hitRightEdge = bunnyHead.position.x > ( rock.position.x + rock.bounds.width / 2.0f);
	        if (hitRightEdge) {
	            bunnyHead.position.x = rock.position.x + rock.bounds.width;
	        }
	        else {
	            bunnyHead.position.x = rock.position.x - bunnyHead.bounds.width;
	        }
	        return;
	    }
	    
	    switch (bunnyHead.jumpState) {
	    case GROUNDED:
	        break;
	    case FALLING:
	    case JUMP_FALLING:
	        bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height + bunnyHead.origin.y;
	        bunnyHead.jumpState = JUMP_STATE.GROUNDED;
	        break;
	    case JUMP_RISING:
	        bunnyHead.position.y = rock.position.y + bunnyHead.bounds.height + bunnyHead.origin.y;
	        break;
	    }
	}
	
	/**
	 * Defines how the collision between the BunnyHead and a GoldCoin is handled.
	 * The collected state of the GoldCoin is set to true and the score value 
	 * of the GoldCoin is added to the player's score.
	 * 
	 * @param goldCoin The GoldCoin that the BunnyHead has collided with
	 */
    private void onCollisionBunnyWithGoldCoin (GoldCoin goldCoin) {
        goldCoin.collected = true;
        score += goldCoin.getScore();
        Gdx.app.log(TAG, "Gold coin collected");
    }
    
    /**
     * Defines how the collision between the BunnyHead and a Feather is handled. 
     * The collected state of the Feather is set to true and the score value
     * of the Feather is added to the player's score. The feather powerup is 
     * applied to the BunnyHead.
     * 
     * @param feather The Feather that the BunnyHead has collided with
     */
    private void onCollisionBunnyWithFeather (Feather feather) {
        feather.collected = true;
        score += feather.getScore();
        level.bunnyHead.setFeatherPowerup(true);
        Gdx.app.log(TAG, "Feather collected");
    }
    
    /**
     * Check for collisions between BunnyHead and other level objects
     */
	private void testCollisions () {
	    r1.set(level.bunnyHead.position.x, level.bunnyHead.position.y, level.bunnyHead.bounds.width, level.bunnyHead.bounds.height);
	    
	    // Test collision: Bunny Head <-> Rocks
	    for (Rock rock : level.rocks) {
	        r2.set(rock.position.x, rock.position.y, rock.bounds.width, rock.bounds.height);
	        
	        if (!r1.overlaps(r2)) continue; // if BunnyHead is not colliding with current Rock, continue to next iteration
	        onCollisionBunnyHeadWithRock(rock);
	        // IMPORTANT: must do all collisions for valid edge testing on rocks
	    }
	    
	    // Test collision: Bunny Head <-> Gold Coins
	    for (GoldCoin goldCoin : level.goldcoins) {
	        if (goldCoin.collected) continue; // ignore GoldCoin that has already been collected
	        r2.set(goldCoin.position.x, goldCoin.position.y, goldCoin.bounds.width, goldCoin.bounds.height);
	        if (!r1.overlaps(r2)) continue; // if BunnyHead is not colliding with current GoldCoin, continue to next iteration
	        onCollisionBunnyWithGoldCoin(goldCoin);
	        break;
	    }
	    
	    // Test collision: Bunny Head <-> Feathers
	    for (Feather feather : level.feathers) {
	        if (feather.collected) continue; // ignore Feather that has already been collected
	        r2.set(feather.position.x, feather.position.y, feather.bounds.width, feather.bounds.height);
	        if (!r1.overlaps(r2)) continue; // if BunnyHead is not colliding with current Feather, continue to next iteration
	        onCollisionBunnyWithFeather(feather);
	        break;
	    }
	}
}
