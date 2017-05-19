package com.edocti.jnext.gof.behavioral;

import java.util.ArrayList;
import java.util.List;

class Notepad {  // Originator
	
	private List<String> lines;
	
	public Notepad() {
		lines = new ArrayList<>();
	}
	
	public Memento exportState() {  // createMemento
		return new Memento(lines);
	}
	
	public void restoreState(Memento m) {  // setMemento
		lines = new ArrayList<>();
		lines.addAll(m.getState());
	}
	
	public Notepad addLine(String line) {
		lines.add(line);
		return this;
	}
	
	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String line : lines)
			sb.append(line).append("\n");
		return sb.toString();
	}
}

class Memento {
	private List<String> state;
	public Memento(List<String> state) {
		this.state = new ArrayList<>();
		this.state.addAll(state);
	}
	public List<String> getState() {
		return state;
	}
	public void setState(List<String> state) {
		this.state = state;
	}
}


class UndoManager {  // Caretaker
	private List<Memento> history = new ArrayList<>();
	private List<String> ids = new ArrayList<>();
	
	public void addState(String id, Memento m) {  // addMemento
		history.add(m);
		ids.add(id);
	}
	
	public Memento getState(String id) {  // getMemento
		int idx = -1;
		for (int i = 0; i < ids.size(); i++) {
			if (ids.get(i).equalsIgnoreCase(id)) 
				idx = i;
		}
		if (idx != -1) {
			return history.get(idx);
		}
		return null;
	}
}

public class MementoDemo {
	public static void main(String[] args) {
		Notepad myDoc = new Notepad();
		UndoManager mgr = new UndoManager();
		myDoc.addLine("cucu").addLine("rigu").addLine("bau");
		System.out.println(myDoc);
		mgr.addState("1", myDoc.exportState());  // createMemento
		myDoc.addLine("boieri").addLine("mari");
		System.out.println(myDoc);
		mgr.addState("2", myDoc.exportState());  // createMemento
		
		System.out.println("UNDO:");
		myDoc.restoreState(mgr.getState("1"));
		System.out.println(myDoc);
	}
}
