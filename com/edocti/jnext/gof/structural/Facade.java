package com.edocti.jnext.gof.structural;

class CartesianPoint2D {
	private double x, y;

	public CartesianPoint2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void move(int dx, int dy) {
		x += dx;
		y += dy;
	}

	@Override public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}

class PolarPoint2D {
	private double radius, angle;

	public PolarPoint2D(double radius, double angle) {
		this.radius = radius;
		this.angle = angle;
	}

	public void rotate(int ang) {
		angle += ang % 360;
	}

	public double getRadius() { return radius; }
	public double getAngle() { return angle; }
	
	@Override public String toString() {
		return "(" + radius + " @ " + angle + ")";
	}
}


class CoordUtils {
	public static final CartesianPoint2D polarToCartesian(PolarPoint2D pp) {
		final double angleRad = pp.getAngle() * Math.PI / 180.0;
		final double radius = pp.getRadius();
		double x = Math.cos(angleRad) * radius;
		double y = Math.sin(angleRad) * radius;
		return new CartesianPoint2D(x, y);
	}
	
	public static final PolarPoint2D cartesianToPolar(CartesianPoint2D pc) {
		double x = pc.getX();
		double y = pc.getY();
		double radius = Math.sqrt( x * x + y * y );
		double angleRad = Math.atan2(y, x) * 180 / Math.PI;
		return new PolarPoint2D(radius, angleRad);
	}
}


class Point2D {
	private CartesianPoint2D pc;
	public Point2D(double x, double y) {
		pc = new CartesianPoint2D(x, y);
	}
	
	public void move(int dx, int dy) {
		pc.move(dx, dy);
	}
	
	public void rotate(int angle, Point2D pivot) {
		double x = pc.getX() - pivot.pc.getX();
		double y = pc.getY() - pivot.pc.getY();
		CartesianPoint2D cart = new CartesianPoint2D(x, y);
		PolarPoint2D pp = CoordUtils.cartesianToPolar(cart);
		pp.rotate(angle);
		pc = CoordUtils.polarToCartesian(pp);
	}
	@Override public String toString() {
		return "(" + pc.getX() + ", " + pc.getY() + ")"; 
	}
}

class Line2D {
	private Point2D p1, p2;

	public Line2D(Point2D p1, Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public void move(int dx, int dy) {
		p1.move(dx, dy);
		p2.move(dx, dy);
	}

	public void rotate(int angle) {
		p2.rotate(angle, p1);
	}

	public String toString() {
		return "p1: " + p1 + ", p2: " + p2;
	}
}

public class Facade {
	public static void main(String[] args) {
		Line2D l = new Line2D(new Point2D(0, 0), new Point2D(10, 10));
		l.move(2, 2);
		l.rotate(30);
		System.out.println(l);
	}
}
