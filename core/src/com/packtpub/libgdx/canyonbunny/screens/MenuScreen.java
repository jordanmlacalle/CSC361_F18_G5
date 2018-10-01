package com.packtpub.libgdx.canyonbunny.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.packtpub.libgdx.canyonbunny.game.Assets;
import com.packtpub.libgdx.canyonbunny.util.Constants;
import com.packtpub.libgdx.canyonbunny.util.CharacterSkin;
import com.packtpub.libgdx.canyonbunny.util.GamePreferences;

public class MenuScreen extends AbstractGameScreen
{
    private Skin skinLibgdx;
    
    private static final String TAG = MenuScreen.class.getName();
    
    /**
     * stage - stage for CanyonBunny game screen
     * skinCanyonBunny - skin for CanyonBunny game screen UI
     */
    private Stage stage;
    private Skin skinCanyonBunny;
    
    // Menu
    private Image imgBackground;
    private Image imgLogo;
    private Image imgInfo;
    private Image imgCoins;
    private Image imgBunny;
    private Button btnMenuPlay;
    private Button btnMenuOptions;
    
    // Options
    private Window winOptions;
    private TextButton btnWinOptSave;
    private TextButton btnWinOptCancel;
    private CheckBox chkSound;
    private Slider sldSound;
    private CheckBox chkMusic;
    private Slider sldMusic;
    private SelectBox<CharacterSkin> selCharSkin;
    private Image imgCharSkin;
    private CheckBox chkShowFpsCounter;
    
    // Debug
    private final float DEBUG_REBUILD_INTERVAL = 5.0f;
    private boolean debugEnabled = false;
    private float debugRebuildStage;
    
    public MenuScreen (Game game)
    {
        super(game);
    }
    
