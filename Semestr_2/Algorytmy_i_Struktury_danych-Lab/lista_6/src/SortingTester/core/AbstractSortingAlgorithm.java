package SortingTester.core;

import SortingTester.measuring.CountingComparator;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractSortingAlgorithm<T> {

	private CountingComparator<T> comparator;
	
	public AbstractSortingAlgorithm(Comparator<? super T> comparator) {
		this.comparator = new CountingComparator<T>(comparator);
	}
	
	public void reset() {
		comparator.reset();
	}
	
	public long comparisons() {
		return comparator.count();
	}
	
	public Comparator<? super T> baseComparator() {
		return comparator.baseComparator();
	}
	
	protected int compare(T lhs, T rhs) {
		return comparator.compare(lhs, rhs);
	}
	
	public abstract List<T> sort(List<T> list);
}
