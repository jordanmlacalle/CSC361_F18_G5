package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.canyonbunny.game.objects.AbstractGameObject;
import com.packtpub.libgdx.canyonbunny.game.objects.Clouds;
import com.packtpub.libgdx.canyonbunny.game.objects.Mountains;
import com.packtpub.libgdx.canyonbunny.game.objects.Rock;
import com.packtpub.libgdx.canyonbunny.game.objects.WaterOverlay;


public class Level {
    public static final String TAG = Level.class.getName();
    
    public enum BLOCK_TYPE {
        EMPTY(0, 0, 0), // Black
        ROCK(0, 255, 0), // Green
        PLAYER_SPAWNPOINT(255, 255, 255), // White
        ITEM_FEATHER(255, 0, 255), // Purple
        ITEM_GOLD_COIN(255, 255, 0); // Yellow
        
        private int color;
    }

}
