package com.game;

import java.awt.*;

public /*abstract*/ class Entity {
//    protected int x, y;
    protected int w, h;  // w and h will disappear
    protected Position pos;

	protected StarTrek world;
	
	protected Sprite sprite;
	
	protected double moveOverflowX, moveOverflowY;

	/** vertical speed in px/sec */
	protected int speedX;

	/** vertical speed in px/sec */
	protected int speedY;
	
	protected boolean isActive = true;
	protected Side side = Side.NEUTRAL;
	protected int health;

	public Entity(int x, int y, int width, int height, int health) {
		this.world = StarTrek.getInstance();
		this.w = width;
		this.h = height;
		this.pos = new Position(x, y);
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
		
		pos.move(moveX, moveY);
	}
	
	public void update(long elapsed) {
		move(elapsed);
	}
	
	public void draw(Graphics2D g) {
		// sprite.draw(g)
		g.setColor(Color.WHITE);
		g.fillRect(pos.x, pos.y, w, h);
		g.setColor(Color.BLACK);
	}
	
	public boolean collidesWith(Entity other) {
		Rectangle r1 = new Rectangle(pos.x, pos.y, w, h);
		Rectangle r2 = new Rectangle(other.pos.x, other.pos.y, other.w, other.h);
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
		return pos.x;
	}

	public int getY() {
		return pos.y;
	}

	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}

	protected Position getEdgePlacementPosition() {
        return new Position(1, 2);
    }
}
