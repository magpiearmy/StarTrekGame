package com.edocti.jnext.gof.creational;

import static java.lang.System.*;

abstract class Node2D implements Cloneable {
	protected int x, y, w, h;
	protected String name;
	
	public Node2D(int x, int y, int w, int h, String name) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.name = name;
	}
	
	@Override public String toString() {
		return name + "(" + x + ", " + y + ")";
	}
	
	public void changePosition(int x, int y) { this.x = x; this.y = y; }
	
	public void setName(String name) { this.name = name; }
}

class Sound extends Object { Sound() {super();} }
class Texture {}

class Enemy extends Node2D {
	protected Sound sound;
	protected Texture texture;
	
	public void loadSound() {
		out.print("Loading sound...");
		try { Thread.sleep(1000); } catch (InterruptedException e) { }
		this.sound = new Sound();
		out.println("done");
	}
	public void loadTexture() {
		out.print("Loading texture...");
		try { Thread.sleep(1000); } catch (InterruptedException e) { }
		this.texture = new Texture();
		out.println("done");
	}

	public Enemy(int x, int y, int w, int h, String name) {
		super(x, y, w, h, name);
		loadSound();
		loadTexture();
	}
	
	public Enemy clone() throws CloneNotSupportedException {
		Enemy inst = (Enemy) super.clone();
		// deep clone logic
		inst.sound = this.sound;
		inst.texture = this.texture;
		return inst;
	}
}

public class Prototype {
	public static void main(String[] args) throws CloneNotSupportedException {
		Enemy drEvil = new Enemy(0, 0, 50, 100, "drEvil");
		for (int i = 0; i < 100; i++) {
			Enemy en = drEvil.clone();
			en.changePosition(i * 5, i * 15);
			en.setName("Dr Evil" + i);
			out.println(en);
		}
	}
}
