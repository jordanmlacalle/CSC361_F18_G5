package com.mygdx.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class WorldController {
	private static final String TAG =
			WorldController.class.getName();
	
	public Sprite[] testSprites;
	public int selectedSprite;
	
	public WorldController() {
		init();
	}
	
	private void init() {
		initTestObjects();
	}
	
	private void initTestObjects() {
		// Create new array for 5 sprites
		testSprites = new Sprite[5];
		// Create empty POT-sized Pixmap with 8 bit RGBA pixel data
		int width = 32;
		int height  = 32;
		Pixmap pixmap = createProceduralPixmap(width, height);
		// Create a new texture from pixmap data
		Texture texture = new Texture(pixmap);
		// Create new sprites using the just created texture
		for (int i = 0; i < testSprites.length; i++) {
			Sprite spr = new Sprite(texture);
			// Define sprite size to be 1m x 1m in game world
			spr.setSize(1,  1);
			// Set origin to sprite's center
			spr.setOrigin(spr.getWidth() / 2.0f, spr.getHeight() / 2.0f);
			// Calculate random position for sprite
			float randomX = MathUtils.random(-2.0f, 2.0f);
			float randomY = MathUtils.random(-2.0f, 2.0f);
			spr.setPosition(randomX, randomY);
			// Put new sprite into array
			testSprites[i] = spr;
		}
		// Set first sprite as selected one
		selectedSprite = 0;
	}
	
	private Pixap createProceduralPixmap (int width, int height) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		// Fill square with red color at 50% opacity
	
	
	public void update (float deltaTime) { }
}
