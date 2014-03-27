package com.wsp.fedex.ships;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Ship {
	
	abstract void shoot();
	
	abstract void move();
	
	abstract void dispose();
	
	abstract void draw(SpriteBatch batch);
	
	abstract Rectangle getRectangle();
	
	abstract float getX();
	
	abstract float getY();
	
	abstract boolean checkShipCollision(Rectangle enemy);
}
