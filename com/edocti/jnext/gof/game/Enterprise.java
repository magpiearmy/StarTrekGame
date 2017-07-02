package com.edocti.jnext.gof.game;

import java.awt.*;


public class Enterprise extends Entity {
	
	private static final int MAX_HEALTH = 1000;

	private int reloadTime = 100;	//ms
	private int timeSinceFire = 0;	//ms
	
	public Enterprise(StarTrek ref, int x, int y, int width, int height) {
		super(ref, x, y, width, height, MAX_HEALTH);
		side = Side.PLAYER;
	}
	
	public void update(long elapsed) {
		super.update(elapsed);
		timeSinceFire += elapsed;
	}
	
	public void fire() {
		if (timeSinceFire >= reloadTime) {
			Projectile projectile = new Bullet(world, this, x+w, y + h/2, 28, 5, side);
			projectile.setSpeedX(400);
			world.addEntity(projectile);
			
			timeSinceFire = 0;
		}
	}

	public void draw(Graphics2D g) {
		// sprite.draw(g)
		g.setColor(new Color(90, 230, 90));
		g.fillRect(x, y, w, h);
		g.setColor(Color.BLACK);

		// Draw health bar
		g.setColor(new Color(120, 250, 120));
		int healthBarWidth = (int)(((float)health / (float)MAX_HEALTH) * w);
		g.fillRect(x, y - 8, healthBarWidth, 4);
		g.setColor(Color.GRAY);
		g.drawRect(x, y - 8, w, 4);
	}
}
