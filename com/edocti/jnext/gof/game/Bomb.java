package com.edocti.jnext.gof.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Bomb extends Projectile {

	private final static long FLASH_TIME = 200; //ms
	private long timeSinceFlash = 0;
	private boolean flash = true;
	
	public Bomb(StarTrek ref, Entity owner, int x, int y, int width, int height, Side side) {
		super(ref, owner, x, y, width, height, 1, side);
		this.damage = 400;
	}
	
	public void update(long elapsed) {
		super.update(elapsed);
		
		timeSinceFlash += elapsed;
		if (timeSinceFlash >= FLASH_TIME) {
			timeSinceFlash -= FLASH_TIME;
			flash = !flash;
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(new Color(70, 70, 70));
		g.fillOval(x, y, w, h);
		
		g.setColor( flash ? Color.RED : Color.YELLOW );
		g.setStroke(new BasicStroke(2));
		g.drawOval(x, y, w, h);
	}
}
