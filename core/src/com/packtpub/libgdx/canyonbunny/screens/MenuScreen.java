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
import com.packtpub.libgdx.canyonbunny.game.Assets;
import com.packtpub.libgdx.canyonbunny.util.Constants;

//DONE: 247, 248
//TODO 253, 254, 255, 256, 257, 258, 259
public class MenuScreen extends AbstractGameScreen
{
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
        Table.drawDebug(stage);
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
     * Hide the menu screen by disposing memory
     */
    @Override
    public void hide ()
    {
        stage.dispose();
        skinCanyonBunny.dispose();
    }
    
    /**
     * 
     */
    @Override
    public void pause ()
    {
        
    }
    
    /**
     * Rebuilds the stage for the menu screen
     */
    private void rebuildStage ()
    {
        skinCanyonBunny = new Skin(Gdx.files.internal(Constants.SKIN_CANYONBUNNY_UI), new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
        
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
                                            onPlayClicked(;)
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
        Table layer = new Table();
        return layer;
    }
    
    /**
     * The action to be performed when the "play" button on the menu is clicked.
     * Starts the game.
     */
    private void onPlayClicked()
    {
        game.setScreen(new GameScreen(game));
    }
    
    private void onOptionsClicked()
    {
        
    }
}
