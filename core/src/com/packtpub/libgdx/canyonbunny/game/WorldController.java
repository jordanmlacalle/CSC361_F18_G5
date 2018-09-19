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
		return false;
	}
	
	/**
	 * Initialize the required resources for the WorldController
	 */
	private void init () {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		lives = Constants.LIVES_START;
		initLevel();
	}
	
	/**
	 * Initialize the current level
	 */
	private void initLevel() {
	    score = 0;
	    level = new Level(Constants.LEVEL_01);
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
		level.update(deltaTime);
		cameraHelper.update(deltaTime);
	}
	
	/**
	 * Handle debug input, allows testing during development. Enables control of
	 * primary (non-gui) camera.
	 * @param deltaTime time passed since the previous frame
	 */
	private void handleDebugInput (float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop) return;
		
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
	
	private void onCollisionBunnyHeadWithRock (Rock rock) {}
    private void onCollisionBunnyWithGoldCoin (GoldCoin goldCoin) {}
    private void onCollisionBunnyWithFeather (Feather feather) {}
    
	private void testCollisions () {
	    r1.set(level.bunnyHead.position.x, level.bunnyHead.position.y, level.bunnyHead.bounds.width, level.bunnyHead.bounds.height);
	    
	    // Test collision: Bunny Head <-> Rocks
	    for (Rock rock : level.rocks) {
	        r2.set(rock.position.x, rock.position.y, rock.bounds.width, rock.bounds.height);
	        
	        if (!r1.overlaps(r2)) continue;
	        onCollisionBunnyHeadWithRock(rock);
	        // IMPORTANT: must do all collisions for valid edge testing on rocks
	    }
	    
	    // Test collision: Bunny Head <-> Gold Coins
	    for (GoldCoin goldCoin : level.goldCoins) {
	        if (goldCoin.collected) continue;
	        r2.set((goldCoin.position.x, goldCoin.position.y, goldCoin.bounds.width, goldCoin.bounds.height);
	        if (!r1.overlaps(r2)) continue;
	        onCollisionBunnyWithGoldCoin(goldCoin);
	        break;
	    }
	    
	    // Test collision: Bunny Head <-> Feathers
	    for (Feather feather : level.feathers) {
	        if (feather.collected) continue;
	        r2.set(feather.position.x, feather.position.y, feather.bounds.width, feather.bounds.height);
	        if (!r1.overlaps(r2)) continue;
	        onCollisionBunnyWithFeather(feather);
	        break;
	    }
	}
}
