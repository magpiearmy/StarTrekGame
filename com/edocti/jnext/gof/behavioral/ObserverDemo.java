package com.edocti.jnext.gof.behavioral;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

class Observable {
	protected List<Observer> observers;
	public Observable() {
		observers = new CopyOnWriteArrayList<>();
	}
	
	public void attach(Observer obs) {
		observers.add(obs);
	}
	public void detach(Observer obs) {
		observers.remove(obs);
	}
}

interface Observer {
	void update();
}

class Customer implements Observer {
	private Observable item;
	private String name;
	
	public Customer(String name) { this.name = name; }

	@Override
	public void update() {
		Deposit.INSTANCE.buy(this);
	}
}

class Deposit extends Observable {
	private int numberOfTrotinete;
	private Deposit() {}
	public static final Deposit INSTANCE = new Deposit();
	
	public int getNumberOfItems() {
		return numberOfTrotinete;
	}
	
	public synchronized void buy(Customer c) {
		if (numberOfTrotinete <= 0) {
			System.out.println("Customer " + c + " cannot buy");
			attach(c);
		} else {
			detach(c);
			setTrotinete(--numberOfTrotinete);
			System.out.println("Customer " + c + " bought one item");
		}
	}

	private void notifyObservers() {
		if (numberOfTrotinete > 0)
			for (Observer o : observers) {
				o.update();
			}
	}
	
	public void setTrotinete(int number) {
		numberOfTrotinete = number;
		notifyObservers();
	}
	
	public void addTrotinete(int numberOfItems) {
		this.numberOfTrotinete = numberOfItems;
		notifyObservers();
	}
}


public class ObserverDemo {

	public static void main(String[] args) {
		System.out.println("Starting...");
		Deposit dep = Deposit.INSTANCE;
		
		Customer[] customers = {new Customer("Ion"), new Customer("Gigi")};
		for (Customer c : customers) {
			dep.buy(c);
		}
		try { Thread.sleep(1000); } catch (InterruptedException e) {}
		
		dep.addTrotinete(1);
	}
}
