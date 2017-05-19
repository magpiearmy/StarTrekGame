package com.edocti.jnext.gof.behavioral;

abstract class DrivingMode {
	abstract void controlInjectors();
	abstract void steer();
	abstract void brake();
	
}

class SportsMode extends DrivingMode {
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

class ComfortMode extends DrivingMode {
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

abstract class SpeedMode {
	abstract void LKAS();
	abstract void ACC();
}

class Over60 extends SpeedMode {

	@Override
	void LKAS() {
		System.out.println("LKAS activated");
	}

	@Override
	void ACC() {
		System.out.println("ACC activated");
	}
	
}

class LessThen60 extends SpeedMode {
	@Override
	void LKAS() {
		System.out.println("LKAS deactivated");
	}

	@Override
	void ACC() {
		System.out.println("ACC deactivated");
	}
}

class StrategyCar {
	private DrivingMode drivingMode;
	private SpeedMode speedMode;
	private double speed;
	
	public StrategyCar(DrivingMode state) {
		this.drivingMode = state;
		speedMode = new LessThen60();
	}
	
	public void accelerate(double delta) {
		speed += delta;
		if (speed >= 60) {
			setSpeedMode(new Over60());
		} else {
			setSpeedMode(new LessThen60());
		}
	}
	
	public void setSpeedMode(SpeedMode m) { speedMode = m; }
	
	public void setDrivingMode(DrivingMode m) { drivingMode = m; }
	
	public void controlInjectors() {
		drivingMode.controlInjectors();
	}
	
	public void steer() {
		drivingMode.steer();
	}
	
	public void brake() {
		drivingMode.brake();
	}
	
	public void LKAS() {
		speedMode.LKAS();
	}
	public void ACC() {
		speedMode.ACC();
	}
}


public class Strategy {
	public static void main(String[] args) {
		StrategyCar car = new StrategyCar(new SportsMode());
		car.brake();
		car.steer();
		car.controlInjectors();
		car.LKAS();
		car.ACC();
		
		car.accelerate(100);
		car.controlInjectors();
		car.LKAS();
		car.ACC();
		car.setDrivingMode(new ComfortMode());
		car.brake();
		car.steer();
		car.controlInjectors();

	}

}
