package com.game;

import com.game.EnemyFactory.EnemyType;
import com.game.commands.CommandQueue;
import com.game.commands.GameCommand;
import com.game.input.GameKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class World extends Canvas {
	private static final long serialVersionUID = 1033091750611217336L;

	/** This strategy allows the use of accelerated page flip */
	private BufferStrategy strategy;
	private volatile boolean gameRunning;
	private List<com.game.Entity> entities;
	private List<Entity> newEntities;
	private List<Entity> escapedEntities;
	private int lives = 3;
    private CommandQueue commandQueue = new CommandQueue();

	private final Font gameOverFont = new Font("Serif", Font.BOLD, 90);

	private static final World instance = new World();

	public static synchronized World getInstance() {
		return instance;
	}

	private World() {
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
		this.createBufferStrategy(2);
		this.strategy = this.getBufferStrategy();
	}
	
	private void initEntities() {
		entities = new ArrayList<>();
		newEntities = new ArrayList<>();
		escapedEntities = new ArrayList<>();
		
		// Create our Enterprise ship and add to the collection
		Entity enterprise = new Enterprise(20, 300, 80, 120);
		entities.add(enterprise);
		
		// Create some enemies
		EnemyFactory factory = new EnemyFactory();
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
		this.addKeyListener(new GameKeyListener(commandQueue));
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
				doFrame(elapsed);
				prevTime = System.nanoTime();
			}
		}

		drawGameOverMessage();

		// Wait a short while before terminating
		try {
			Thread.sleep(2000);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawGameOverMessage() {
		final String str = "Game Over";

		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		FontMetrics metrics = g.getFontMetrics(gameOverFont);

		g.setFont(gameOverFont);
		g.setColor(Color.WHITE);
		g.drawString(str, getWidth()/2 - metrics.stringWidth(str)/2, getHeight()/2);

		g.dispose();
		strategy.show();
	}

	private void doFrame(long elapsed) {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1024, 768);

		removeDestroyedEntities();
		addNewEntities();
		processCommands();
		updateEntities(elapsed);
		handleCollisions();
		drawEntities(g);
		drawHud(g);

		for (Entity e : escapedEntities) {
			if (entities.remove(e))
				lives--;
		}

		if (isEndOfGame()) {
			gameRunning = false;
		}

		g.dispose();  // draw complete => clear buffer
		strategy.show();  // flip buffer
	}

	private void processCommands() {
		GameCommand command;
		while ((command = commandQueue.getNextCommand()) != null) {
			command.execute();
		}
	}

	private boolean isEndOfGame() {
		return lives <= 0 || !entities.get(0).isActive();
	}

	private void updateEntities(long elapsed) {
		for (Entity e : entities) {
			e.update(elapsed);
		}
	}

	private void drawEntities(Graphics2D g) {
		for (Entity e : entities) {
			e.draw(g);
		}
	}

	private void drawHud(Graphics g) {
		g.setFont(new Font("Consolas", 0, 20));
		g.setColor(Color.WHITE);
		g.drawString("LIVES: "+Integer.toString(lives), 10, this.getHeight() - 20);

	}

	private void handleCollisions() {
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
	}

	private void addNewEntities() {
		for (Entity e : newEntities) {
			entities.add(e);
		}
		newEntities.clear();
	}

	private void removeDestroyedEntities() {
		for (int i = 0; i < entities.size(); ) {
			if (!entities.get(i).isActive()) {
				if (i != 0) { // Don't remove enterprise; checked later
					entities.remove(i);
				}
			}
			else {
				i++;
			}
		}
	}
	
	public static void main(String argv[]) {
		World game = World.getInstance();
		game.startGame();
		game.gameLoop();
		System.exit(0);
	}
}
