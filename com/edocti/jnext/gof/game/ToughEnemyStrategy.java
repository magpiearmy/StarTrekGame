package com.edocti.jnext.gof.game;

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
			Projectile proj = new Bomb(enemy.getWorld(), enemy,
					enemy.getX()-24, enemy.getY(), 24, 24,
					Side.ENEMY);
			proj.setSpeedX(-90);
			enemy.fire(proj);
			firedBulletLast = false;
		}
		else {
			Projectile proj = new Bullet(enemy.getWorld(), enemy,
										enemy.getX(), enemy.getY(), 15, 8,
										Side.ENEMY);
			proj.setSpeedX(-200);
			enemy.fire(proj);
			firedBulletLast = true;
		}
	}

}
