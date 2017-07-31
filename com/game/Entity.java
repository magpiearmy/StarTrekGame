package com.game;

import java.awt.*;

public /*abstract*/ class Entity {
	protected StarTrek world;
	
	protected Sprite sprite;
	protected int x, y, w, h;  // w and h will disappear
	
	protected double moveOverflowX, moveOverflowY;

	/** vertical speed in px/sec */
	protected int speedX;

	/** vertical speed in px/sec */
	protected int speedY;
	
	protected boolean isActive = true;
	protected Side side = Side.NEUTRAL;
	protected int health;

	public Entity(StarTrek ref, int x, int y, int width, int height, int health) {
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
		this.world = ref;
		this.health = health;
	}

	public void destroy() { isActive = false; }
	public boolean isActive() { return isActive; }
	
	public void setSpeedX(int dx) { this.speedX = dx; }
	public void setSpeedY(int dy) { this.speedY = dy; }
	
	protected void move(long delta) {
		
		double dx = (speedX * delta) / 1000f;
		double dy = (speedY * delta) / 1000f;
		
		int moveX = (int)Math.floor(dx + moveOverflowX);
		int moveY = (int)Math.floor(dy + moveOverflowY);
		moveOverflowX += dx - (double)moveX;
		moveOverflowY += dy - (double)moveY;
		
		x += moveX;
		y += moveY;
	}
	
	public void update(long elapsed) {
		move(elapsed);
	}
	
	public void draw(Graphics2D g) {
		// sprite.draw(g)
		g.setColor(Color.WHITE);
		g.fillRect(x, y, w, h);
		g.setColor(Color.BLACK);
	}
	
	public boolean collidesWith(Entity other) {
		Rectangle r1 = new Rectangle(x, y, w, h);
		Rectangle r2 = new Rectangle(other.x, other.y, other.w, other.h);
		return r1.intersects(r2);
	}
	
	public /*abstract*/ void hasCollided(Entity other) {
//		System.out.println("collided");
	}

	public void hit(int damage) {
		health -= damage;
		if (health <= 0) destroy();
	}
	
	public StarTrek getWorld() {
		return world;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
}
