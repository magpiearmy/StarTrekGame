package com.game;

import java.awt.*;

public class Bullet extends Projectile {
	
	public Bullet(Entity owner, int x, int y, int width, int height, Side side) {
		super(owner, x, y, width, height, 1, side);
		this.damage = 50;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(new Color(225, 225, 80));
		g.fillOval(pos.x, pos.y, w, h);
	}
}
