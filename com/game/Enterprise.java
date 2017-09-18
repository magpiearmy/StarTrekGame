package com.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Enterprise extends Entity {
	
	private static final int MAX_HEALTH = 1000;

	private int reloadTime = 100;	//ms
	private int timeSinceFire = 0;	//ms

	private List<Turret> turrets = new ArrayList<>();
	
	public Enterprise(int x, int y, int width, int height) {
		super(x, y, width, height, MAX_HEALTH);
		side = Side.PLAYER;
		turrets.add(new Turret(pos.x+w, pos.y+h/2, 8, 12, 1, this));
	}

	public void move(long delta) {
		super.move(delta);
	}
	
	public void update(long elapsed) {
		super.update(elapsed);
		timeSinceFire += elapsed;
	}
	
	public void fire() {

		if (timeSinceFire >= reloadTime) {

			for (Turret turret : turrets) {
				turret.fire();
			}
			
			timeSinceFire = 0;
		}
	}

	public void draw(Graphics2D g) {
		// sprite.draw(g)
		g.setColor(new Color(90, 230, 90));
		g.fillRect(pos.x, pos.y, w, h);
		g.setColor(Color.BLACK);

		// Draw health bar
		g.setColor(new Color(120, 250, 120));
		int healthBarWidth = (int)(((float)health / (float)MAX_HEALTH) * w);
		g.fillRect(pos.x, pos.y - 8, healthBarWidth, 4);
		g.setColor(Color.GRAY);
		g.drawRect(pos.x, pos.y - 8, w, 4);
	}
}
