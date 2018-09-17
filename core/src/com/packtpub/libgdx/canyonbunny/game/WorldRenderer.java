package com.packtpub.libgdx.canyonbunny.game;

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
  
  private void init () { 
	  batch = new SpriteBatch();
	  camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
	  camera.position.set(0, 0, 0);
	  camera.update();
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
  }

  @Override public void dispose () {
	  batch.dispose();
  }
}