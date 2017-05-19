package com.edocti.jnext.gof.structural;

abstract class Image {
	public static Platform platform;
	protected String name;
	
	public Image(String name) { this.name = name; }
	public abstract void load();
	public abstract void rotate(int angle);
	
	public static void setPlatform(Platform p) { platform = p; }
}

class Png extends Image {
	public Png(String name) {super(name);}
	@Override public void load() {
		platform.open(name);
		System.out.println("png.load()");
		platform.render();
	}

	@Override
	public void rotate(int angle) {
		System.out.println("pre: png.rotate()");
		platform.rotate(angle);
		System.out.println("post: png.rotate()");
	}
}

class Jpg extends Image {
	public Jpg(String name) {super(name);}
	
	@Override public void load() {
		platform.open(name);
		System.out.println("jpg: some specific opetration");
		System.out.println("jpg.load()");
		platform.render();
	}

	@Override
	public void rotate(int angle) {
		System.out.println("pre: jpg.rotate()");
		platform.rotate(angle);
		System.out.println("post: jpg.rotate()");
	}
}


interface Platform {
	void open(String name);
	void render();
	void rotate(int angle);
}

class LibOpenGL {
	public void operation1() { System.out.println("OGL.op1()"); }
	public void operation2() { System.out.println("OGL.op2()"); }
	public void operation3() { System.out.println("OGL.op3()"); }
	public void operation4() { System.out.println("OGL.op4()"); }

}

class Linux implements Platform {
	private LibOpenGL lib = new LibOpenGL();
	@Override
	public void open(String name) {
		System.out.println("Linux.open(" + name + ")");
	}

	@Override
	public void render() {
		System.out.println("Linux.render()");
		lib.operation1();
		lib.operation2();
	}

	@Override
	public void rotate(int angle) {
		System.out.println("Linux.rotate()");
		lib.operation1();
		lib.operation2();
		lib.operation4();
	}
}

class LibDirectX {
	public void operation1() { System.out.println("DX.op1()"); }
	public void operation2() { System.out.println("DX.op2()"); }
	public void operation3() { System.out.println("DX.op3()"); }
}

class Windows implements Platform {
	private LibDirectX lib = new LibDirectX();
	@Override
	public void open(String name) {
		System.out.println("Windows.open(" + name + ")");
	}

	@Override
	public void render() {
		System.out.println("Windows.render()");
		lib.operation1();
	}

	@Override
	public void rotate(int angle) {
		System.out.println("Windows.rotate()");
		lib.operation2();
		lib.operation3();
	}
}

public class Bridge {
	private static void setPlatform(String arg) {
		if (arg.equalsIgnoreCase("linux")) {
			Image.setPlatform(new Linux());
			return;
		}
		if (arg.equalsIgnoreCase("windows")) {
			Image.setPlatform(new Windows());
			return;
		}
		throw new IllegalArgumentException("Unknown platform");
	}
	
	public static void main(String[] args) {
		setPlatform(args[0]);
		Image im1 = new Jpg("im1.jpg");
		Image im2 = new Png("im2.png");
		im1.load();
		im1.rotate(90);
		im1.load();
		im2.load();
		im2.rotate(90);
		im2.load();
	}
}
