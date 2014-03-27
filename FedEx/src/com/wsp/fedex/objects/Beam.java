package com.wsp.fedex.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Beam {
	
	Texture phaser;
	
	private Sprite beam;
	private int velX;
	private int velY;
	
	public Beam(float x, float y, int velocityX, int velocityY) {
		phaser = new Texture(Gdx.files.internal("img/phaser.png"));
		
		velX = velocityX;
		velY = velocityY;
		
		beam = new Sprite(phaser);
		beam.setPosition(x, y);
        beam.setSize(32,256);
	}
	
	public void move() {
		beam.translateY(velY * Gdx.graphics.getDeltaTime());
		beam.translateX(velX * Gdx.graphics.getDeltaTime());
	}
	
	public float getX() {
		return beam.getX();
	}
	
	public float getY() {
		return beam.getY();
	}
	
	public void draw(SpriteBatch batch) {
		beam.draw(batch);
	}
	
	public Rectangle getRectangle() {
		return beam.getBoundingRectangle();
	}

}
