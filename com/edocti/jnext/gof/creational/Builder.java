package com.edocti.jnext.gof.creational;

import static java.lang.System.*;

class Sample {
	private int i1;
	private int i2;
	private int i3;
	private int i4;
	
	private Sample(SampleBuilder sb) {
		i1 = sb.i1;
		i2 = sb.i2;
		i3 = sb.i3;
		i4 = sb.i4;
	}
	
	public static class SampleBuilder {
		private int i1;
		private int i2;
		private int i3;
		private int i4;
		
		public SampleBuilder i1(int i) { i1 = i; return this; }
		public SampleBuilder i2(int i) { i2 = i; return this; }
		public SampleBuilder i3(int i) { i3 = i; return this; }
		public SampleBuilder i4(int i) { i4 = i; return this; }
		
		public Sample build() {
			return new Sample(this);
		}
	}
}


public class Builder {
	public static void main(String[] args) {
		Sample s1 = new Sample.SampleBuilder().i1(10).i3(100).build();
		Sample s2 = new Sample.SampleBuilder().i2(10).i4(100).build();
		Sample s3 = new Sample.SampleBuilder().i2(10).build();
	}
}

