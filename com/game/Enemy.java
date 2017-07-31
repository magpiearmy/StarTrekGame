package com.game;

import java.awt.*;
import java.util.List;

public class Enemy extends Entity {
	
	private int startingHealth;
	private EnemyStrategy strategy = new BasicEnemyStrategy(this);

	protected List<Turret> turrets;

	public Enemy(StarTrek ref, int x, int y, int width, int height, int health) {
		super(ref, x, y, width, height, health);
		this.startingHealth = health;
		this.side = Side.ENEMY;
	}
	
	public boolean isDead() {
		return (health <= 0);
	}
	
	public void setStrategy(EnemyStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void update(long elapsed) {
		super.update(elapsed);
		strategy.update(elapsed);

		checkEscape();
	}

	private void checkEscape() {
		if (x + w < 0) {
			world.addEscapedEntity(this);
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, w, h);

		// Draw health bar just above
		g.setColor(Color.GREEN);
		int healthBarWidth = (int)(((float)health / (float)startingHealth) * w);
		g.fillRect(x, y - 8, healthBarWidth, 4);
		g.setColor(Color.GRAY);
		g.drawRect(x, y - 8, w, 4);
		
	}

	public void fire(Projectile projectile) {
		world.addEntity(projectile);
	}
	
	public Point getProjectileSpawnPoint(int projectileWidth, int projectileHeight) {
		return new Point(x - projectileWidth, (y+h/2)-projectileHeight/2);
	}

}
