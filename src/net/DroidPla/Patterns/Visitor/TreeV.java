package net.DroidPla.Patterns.Visitor;

abstract class TreeV<E> {
	public interface Visitor<E, R> {
		public R leaf(E elt);

		public R branch(R left, R right);
	}

	public abstract <R> R visit(Visitor<E, R> v);

	public static <T> TreeV<T> leaf(final T e) {
		return new TreeV<T>() {
			public <R> R visit(Visitor<T, R> v) {
				return v.leaf(e);
			}
		};
	}

	public static <T> TreeV<T> branch(final TreeV<T> l, final TreeV<T> r) {
		return new TreeV<T>() {
			public <R> R visit(Visitor<T, R> v) {
				return v.branch(l.visit(v), r.visit(v));
			}
		};
	}
}
