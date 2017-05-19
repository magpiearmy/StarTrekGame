package com.edocti.jnext.gof.behavioral;


abstract class PaymentMethod {
	abstract void displayPaymentInformation();
}


abstract class MyFramework {

	public void establishSecureConnection() {
		System.out.println("Secure connection established");
	}
	
	public void connectToBank() {
		establishSecureConnection();
		System.out.println("Connecting to bank...");
	}
	
	public abstract PaymentMethod getPaymentMethod();
	
	public void buy(double sum, String shopName, String product) {
		System.out.println("Buying " + product + " from " + shopName);
		PaymentMethod m = getPaymentMethod();
		m.displayPaymentInformation();
		connectToBank();
		System.out.println("Payment complete");
	}
}

class MyPaymentMethod extends PaymentMethod {

	@Override
	void displayPaymentInformation() {
		System.out.println("Display My Payment info");
	}
}


class MyApplication extends MyFramework {

	@Override
	public PaymentMethod getPaymentMethod() {
		return new MyPaymentMethod();
	}
}

public class TemplateMethod {

	public static void main(String[] args) {
		String product = "Trotineta";
		String shopName = "emag";
		MyFramework myApp = new MyApplication();
		myApp.buy(500, shopName, product);
	}
}
