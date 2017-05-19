package com.edocti.jnext.gof.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.edocti.jnext.gof.game.EnemyFactory.EnemyType;

class MyKeyListener implements KeyListener {
	private StarTrek starTrek;
	private boolean released = true;
	
	public MyKeyListener(StarTrek s) {
		starTrek = s;
	}
	
	@Override public void keyTyped(KeyEvent e) {
	}

	@Override public void keyPressed(KeyEvent e) {
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

	@Override public void keyReleased(KeyEvent e) {
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

public class StarTrek extends Canvas {
	private static final long serialVersionUID = 1033091750611217336L;

	/** This strategy allows the use of accelerated page flip */
	private BufferStrategy strategy;
	private volatile boolean gameRunning;
	private List<Entity> entities;
	private List<Entity> newEntities;
	private List<Entity> escapedEntities;
	private int lives = 3;

	public StarTrek() {
		JFrame container = new JFrame("Star Trek");
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(1024, 768));
		panel.setLayout(null);
		
		this.setBounds(0, 0, 1024, 768);
		panel.add(this);
		
		this.setIgnoreRepaint(true);
		
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.requestFocus();
		this.addKeyListener(new MyKeyListener(this));
		this.createBufferStrategy(2);
		this.strategy = this.getBufferStrategy();
	}
	
	private void initEntities() {
		entities = new ArrayList<>();
		newEntities = new ArrayList<>();
		escapedEntities = new ArrayList<>();
		
		// Create our Enterprise ship and add to the collection
		Entity enterprise = new Enterprise(this, 20, 300, 80, 120);
		entities.add(enterprise);
		
		// Create some enemies
		EnemyFactory factory = new EnemyFactory(this);
		entities.add(factory.createEnemy(EnemyType.WEAK_ENEMY, 1024, 350));
		entities.add(factory.createEnemy(EnemyType.WEAK_ENEMY, 1024, 100));
		entities.add(factory.createEnemy(EnemyType.WEAK_ENEMY, 1024, 600));
		entities.add(factory.createEnemy(EnemyType.TOUGH_ENEMY, 1224, 300));
		entities.add(factory.createEnemy(EnemyType.CRAZY_ENEMY, 1524, 500));
	}
	
	public void addEntity(Entity entity) {
		newEntities.add(entity);
	}

	public void addEscapedEntity(Entity entity) {
		escapedEntities.add(entity);
	}
	
	public Entity getEnterprise() {
		assert(entities.size() > 0);
		return entities.get(0);
	}
	
	public void startGame() {
		initEntities();
		gameRunning = true;
	}
	
	public void gameLoop() {
		long currentTime;
		long prevTime = System.nanoTime();
		
		while (gameRunning) {
			
			currentTime = System.nanoTime();					// nano-precision
			long elapsed = (currentTime - prevTime) / 1000000;	// milli-precision
			
			if (elapsed >= 10) // Update every 10ms
			{
				Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, 1024, 768);
				
				// Remove any destroyed entities
				for (int i = 0; i < entities.size(); ) {
					if (!entities.get(i).isActive())
						entities.remove(i);
					else
						i++;
				}
				
				for (Entity e : newEntities) {
					entities.add(e);
				}
				newEntities.clear();
				
				// Update entities...
				for (Entity e : entities) {
					e.update(elapsed);
				}
				
				// Draw entities...
				for (Entity e : entities) {
					e.draw(g);
				}
				
				g.setFont(new Font("Consolas", 0, 20));
				g.setColor(Color.WHITE);
				g.drawString("LIVES: "+Integer.toString(lives), 10, this.getHeight() - 20);
				
				// Handle any new collisions
				for (int i = 0; i < entities.size(); i++) {
					for (int j = i + 1; j < entities.size(); j++) {
						Entity e1 = entities.get(i);
						Entity e2 = entities.get(j);
						
						if (e1.collidesWith(e2)) {
							e1.hasCollided(e2);
							e2.hasCollided(e1);
						}
					}
				}
				
				for (Entity e : escapedEntities) {
					if (entities.remove(e))
						lives--;
				}
				
				if (lives <= 0) {
					Font gameOverFont = new Font("Serif", Font.BOLD, 90);
					FontMetrics metrics = g.getFontMetrics(gameOverFont);
					g.setFont(gameOverFont);
					String str = "Game Over";
					g.drawString(str,
							getWidth()/2 - metrics.stringWidth(str)/2,
							getHeight()/2);
					gameRunning = false;
				}
				
				g.dispose();  // draw complete => clear buffer
				strategy.show();  // flip buffer
				
				prevTime = System.nanoTime();
			}
		}
		try {
			Thread.sleep(2000);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String argv[]) {
		StarTrek game = new StarTrek();
		game.startGame();
		game.gameLoop();
		System.exit(0);
	}
}
