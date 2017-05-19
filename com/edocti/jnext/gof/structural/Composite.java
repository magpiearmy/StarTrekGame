package com.edocti.jnext.gof.structural;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

interface Action {
	public void doAction(File file);
}

class JavaCompile implements Action {
	@Override
	public void doAction(File file) {
		if (!file.getExt().equals("java")) {
			
		} else {
			System.out.println("Compiling " + file.getName() + ".java...");
		}
	}
}

abstract class File {
	protected String name;
	protected String ext;
	protected Action action;
	
	public File(String name, String ext) { this.name = name; this.ext = ext;}
	
	public void action() {
		if (action != null)
			action.doAction(this); 
	}

	public void setAction(Action a) { action = a; }
	public String getExt() { return ext; }
	public String getName() { return name; }
	public void open() {} 
	public abstract String cat();
}

class TextFile extends File {
	protected String content;
	
	public TextFile(String name, String content) {
		super(name, "txt");
		this.content = content;
	}

	@Override
	public String cat() {
		return content + "\n";
	}
}

class JavaFile extends File {
	protected String content;
	
	public JavaFile(String name, String content) {
		super(name, "java");
		this.content = content;
	}

	@Override
	public String cat() {
		return name + ".java: " + content + "\n";
	}
}

class Folder extends File {
	private List<File> children;
	public Folder(String name) {
		super(name, "");
		children = new CopyOnWriteArrayList<>();
	}
	
	@Override public void action() {
		if (action != null)
			action.doAction(this);
		for (File f: children) {
			f.action();
		}
	}

	@Override
	public String cat() {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append('\n');
		for (File f : children) {
			sb.append(f.name).append(':');
			sb.append(f.cat());
		}
		return sb.toString();
	}
	public void addFile(File file) {
		if (file == null) {
			throw new IllegalArgumentException("file is null");
		}
		children.add(file);
	}
	
	public void rm(String name) {
		int idx = -1;
		for(int i = 0; i < children.size(); i++) {
			if (children.get(i).name.equalsIgnoreCase(name)) {
				idx = i;
			}
		}
		if (idx != -1) {
			children.remove(idx);
		}
	}
}

public class Composite {
	public static void main (String[] args) {
		Folder root = new Folder("root");
		root.addFile(new TextFile("a11", "content11"));
		root.addFile(new TextFile("a12", "content12"));
		root.addFile(new TextFile("a13", "content13"));
		Folder subfolder = new Folder("subfolder");
		root.addFile(subfolder);
		subfolder.addFile(new TextFile("a21", "content21"));
		subfolder.addFile(new TextFile("a22", "content22"));
		subfolder.addFile(new TextFile("a23", "content23"));
		JavaFile f = new JavaFile("Abc", "public class Abc");
		f.setAction(new JavaCompile());
		subfolder.addFile(f);

		System.out.println(root.cat());
		root.action();
	}
}
