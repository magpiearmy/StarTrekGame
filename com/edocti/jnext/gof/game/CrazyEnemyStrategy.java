package com.edocti.jnext.gof.game;

public class CrazyEnemyStrategy extends EnemyStrategy {

	public CrazyEnemyStrategy(Enemy enemy) {
		super(enemy);
		enemy.setSpeedX(-40);
		enemy.setSpeedY(80);
	}

	@Override
	public void update(long elapsed) {
		if (enemy.getY() <= 20) {
			enemy.setSpeedY(80);
		} else if (enemy.getY() >= enemy.getWorld().getHeight() - 50) {
			enemy.setSpeedY(-80);
		}
	}

}
