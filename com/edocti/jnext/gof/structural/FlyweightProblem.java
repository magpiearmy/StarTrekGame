package com.edocti.jnext.gof.structural;

class Node {
	private static int count = 0;
	private int id;
	private int i;
	private float f;

	public Node() {
		id = count++;
	}

	public int getI() { return i; }

	public void setI(int i) { 	this.i = i; }

	public float getF() { return f; }

	public void setF(float f) { this.f = f; }

	@Override public String toString() {
		return "id: " + id + ", i = " + i + ", f = " + f;
	}
}

public class FlyweightProblem {
	private static final int N = 1000000;
	
	public static void main(String[] args) {
		Node[] nodes = new Node[N];
		for (int i = 0; i < N; i++) {
			nodes[i] = new Node();
			nodes[i].setI(10);
			nodes[i].setF(10.32f);
		}
		System.out.println(nodes[100].toString());
	}
}
