package com.edocti.jnext.gof.game;


public class EnemyFactory {
	
	public enum EnemyType { WEAK_ENEMY, TOUGH_ENEMY, CRAZY_ENEMY, LAZY_ENEMY };
	
	private StarTrek starTrek;

	public EnemyFactory(StarTrek starTrek) {
		this.starTrek = starTrek;
	}
	
	public Enemy createEnemy(EnemyType type, int x, int y) {
		
		Enemy enemy = null;
		switch(type) {
		case WEAK_ENEMY: {
			enemy = new Enemy(starTrek, x, y, 40, 50, 50);
			break;
		}
		case TOUGH_ENEMY: {
			enemy = new Enemy(starTrek, x, y, 90, 120, 100);
			enemy.setStrategy(new ToughEnemyStrategy(enemy));
			break;
		}
		case CRAZY_ENEMY: {
			enemy = new Enemy(starTrek, x, y, 50, 80, 50);
			enemy.setStrategy(new CrazyEnemyStrategy(enemy));
			break;
		}
		case LAZY_ENEMY: {
			enemy = new Enemy(starTrek, x, y, 60, 50, 50); //TODO: Add different parameters later
			break;
		}
		}
		return enemy;
	}
	
}
