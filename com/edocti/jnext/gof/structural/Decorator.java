package com.edocti.jnext.gof.structural;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import static java.lang.System.*;

interface Node2D {
	void draw();
}

class TextField implements Node2D {
	protected int w, h;
	public TextField(int w, int h) { this.w = w; this.h = h; }
	
	@Override
	public void draw() {
		System.out.println("TextField#draw()");
	}
}

abstract class Node2DDecorator implements Node2D {
	private Node2D node;
	public Node2DDecorator(Node2D node) { this.node = node; }
	@Override public void draw() { node.draw(); }
}

class BorderDecorator extends Node2DDecorator {
	public BorderDecorator(Node2D node) {
		super(node);
	}
	
	@Override public void draw() {
		super.draw();
		System.out.println("\tBorder");
	}
}

class ScrollDecorator extends Node2DDecorator {
	public ScrollDecorator(Node2D node) {
		super(node);
	}
	
	@Override public void draw() {
		super.draw();
		System.out.println("\tScroll");
	}
}

class PasswordDecorator extends Node2DDecorator {
	public PasswordDecorator(Node2D node) {
		super(node);
	}
	
	@Override public void draw() {
		super.draw();
		System.out.println("\tPassword");
	}
}

public class Decorator extends TestCase {
	public void testDecorator() {
		Node2D username = new BorderDecorator(new ScrollDecorator(new TextField(400, 50)));
		Node2D password = new BorderDecorator(new PasswordDecorator(new TextField(400, 50)));
		
		username.draw();
		password.draw();
	}

	public static void main(String[] args) {
		TestRunner.run(Decorator.class);
	}
}
