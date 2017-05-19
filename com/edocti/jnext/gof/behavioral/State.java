package com.edocti.jnext.gof.behavioral;

public abstract class State {
	abstract void controlInjectors();
	abstract void steer();
	abstract void brake();
	
	public static void main(String[] args) {
		Car car = new Car(new SportState());
		car.brake();
		car.steer();
		car.controlInjectors();
	}
}

class SportState extends State {
	@Override
	void controlInjectors() {
		System.out.println("Sport injection");
	}

	@Override
	void steer() {
		System.out.println("Sport steering");
	}

	@Override
	void brake() {
		System.out.println("Sport braking");
	}
}

class ComfortState extends State {
	@Override
	void controlInjectors() {
		System.out.println("Comfort injection");
	}

	@Override
	void steer() {
		System.out.println("Comfort steering");
	}

	@Override
	void brake() {
		System.out.println("Comfort braking");
	}
}


class Car {
	private State state;
	public Car(State state) {
		this.state = state;
	}
	
	public void controlInjectors() {
		state.controlInjectors();
	}
	
	public void steer() {
		state.steer();
	}
	
	public void brake() {
		state.brake();
	}
}

//
//public class State {
//}
