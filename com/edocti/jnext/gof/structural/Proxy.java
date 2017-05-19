package com.edocti.jnext.gof.structural;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import static java.lang.System.*;

interface ProxyBase {
	void op1();
	void op2();
	String op3();
}

class SimpleProxy implements ProxyBase {
	private ProxyBase target;
	public SimpleProxy(ProxyBase target) {
		this.target = target;
	}
	@Override
	public void op1() {
		// additional operations / business logic
		target.op1();
	}
	@Override
	public void op2() {
		target.op2();		
	}
	@Override
	public String op3() {
		return target.op3();
	}
}

class SimpleImplementation implements ProxyBase {
	@Override
	public void op1() { out.println("Simple#op1"); }

	@Override
	public void op2() { out.println("Simple#op2"); }

	@Override
	public String op3() { return "Simple#op3"; }
	
}


public class Proxy extends TestCase {
	public void testProxy() {
		SimpleProxy proxy = new SimpleProxy(new SimpleImplementation());
		proxy.op1();
	}
	
	public static void main(String[] args) {
		TestRunner.run(Decorator.class);
	}
}

