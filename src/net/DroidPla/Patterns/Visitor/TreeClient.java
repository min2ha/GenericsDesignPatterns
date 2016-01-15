package net.DroidPla.Patterns.Visitor;

class TreeClient {
	public static <T> String toString(TreeV<T> t) {
		return t.visit(new TreeV.Visitor<T, String>() {
			public String leaf(T e) {
				return e.toString();
			}

			public String branch(String l, String r) {
				return "(" + l + "^" + r + ")";
			}
		});
	}

	public static <N extends Number> double sum(TreeV<N> t) {
		return t.visit(new TreeV.Visitor<N, Double>() {
			public Double leaf(N e) {
				return e.doubleValue();
			}

			public Double branch(Double l, Double r) {
				return l + r;
			}
		});
	}

	public static void main(String[] args) {
		TreeV<Integer> t = TreeV.branch(TreeV.branch(TreeV.leaf(1), TreeV.leaf(2)), TreeV.leaf(3));
		assert toString(t).equals("((1^2)^3)");
		assert sum(t) == 6;
	}
}
