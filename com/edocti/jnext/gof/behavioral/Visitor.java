package com.edocti.jnext.gof.behavioral;

interface IVisitor {
	void visit(Employee e);
	void visit(Manager m);
	void visit(Programmer p);
}

abstract class Employee {
	protected String name;
	protected double salary;
	
	public void operation(IVisitor v) {
		if (v != null)
			v.visit(this);
	}
}

class Manager extends Employee {
	public Manager(String name, double salary) {
		super();
		this.name = name;
		this.salary = salary;
	}

	public void operation(IVisitor v) {
		if (v != null)
			v.visit(this);
	}
}

class Programmer extends Employee {
	public Programmer(String name, double salary) {
		this.name = name;
		this.salary = salary;
	}

	public void operation(IVisitor v) {
		if (v != null)
			v.visit(this);
	}
}

class CalculateTax implements IVisitor {
	@Override
	public void visit(Employee e) {
	}

	@Override
	public void visit(Manager m) {
		System.out.println("Manager tax = " + 0.2 * m.salary);
	}

	@Override
	public void visit(Programmer p) {
		System.out.println("Programmer tax = " + 0.1 * p.salary);
	}
}

class DisplayEmployee implements IVisitor {
	@Override
	public void visit(Employee e) {
	}

	@Override
	public void visit(Manager m) {
		System.out.println("Manager " + m.name);
	}

	@Override
	public void visit(Programmer p) {
		System.out.println("Programmer " + p.name);
	}
}

public class Visitor {
	public static void main(String[] args) {
		Employee m1 = new Manager("Gigi", 100);
		Employee p1 = new Programmer("Ionel", 100);
		IVisitor displayer = new DisplayEmployee();
		IVisitor taxCalculator = new CalculateTax();
		m1.operation(displayer);
		p1.operation(displayer);
		m1.operation(taxCalculator);
	}
}
