package SortingTester.testing.generation.conversion;

import SortingTester.testing.generation.Generator;

import java.util.LinkedList;

public class LinkedListGenerator<T> implements Generator<T> {

	private Generator<? extends T> generator;
	
	public LinkedListGenerator(Generator<? extends T> generator) {
		this.generator = generator;
	}
	
	@Override
	public LinkedList<T> generate(int size) {
		LinkedList<T> list = new LinkedList<T>();
		
		for(T value : generator.generate(size)) {
			list.add(value);
		}
		
		return list;
	}
}
