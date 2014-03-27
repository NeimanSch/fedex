package com.wsp.fedex.screens;


import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.wsp.fedex.fedex;

public class GameScreen implements Screen{
	
	final fedex game;
	
	Texture eship_1;
	Texture pipe_o;
	Texture blaster;
	Sound explosion;
	Music engine;
	OrthographicCamera cam;
	Sprite ship;
	SpriteBatch batch;
	Array<Sprite> fleet;
	Array<Sprite> beams;
	long lastShipTime;
	long lastBeamTime;
	int shipsDestroyed;
	boolean useAccelerometer = true;

	 public GameScreen(final fedex gam) {
	        this.game = gam;

	        // load the images for the enemy ships and the player ship, 64x64 pixels each
	        eship_1 = new Texture(Gdx.files.internal("img/eship_1.png"));
	        pipe_o = new Texture(Gdx.files.internal("img/pipe_o.png"));
	        blaster = new Texture(Gdx.files.internal("img/phaser.png"));

	        // load the explosion sound effect and the ship engine
	        explosion = Gdx.audio.newSound(Gdx.files.internal("sound/blasters.wav"));
	        engine = Gdx.audio.newMusic(Gdx.files.internal("sound/ship_engine.wav"));
	        engine.setLooping(true);

	        // create the camera and the SpriteBatch
	        cam = new OrthographicCamera();
	        cam.setToOrtho(false, 720, 1280);

	        // create a Sprite to logically represent the player's ship
	        batch = new SpriteBatch();
	        ship = new Sprite(pipe_o);
	        ship.setSize(64,64);
	        ship.setPosition(((720/2)-(64/2)), 20);

	        // create the fleet array and spawn the first ship
	        fleet = new Array<Sprite>();
	        spawnEnemyShip();
	        
	        // Start shooting
	        beams = new Array<Sprite>();
	        spawnPhaser();

	    }
	 
	 	private void spawnPhaser() {
	 		Sprite beam = new Sprite(blaster);
	        beam.setPosition(ship.getX(), ship.getY());
	        beam.setSize(32,256);
	        beams.add(beam);
	 		lastBeamTime = TimeUtils.nanoTime();
	 	}

	    private void spawnEnemyShip() {
	        Sprite eShip = new Sprite(eship_1);
	        eShip.setPosition(MathUtils.random(0, 720 - 64),1280);
	        eShip.setSize(64,64);
	        fleet.add(eShip);
	        lastShipTime = TimeUtils.nanoTime();
	    }

	    @Override
	    public void render(float delta) {
	        // clear the screen with a dark blue color. The
	        // arguments to glClearColor are the red, green
	        // blue and alpha component in the range [0,1]
	        // of the color to be used to clear the screen.
	        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	        // tell the camera to update its matrices.
	        cam.update();

	        // tell the SpriteBatch to render in the
	        // coordinate system specified by the camera.
	        game.batch.setProjectionMatrix(cam.combined);
	        game.batch.begin();
	        game.font.draw(game.batch, "Drops Collected: " + shipsDestroyed, 0, 1280);
	        game.batch.end();
	        
	        batch.setProjectionMatrix(cam.combined);
	        batch.begin();
	        ship.draw(batch);
	        for (Sprite beam : beams) {
	        	beam.draw(batch);
	        }
	        for (Sprite eShip : fleet) {
	            eShip.draw(batch);
	        }
	        batch.end();

	        // process user input
	        if (useAccelerometer){
	        	ship.translate(Gdx.input.getAccelerometerX()*-2, Gdx.input.getAccelerometerY()*-2);
	        }
	        if (Gdx.input.isTouched()) {
	            Vector3 touchPos = new Vector3();
	            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	            cam.unproject(touchPos);
	            ship.setPosition(touchPos.x, touchPos.y);
	        }
	        if (Gdx.input.isKeyPressed(Keys.LEFT))
	            ship.translateX(-200 * Gdx.graphics.getDeltaTime());
	        if (Gdx.input.isKeyPressed(Keys.RIGHT))
	        	ship.translateX(200 * Gdx.graphics.getDeltaTime());
	        if (Gdx.input.isKeyPressed(Keys.UP))
	        	ship.translateY(200 * Gdx.graphics.getDeltaTime());
	        if (Gdx.input.isKeyPressed(Keys.DOWN))
	        	ship.translateY(-200 * Gdx.graphics.getDeltaTime());

	        // make sure the player's ship stays within the screen bounds
	        if (ship.getX() < 0)
	            ship.setX(0);
	        if (ship.getX() > 720 - 64)
	            ship.setX(720 - 64);
	        if (ship.getY() < 0)
	            ship.setY(0);
	        if (ship.getY() > 1280 - 64)
	            ship.setY(1280 - 64);
	        
	        // keep shooting
	        if(TimeUtils.nanoTime() - lastBeamTime > (1000000000/2)){
	        	spawnPhaser();
	        }
	        Iterator<Sprite> beam_iter = beams.iterator();
	        while(beam_iter.hasNext()){
	        	Sprite beam = beam_iter.next();
	        	beam.translateY(200 * Gdx.graphics.getDeltaTime());
	        }
	        // check if we need to create a new enemy ship
	        if (TimeUtils.nanoTime() - lastShipTime > 1000000000)
	            spawnEnemyShip();

	        // move the fleet, remove any that are beneath the bottom edge of
	        // the screen or that hit the ship. In the later case we increase the 
	        // value our ships destroyed counter and add a sound effect.
	        Iterator<Sprite> iter = fleet.iterator();
	        while (iter.hasNext()) {
	            Sprite eShip = iter.next();
	            eShip.translateY(-200 * Gdx.graphics.getDeltaTime());
	            if ((eShip.getY() + 64) < 0)
	                iter.remove();
/*	            
	            if (eShip.overlaps(ship)) {
	                shipsDestroyed++;
	                explosion.play();
	                iter.remove();
	        	}
*/	        	
	        }
	    }

	    @Override
	    public void resize(int width, int height) {
	    }

	    @Override
	    public void show() {
	        // start the playback of the ships engine
	        // when the screen is shown
	        engine.play();
	    }

	    @Override
	    public void hide() {
	    }

	    @Override
	    public void pause() {
	    }

	    @Override
	    public void resume() {
	    }

	    @Override
	    public void dispose() {
	        eship_1.dispose();
	        pipe_o.dispose();
	        explosion.dispose();
	        engine.dispose();
	        blaster.dispose();
	        batch.dispose();
	    }


}
