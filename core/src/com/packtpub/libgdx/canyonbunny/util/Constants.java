package com.packtpub.libgdx.canyonbunny.util;

import java.util.prefs.Preferences;

public class Constants
{
    // Visible game world is 5 meters wide
    public static final float VIEWPORT_WIDTH = 5.0f;

    // Visible game world is 5 meters tall
    public static final float VIEWPORT_HEIGHT = 5.0f;

    // GUI Width
    public static final float VIEWPORT_GUI_WIDTH = 800.0f;

    // GUI Height
    public static final float VIEWPORT_GUI_HEIGHT = 480.0f;

    // Location of description file for texture atlas
    public static final String TEXTURE_ATLAS_OBJECTS = "../core/assets/images/canyonbunny.atlas";

    // Location of image file for level 01
    public static final String LEVEL_01 = "assets/levels/level-01.png";

    // Amount of extra lives at level start
    public static final int LIVES_START = 3;

    // Duration of feather power-up in seconds
    public static final float ITEM_FEATHER_POWERUP_DURATION = 9;

    // Delay after game over
    public static final float TIME_DELAY_GAME_OVER = 3;
    
    public static final String TEXTURE_ATLAS_UI = "../core/assets/images/canyonbunny-ui.atlas";
    
    public static final String TEXTURE_ATLAS_LIBGDX_UI = "../core/assets/images/uiskin.atlas";
    
    // Location of description file for skins
    public static final String SKIN_LIBGDX_UI = "../core/assets/images/uiskin.json";
    
    public static final String SKIN_CANYONBUNNY_UI = "../core/assets/images/canyonbunny-ui.json";
    
    public static final String PREFERENCES = "/CSC361_F18_G5-core/src/com/packtpub/libgdx/canyonbunny/util/GamePreferences.java";
}
