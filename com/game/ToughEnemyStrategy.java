package com.game;

import java.awt.*;

public class ToughEnemyStrategy extends EnemyStrategy {

	private long reloadTime = 5000; //ms
	private long timeSinceFire = 0;
	private boolean firedBulletLast = false;
	
	public ToughEnemyStrategy(Enemy enemy) {
		super(enemy);
		enemy.setSpeedX(-40);
	}

	@Override
	public void update(long elapsed) {
		timeSinceFire += elapsed;
		if (timeSinceFire >= reloadTime) {
			fire();
			timeSinceFire -= reloadTime;
		}
	}

	private void fire() {
		
		// Alternate between bullets and bombs
		if (firedBulletLast) {
			final int pw = 24;
			final int ph = 24;
			Point projectilePos = enemy.getProjectileSpawnPoint(pw, ph);
			Projectile proj = new Bomb(enemy,
					projectilePos.x, projectilePos.y, pw, ph,
					Side.ENEMY);
			proj.setSpeedX(-90);
		}
		else {
			final int pw = 15;
			final int ph = 8;
			Point projectilePos = enemy.getProjectileSpawnPoint(pw, ph);
			Projectile proj = new Bullet(enemy,
										projectilePos.x, projectilePos.y, pw, ph,
										Side.ENEMY);
			proj.setSpeedX(-200);
		}
	}

	private void fireProjectile(Projectile projectile) {
		enemy.fire(projectile);
		firedBulletLast = !firedBulletLast;
	}

}
