package com.edocti.jnext.gof.game;


public abstract class EnemyStrategy {
	protected Enemy enemy;
	
	public EnemyStrategy(Enemy enemy) {
		this.enemy = enemy;
	}
	
	public abstract void update(long elapsed);
}
