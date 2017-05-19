package com.edocti.jnext.gof.structural;


class FlyNodeFactory {
	// this can be 1000 or 1000000 (up to you)
	private static final int N = 1000;
	
	// the common state can be immutable or not
	// if not, you'll use some pool and have an update logic
//	public static int[] i = new int [N];
//	public static float[] f = new float[N];
	public static FlyNode[] nodes = new FlyNode[N];
	
//	static {
//		for (int j = 0; j < N; j++) {
//			i[j] = 10;
//			f[j] = 10.23f;
//		}
//	}
	
	public static int getI(int id) {
		return 10; // i[id % N];
	}
	
	public static float getF(int id) {
		return 10.23f; // f[id % N];
	}
	
	public static FlyNode getFlyNode(int id) {
		if (nodes[id % N] == null) {
			nodes[id % N] = new FlyNode(id);
		}
		return nodes[id % N];
	}
	
}


class FlyNode {
	private static int count = 0;
	private int id;
	public FlyNode(int id) { this.id = id; }

	public static int getI(int id) { return FlyNodeFactory.getI(id); }

//	public static void setI(int id, int i) { FlyNodeFactory.i[id] = i; }

	public static float getF(int id) { return FlyNodeFactory.getF(id); }

//	public void setF(int id, float f) { FlyNodeFactory.f[id] = f; }
	
	@Override public String toString() {
		return "id: " + id + ", i = " + getI(id) + ", f = " + getF(id);
	}
	
	public int getId() { return id; }
}


public class FlyweightSolution {
//	private static final int N = 1000000;
	public static void main(String[] args) {
//		FlyNode[] nodes = new FlyNode[N];
//		for (int i = 0; i < N; i++) {
//			nodes[i] = new FlyNode();
//		}
		// flyweights are created on the fly, when needed
		System.out.println(FlyNodeFactory.getFlyNode(100).toString());
	}
}
