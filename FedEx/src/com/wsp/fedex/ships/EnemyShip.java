package com.wsp.fedex.ships;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.wsp.fedex.objects.Beam;

public class EnemyShip extends Ship {
	
	Texture enemyTexture;
	
	private Sprite enemy;
	private int velocityY = -200;
	private int velocityX = 200;
	private Array<Beam> beams = new Array<Beam>();
	
	public EnemyShip() {
		enemyTexture = new Texture(Gdx.files.internal("img/eship_1.png"));
		
		enemy = new Sprite(enemyTexture);
		enemy.setPosition(MathUtils.random(0, 720 - 64),1280);
	    enemy.setSize(64,64);
	}

	@Override
	public void shoot() {
		// TODO Auto-generated method stub
		Beam beam = new Beam(enemy.getX(), enemy.getY(), velocityX, velocityY - 200);
        beams.add(beam);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		enemy.translateY(velocityY * Gdx.graphics.getDeltaTime());
		enemy.translateX(velocityX * Gdx.graphics.getDeltaTime());
		
		if((enemy.getX() > 720 - 64) || (enemy.getX() < 0)) {
			velocityX = -velocityX;
		}
		
		Iterator<Beam> beam_iter = beams.iterator();
	    while(beam_iter.hasNext()){
	    	Beam beam = beam_iter.next();
	    	beam.move();
	    	if((beam.getY()+64)>1280){
	    		beam_iter.remove();
	        }
	    }
	}
	
	public Array<Beam> getBeams() {
		return beams;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
		enemy.draw(batch);
		for (Beam beam : beams) {
        	beam.draw(batch);
        }
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
	public boolean checkShipCollision(Rectangle player) {
		// TODO Auto-generated method stub
		return player.overlaps(enemy.getBoundingRectangle());
	}
	
	@Override
	public boolean checkBeamCollision(Array<Beam> beams) {
		Iterator<Beam> beamIter = beams.iterator();
    	while(beamIter.hasNext()){
        	Beam beam = beamIter.next();
        	if (enemy.getBoundingRectangle().overlaps(beam.getRectangle())) {
        		beamIter.remove();
        		return true;
        	}
        }
    	return false;
	}

}
