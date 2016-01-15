package net.DroidPla.Patterns.Function;

public interface Function<A, B, X extends Throwable> {
	public B apply(A x) throws X;
}