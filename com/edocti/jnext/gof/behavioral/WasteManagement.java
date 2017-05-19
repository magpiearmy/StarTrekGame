package com.edocti.jnext.gof.behavioral;

interface CollectorAction {
	void action(Al a);
	void action(Paper p);
	void action(Glass g);
}

abstract class Waste {
	protected double volume;

	abstract void collectOperation(CollectorAction a);
}

class Al extends Waste {
	void collectOperation(CollectorAction a) {
		a.action(this);
	}
	public Al(double v) { volume = v; }
}

class Paper extends Waste {
	void collectOperation(CollectorAction a) {
		a.action(this);
	}
	public Paper(double v) { volume = v; }
}

class Glass extends Waste {
	void collectOperation(CollectorAction a) {
		a.action(this);
	}
	public Glass(double v) { volume = v; }
}

class WeightCalculator implements CollectorAction {
	double alWeight;
	double glassWeight;
	double paperWeight;

	@Override
	public void action(Al a) {
		alWeight += a.volume * 10;
	}

	@Override
	public void action(Paper p) {
		paperWeight += p.volume * 1;
	}

	@Override
	public void action(Glass g) {
		glassWeight += g.volume * 20;
	}
	
	public double getTotalWeight() {
		return alWeight + paperWeight + glassWeight;
	}
}

public class WasteManagement {
	public static void main(String[] args) {
		Waste[] recycleBin = {
			new Al(100), new Al(200), new Glass(100),
			new Paper(20), new Paper(100)
		};
		WeightCalculator wc = new WeightCalculator();
		for (Waste w : recycleBin) {
			w.collectOperation(wc);
		}
		System.out.println(wc.getTotalWeight());
	}
}
