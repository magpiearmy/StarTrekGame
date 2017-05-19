package com.edocti.jnext.gof.structural;

import static java.lang.System.*;

class AmericanSmartPhone {
	private String name;
	private AmericanPlug plug;
	
	public AmericanSmartPhone(String name) {
		this.name = name;
		plug = new AmericanPlug();
	}
	@Override public String toString() { return name; }
	
	public void charge(AmericanSocket socket) {
		socket.accept(plug);
		out.println(name + " charging...");
	}
}

class AmericanPlug {
	public void plug(AmericanSocket socket) {
		socket.accept(this);
	}
}
class AmericanSocket {
	public void accept(AmericanPlug plug) {
		out.println("American plug inserted");
	}
}
class RomanianPlug {
	public void plug(RomanianSocket socket) {
		socket.accept(this);
	}
}
class RomanianSocket {
	public void accept(RomanianPlug plug) {
		out.println("Romanian plug inserted");
	}
}

class RomanianToAmericanAdapter {
	private AmericanSocket amSocket;
	private RomanianPlug romPlug;
	
	public RomanianToAmericanAdapter() {
		amSocket = new AmericanSocket();
		romPlug = new RomanianPlug();
	}
	
	public void plug(RomanianSocket socket) {
		socket.accept(romPlug);
	}
	
	public AmericanSocket getSocket() { return amSocket; }
	
}

public class Adapter {
	public static void main(String[] args) {
		AmericanSmartPhone galaxy = new AmericanSmartPhone("Galaxy");
		RomanianSocket wallSocket = new RomanianSocket();
		RomanianToAmericanAdapter adapter = new RomanianToAmericanAdapter();
		adapter.plug(wallSocket);
		galaxy.charge(adapter.getSocket());
	}
}
