package com.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GameKeyListener implements KeyListener {
	private StarTrek starTrek;
	private boolean released = true;

	public GameKeyListener(StarTrek s) {
		starTrek = s;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Enterprise enterprise = (Enterprise)starTrek.getEnterprise();

		if (released) {

			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				enterprise.setSpeedY(-400);
				break;
			case KeyEvent.VK_DOWN:
				enterprise.setSpeedY(400);
				break;
			case KeyEvent.VK_SPACE:
				enterprise.fire();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Entity enterprise = starTrek.getEnterprise();

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
			enterprise.setSpeedY(0);
			released = true;
			break;
		}
	}
}