    /**
     * Renders the gamescreen based on time passed since last frame
     * @param deltaTime the time passed since the last frame
     */
    @Override
    public void render (float deltaTime)
    {
        // OpenGL clear color -> black
        Gdx.gl.glClearColor(0.0f,  0.0f,  0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (debugEnabled)
        {
            debugRebuildStage -= deltaTime;
            
            if (debugRebuildStage <= 0)
            {
                debugRebuildStage = DEBUG_REBUILD_INTERVAL;
                rebuildStage();
            }
        }
        
        stage.act(deltaTime);
        stage.draw();
        stage.setDebugAll(true);
    }
    
    /**
     * Resize the menu screen
     * 
     * @param width The desired width after resizing
     * @param height The desired height after resizing
     */
    @Override
    public void resize (int width, int height)
    {
        stage.getViewport().update(width, height, true);
    }
    
    /**
     * Display the menu screen
     */
    @Override
    public void show ()
    {
        stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        rebuildStage();
    }
    
    /**
     * Hides menu by disposing objects.
     */
    @Override
    public void hide()
    {
        stage.dispose();
        skinCanyonBunny.dispose();
        skinLibgdx.dispose();
    }
    
    /**
     * 
     */
    @Override
    public void pause ()
    {
        
    }
    
    /**
     * Rebuilds the stage for the menu screen, reloading skin assets
     */
    private void rebuildStage ()
    {
        skinCanyonBunny = new Skin(Gdx.files.internal(Constants.SKIN_CANYONBUNNY_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
        skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));

        // Build all layers
        Table layerBackground = buildBackgroundLayer();
        Table layerObjects = buildObjectsLayer();
        Table layerLogos = buildLogosLayer();
        Table layerControls = buildControlsLayer();
        Table layerOptionsWindow = buildOptionsWindowLayer();
        
        // Assemble stage for menu screen
        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        stack.add(layerBackground);
        stack.add(layerObjects);
        stack.add(layerLogos);
        stack.add(layerControls);
        stack.addActor(layerOptionsWindow);
    }
    
    /**
     * Builds the background layer using the background image
     * from the skinCanyonBunny Skin.
     * 
     * @return The built background layer
     */
    private Table buildBackgroundLayer ()
    {
        Table layer = new Table();
        // Add background
        imgBackground = new Image(skinCanyonBunny, "background");
        layer.add(imgBackground);
        return layer;
    }
    
    /**
     * Builds the objects layer using the coins and bunny
     * images from the skinCanyonBunny Skin.
     * 
     * @return The built objects layer
     */
    private Table buildObjectsLayer ()
    {
        Table layer = new Table();
        // Add coins image
        imgCoins = new Image(skinCanyonBunny, "coins");
        layer.addActor(imgCoins);
        imgCoins.setPosition(135, 80);
        // Add bunny image
        imgBunny = new Image(skinCanyonBunny, "bunny");
        layer.addActor(imgBunny);
        imgBunny.setPosition(355, 40);
        return layer;
    }
    
    /**
     * Builds the logos layer containing logos for CanyonBunny and 
     * information such as tools used (libgdx) using images from the
     * skinCanyonBunny skin.
     * 
     * @return The built logos layer
     */
    private Table buildLogosLayer ()
    {
        Table layer= new Table();
        layer.left().top();
        // Add Game Logo
        imgLogo = new Image(skinCanyonBunny, "logo");
        layer.add(imgLogo);
        layer.row().expandY();
        // Add Info Logos
        imgInfo = new Image(skinCanyonBunny, "info");
        layer.add(imgInfo).bottom();
        
        if(debugEnabled)
        {
            layer.debug();
        }
        
        return layer;
    }
    
    /**
     * Builds the controls layer which adds UI elements which allow the
     * player to start playing by clicking a button.
     * 
     * @return The built controls layer
     */
    private Table buildControlsLayer ()
    {
        Table layer = new Table();
        layer.right().bottom();
        // Add Play Button
        btnMenuPlay = new Button(skinCanyonBunny, "play");
        layer.add(btnMenuPlay);
        btnMenuPlay.addListener(new ChangeListener()
                                {
                                    @Override
                                       public void changed(ChangeEvent event, Actor actor)
                                       {
                                            onPlayClicked();
                                       }
                                });
        if(debugEnabled)
        {
            layer.debug();
        }
        
        return layer;
    }
    
    private Table buildOptionsWindowLayer ()
    {
        winOptions = new Window("Options", skinLibgdx);
        // Add Audio Settings: Sound/Music Checkbox and Volume Slider
        winOptions.add(buildOptWinAudioSettings()).row();
        // Add Character Skin: Selection Box (White, Gray, Brown)
        winOptions.add(buildOptWinSkinSelection()).row();
        // Add Debug: Show FPS Counter
        winOptions.add(buildOptWinDebug()).row();
        // Add separator and buttons (Save, Cancel)
        winOptions.add(buildOptWinButtons()).pad(10, 0, 10, 0);
        
        // Make options window slightly transparent
        winOptions.setColor(1, 1, 1, 0.8f);
        // Hide options window by default
        winOptions.setVisible(false);
        
        if(debugEnabled)
        {
            winOptions.debug();
        }
        //Let TableLayout recalculate widget sizes and positions
        winOptions.pack();
        // Move options window to bottom right corner
        winOptions.setPosition(Constants.VIEWPORT_GUI_WIDTH - winOptions.getWidth() - 50, 50);
        Table layer = new Table();
        
        return winOptions;
    }
    
    /**
     * Builds audio options 
     * 
     * @return Built audio options table
     */
    private Table buildOptWinAudioSettings()
    {
        Table tbl = new Table();
        // Add Title: "Audio"
        tbl.pad(10, 10, 0, 10);
        tbl.add(new Label("Audio", skinLibgdx, "default-font", Color.ORANGE)).colspan(3);
        tbl.row();
        tbl.columnDefaults(0).padRight(10);
        tbl.columnDefaults(1).padRight(10);
        // Add Checkbox, "Sound" label, sound volume slider
        chkSound = new CheckBox("", skinLibgdx);
        tbl.add(chkSound);
        tbl.add(new Label("Sound", skinLibgdx));
        sldSound = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
        tbl.add(sldSound);
        tbl.row();
        //Add Checkbox, "Music" label, music volume slider
        chkMusic = new CheckBox("", skinLibgdx);
        tbl.add(chkMusic);
        tbl.add(new Label("Music", skinLibgdx));
        sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
        tbl.add(sldMusic);
        tbl.row();
        return tbl;
    }
    
    /**
     * Builds table that contains character skin selection option
     * via a drop-down menu.
     * 
     * @return The built character skin selection options table
     */
    private Table buildOptWinSkinSelection()
    {
        Table tbl = new Table();
        // Add Title: "Character Skin"
        tbl.pad(10, 10, 0, 10);
        tbl.add(new Label("Character Skin", skinLibgdx, "default-font", Color.ORANGE)).colspan(2);
        tbl.row();
        // Add Drop down box filled with skin items
        selCharSkin = new SelectBox<CharacterSkin>(skinLibgdx);
        
        selCharSkin.setItems(CharacterSkin.values());
        
        selCharSkin.addListener(new ChangeListener()
                                {
                                    @Override
                                    public void changed(ChangeEvent event, Actor actor)
                                    {
                                        onCharSkinSelected(((SelectBox<CharacterSkin>)actor).getSelectedIndex());
                                    }
                                });
        tbl.add(selCharSkin).width(120).padRight(20);
        // Add skin preview image
        imgCharSkin = new Image(Assets.instance.bunny.head);
        tbl.add(imgCharSkin).width(50).height(50);
        return tbl;
    }
    
    /**
     * Builds table containing debug settings
     * 
     * @return The built debug settings table
     */
    private Table buildOptWinDebug()
    {
        Table tbl = new Table();
        // Add title: "Debug"
        tbl.pad(10, 10, 0, 10);
        tbl.add(new Label("Debug", skinLibgdx, "default-font", Color.RED)).colspan(3);
        tbl.row();
        tbl.columnDefaults(0).padRight(10);
        tbl.columnDefaults(1).padRight(10);
        // Add checkbox, "Show FPS Counter" label
        chkShowFpsCounter = new CheckBox("", skinLibgdx);
        tbl.add(new Label("Show FPS Counter", skinLibgdx));
        tbl.add(chkShowFpsCounter);
        tbl.row();
        return tbl;
    }
    
    /**
     * Builds table that contains Save and Cancel buttons at
     * the bottom of the Options window. 
     * 
     * @return Built options window buttons table
     */
    private Table buildOptWinButtons()
    {
        Table tbl = new Table();
        // Add separator
        Label lbl = null;
        lbl = new Label("", skinLibgdx);
        lbl.setColor(0.75f, 0.75f, 0.75f, 1);
        lbl.setStyle(new LabelStyle(lbl.getStyle()));
        lbl.getStyle().background = skinLibgdx.newDrawable("white");
        tbl.add(lbl).colspan(2).height(1).width(220).pad(0, 1, 5, 0);
        tbl.row();
        // Add save button with event handler
        btnWinOptSave = new TextButton("Save", skinLibgdx);
        tbl.add(btnWinOptSave).padRight(30);
        btnWinOptSave.addListener(new ChangeListener()
                                 {
                                     @Override
                                     public void changed(ChangeEvent event, Actor actor)
                                     {
                                         onSaveClicked();
                                     }
                                    
                                  });
        // Add cancel button with event handler
        btnWinOptCancel = new TextButton("Cancel", skinLibgdx);
        tbl.add(btnWinOptCancel);
        btnWinOptCancel.addListener(new ChangeListener()
                                    {
                                        @Override
                                        public void changed(ChangeEvent event, Actor actor)
                                        {
                                            onCancelClicked();
                                        }
                                    });
        return tbl;
    }
    /**
     * The action to be performed when the "play" button on the menu is clicked.
     * Starts the game.
     */
    private void onPlayClicked()
    {
        game.setScreen(new GameScreen(game));
    }
    
    /**
     * Actions to be performed when options button clicked on game menu.
     * Opens the Options window.
     */
    private void onOptionsClicked()
    {
        loadSettings();
        btnMenuPlay.setVisible(false);
        btnMenuOptions.setVisible(false);
        winOptions.setVisible(true);
    }
    
    /**
     * Loads saved settings.
     */
    private void loadSettings()
    {
        GamePreferences prefs = GamePreferences.instance;
        prefs.load();
        chkSound.setChecked(prefs.sound);
        sldSound.setValue(prefs.volSound);
        chkMusic.setChecked(prefs.music);
        sldMusic.setValue(prefs.volMusic);
        selCharSkin.setSelectedIndex(prefs.charSkin);
        onCharSkinSelected(prefs.charSkin);
        chkShowFpsCounter.setChecked(prefs.showFpsCounter);
        
    }
    
    /**
     * Saves settings.
     */
    private void saveSettings()
    {
        GamePreferences prefs = GamePreferences.instance;
        prefs.sound = chkSound.isChecked();
        prefs.volSound = sldSound.getValue();
        prefs.music = chkMusic.isChecked();
        prefs.volMusic = sldMusic.getValue();
        prefs.charSkin = selCharSkin.getSelectedIndex();
        prefs.showFpsCounter = chkShowFpsCounter.isChecked();
        prefs.save();
    }
    
    /**
     * Updates preview image
     * 
     * @param index
     */
    private void onCharSkinSelected (int index)
    {
        CharacterSkin skin = CharacterSkin.values() [index];
        imgCharSkin.setColor(skin.getColor());
    }
    
    /**
     * Saves the current settings of the Options window and swaps the Options
     * window for the menu controls
     */
    private void onSaveClicked()
    {
        saveSettings();
        onCancelClicked();
    }
    
    /**
     * Swaps the Options and Menu widgets, discarded any changes made to settings.
     */
    private void onCancelClicked()
    {
        btnMenuPlay.setVisible(true);
        btnMenuOptions.setVisible(true);
        winOptions.setVisible(false);
    }
}
