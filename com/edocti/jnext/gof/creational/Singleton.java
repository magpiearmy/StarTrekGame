package com.edocti.jnext.gof.creational;

import static java.lang.System.*;

public class Singleton {
	private int dummy;
	private static final Singleton instance = new Singleton(10);
	
	private Singleton(int dummy) { out.println(getClass()); this.dummy = dummy; }
	
	public void doSomething() {
		out.println(dummy);
	}
	
	public static /*synchronized*/ Singleton getInstance() {
//		if (instance == null)
//			instance = new Singleton(10);
		return instance;
	}
	
	public static void main(String[] args) {
		Thread[] workers = new Thread[30];
		for (int i = 0; i < 30; i++) {
			workers[i] = new Thread(new Runnable() {
				@Override public void run() {
					// Singleton.getInstance().doSomething();
					DoubleCheckedSingleton.getInstance().doSomething();
				}
			});
			workers[i].start();
		}
		
		for (int i = 0; i < 30; i++)
			try {workers[i].join();} catch(InterruptedException e) {}
	}
}

class DoubleCheckedSingleton {
	private static volatile DoubleCheckedSingleton instance;
	private DoubleCheckedSingleton() { out.println(getClass()); }
	public void doSomething() { out.println("DoubleCheckedSingleton#doSomething()"); }
	
	public static DoubleCheckedSingleton getInstance() {
		DoubleCheckedSingleton inst = instance;
		if (inst == null) {
			synchronized(DoubleCheckedSingleton.class) {
				out.println("me too");
				 inst = instance;
				if (inst == null) {
					out.println("just me");
					instance = new DoubleCheckedSingleton();
					inst = instance;
				}
			}
		}
		return inst;
	}
}
