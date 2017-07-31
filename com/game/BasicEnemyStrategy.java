package com.game;

public class BasicEnemyStrategy extends EnemyStrategy {

	public BasicEnemyStrategy(Enemy enemy) {
		super(enemy);
		enemy.setSpeedX(-100);
	}

	@Override
	public void update(long elapsed) {
		// Basic enemy does nothing except move...
	}

}
