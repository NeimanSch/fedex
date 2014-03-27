package com.wsp.fedex.ships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class EnemyShip extends Ship {
	
	Texture enemyTexture;
	
	private Sprite enemy;
	
	public EnemyShip() {
		enemyTexture = new Texture(Gdx.files.internal("img/eship_1.png"));
		
		enemy = new Sprite(enemyTexture);
		enemy.setPosition(MathUtils.random(0, 720 - 64),1280);
	    enemy.setSize(64,64);
	}

	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		enemy.translateY(-200 * Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		enemy.draw(batch);
	}

	@Override
	public Rectangle getRectangle() {
		// TODO Auto-generated method stub
		return enemy.getBoundingRectangle();
	}
	
	@Override
	public float getX() {
		return enemy.getX();
	}
	
	@Override
	public float getY() {
		return enemy.getY();
	}

	@Override
	public boolean checkShipCollision(Rectangle enemy) {
		// TODO Auto-generated method stub
		return false;
	}

}
