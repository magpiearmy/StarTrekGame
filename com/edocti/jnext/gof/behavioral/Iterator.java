package com.edocti.jnext.gof.behavioral;

import java.util.ArrayList;
import java.util.List;

class CyclicBuffer<T> implements Iterable<T> {
	private int size;
	private int idx;
	private List<T> buf;

	public CyclicBuffer(int size) {
		this.size = size;
		idx = 0;
		buf = new ArrayList<>(size);
	}
	
	public CyclicBuffer<T> add(T elem) {
		if (idx == size) {
			buf.remove(0);
			idx = 0;
		}
		buf.add(idx++, elem);
		return this;
	}
	
	public T get() { return buf.get(idx); }
	
	public T get(int i) { i = i % size; return buf.get(i); }
	
	@Override
	public java.util.Iterator<T> iterator() {
		return new MyIterator();
	}
	
	class MyIterator implements java.util.Iterator<T> {
		private int idx;

		@Override
		public boolean hasNext() {
			return idx < size;
		}

		@Override
		public T next() {
			return get(idx++);
		}
	}
}

public class Iterator {
	public static void main(String[] args) {
		CyclicBuffer<String> names = new CyclicBuffer<>(3);
		names.add("Gigi");
		names.add("Maria");
		names.add("Doru");
		names.add("Cucu");

		CyclicBuffer<Integer> ages = new CyclicBuffer<>(3);
		ages.add(10).add(20).add(30);
		// old-school iteration
		java.util.Iterator<String> it = names.iterator();
		while(it.hasNext())
			System.out.println(it.next());
		
		// for-each support (possible via Iterable)
		for (String name : names)
			System.out.println(name);
		for (int age : ages)
			System.out.println(age);
	}
}
