package com.edocti.jnext.gof.behavioral;
import static java.lang.System.*;
interface Entity {
	void interact1(Entity e);
	void interact2(Elf e);
	void interact2(Orc o);
	void interact2(Dwarf d);
}

class Elf implements Entity {
	public void interact1(Entity second) {
		second.interact2(this);
	}
	public void interact2(Elf e) {
		out.println("Elf is friend with elf");
	}
	public void interact2(Orc o) {
		out.println("Elf wins over Orc");
	}
	public void interact2(Dwarf d) {
		out.println("We are friends");
	}
}

class Orc implements Entity {
	public void interact1(Entity second) {
		second.interact2(this);
	}
	public void interact2(Elf e) {
		out.println("Orc loses in front of elf");
	}
	public void interact2(Orc o) {
		out.println("Orc is friend with orc");
	}
	public void interact2(Dwarf d) {
		out.println("Sometimes orc wins over dwarf");
	}
}

class Dwarf implements Entity {
	public void interact1(Entity second) {
		second.interact2(this);
	}
	public void interact2(Elf e) {
		out.println("Dward is freind with elf");
	}
	public void interact2(Orc o) {
		out.println("Dwarf sometimes wins over Orc");
	}
	public void interact2(Dwarf d) {
		out.println("Dwarf is friend with dwarf");
	}
}

public class VisitorDouble {
	public static void main(String[] args) {
		Entity[] entities = {
				new Elf(), new Elf(), new Orc(), new Orc(),
				new Dwarf(), new Dwarf()};
		for (int i = 0; i < entities.length; i++) {
			for (int j = i+1; j < entities.length; j++) {
				entities[i].interact1(entities[j]);
			}
		}
	}
}
