package com.wsp.fedex.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PlayerShip extends Ship {

	Texture pipe;
	
	private Sprite player;
	private int health = 100;
	private boolean useAccelerometer = true;
	
	public PlayerShip() {
		pipe   = new Texture(Gdx.files.internal("img/pipe_o.png"));
		
		player = new Sprite(pipe);
        player.setSize(64,64);
        player.setPosition(((720/2)-(64/2)), 20);
	}
	
	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		 // process user input
        if (useAccelerometer){
        	player.translate(Gdx.input.getAccelerometerX()*-2, Gdx.input.getAccelerometerY()*-2);
        }
        
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            player.translateX(-200 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
        	player.translateX(200 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.UP))
        	player.translateY(200 * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.DOWN))
        	player.translateY(-200 * Gdx.graphics.getDeltaTime());

        // make sure the player's ship stays within the screen bounds
        if (player.getX() < 0)
            player.setX(0);
        if (player.getX() > 720 - 64)
            player.setX(720 - 64);
        if (player.getY() < 0)
            player.setY(0);
        if (player.getY() > 1280 - 64)
            player.setY(1280 - 64);
		
	}
	
	public void loseHealth() {
		health -= 10;
		Gdx.app.log("health",  Integer.toString(health));
	}
	
	public boolean checkShipCollision(Rectangle enemy) {
		return enemy.overlaps(player.getBoundingRectangle());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		player.draw(batch);
	}
	
	@Override
	public float getX() {
		return player.getX();
	}
	
	@Override
	public float getY() {
		return player.getY();
	}

	@Override
	Rectangle getRectangle() {
		// TODO Auto-generated method stub
		return player.getBoundingRectangle();
	}

}
