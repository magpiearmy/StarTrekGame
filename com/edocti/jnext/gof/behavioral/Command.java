package com.edocti.jnext.gof.behavioral;

import static java.lang.System.*;

interface ActionEvent {}

class ButtonPressed implements ActionEvent {}

interface ActionListener {
	public void actionPerformed(ActionEvent e);
}

class Button {
	private boolean pressed;
	private ActionListener listener;
	
	public Button() {}
	
	public void addActionListener(ActionListener a) {
		listener = a;
	}
	
	public void eventOccured(ActionEvent e) {
		pressed = !pressed;
		if (listener != null) {
			listener.actionPerformed(e);
		}
	}
}

public class Command {
	private static long tid() {
		return Thread.currentThread().getId();
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch(Exception e) {
		}
	}
	
	public static void main(String... args) {
		final Button b = new Button();

		out.printf("Thread %d: register action listener...", tid());
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				out.printf("Thread %d: Button was pressed\n", tid());			
			}
		});
		out.println("OK. Resume work...");

		// this thread doesn't stop its work; actionPerformed() will be called asynchronously
		sleep(100);
		
		// at some point in time and from another thread, an action occurs:
		new Thread(new Runnable() {
			@Override public void run() {
				b.eventOccured(new ButtonPressed());
			}
		}).start();
		
		sleep(100);
		out.printf("Thread %d: still working...\n", tid());
	}
}
