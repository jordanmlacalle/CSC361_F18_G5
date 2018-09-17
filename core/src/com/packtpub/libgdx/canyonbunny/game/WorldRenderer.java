package com.packtpub.libgdx.canyonbunny.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.packtpub.libgdx.canyonbunny.util.Constants;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class WorldRenderer implements Disposable {
  private OrthographicCamera camera;
  private SpriteBatch batch;
  private WorldController worldController;

  public WorldRenderer (WorldController worldController) {
	  this.worldController = worldController;
	  init();
  }
  
  private OrthographicCamera cameraGUI;
  
  private void init () { 
	  batch = new SpriteBatch();
	  camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
	  camera.position.set(0, 0, 0);
	  camera.update();
	  
	  cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH,Constants.VIEWPORT_GUI_HEIGHT);
	  cameraGUI.position.set(0, 0, 0);
	  cameraGUI.setToOrtho(true); // flip y-axis
	  cameraGUI.update();
  }

  public void render () {
	  renderWorld(batch);
  }
  
  private void renderWorld (SpriteBatch batch) {
      worldController.cameraHelper.applyTo(camera);
      batch.setProjectionMatrix(camera.combined);
      batch.begin();
      worldController.level.render(batch);
      batch.end();
}
  
  public void resize (int width, int height) { 
	  camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
	  camera.update();
	  
	  cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
      cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float)height) * (float)width;
      cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
      cameraGUI.update();
  }

  @Override public void dispose () {
	  batch.dispose();
  }
  
  private void renderGuiScore (SpriteBatch batch) 
  {
      float x = -15;
      float y = -15;
      batch.draw(Assets.instance.goldCoin.goldCoin,
          x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
      Assets.instance.fonts.defaultBig.draw(batch,
          "" + worldController.score,
          x + 75, y + 37);
    }
  
  private void renderGuiExtraLive (SpriteBatch batch) {
      float x = cameraGUI.viewportWidth - 50 - Constants.LIVES_START * 50;
      float y = -15;
      for (int i = 0; i < Constants.LIVES_START; i++) 
      {
        if (worldController.lives <= i)
          batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
        batch.draw(Assets.instance.bunny.head,
            x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
        batch.setColor(1, 1, 1, 1);
      } 
  }
}