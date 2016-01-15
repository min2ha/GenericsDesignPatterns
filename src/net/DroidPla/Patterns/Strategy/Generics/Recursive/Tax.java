package net.DroidPla.Patterns.Strategy.Generics.Recursive;


/*
 * In accordance with good practice, we represent all monetary values, such as incomes
 * or taxes, by long integers standing for the value in cents (see the item “Avoid float and
 * double if exact answers are required”, in the General Programming chapter of Effective
 * Java by Joshua Bloch, Addison-Wesley).
 * 
 * 
 * **/



/**
 * Now an instance of NonProfitTrust takes a strategy that expects a NonProfitTrust as
 * an argument, and ForProfitTrust behaves similarly. It is often convenient to set up a
 * parameterized type hierarchy in this way, where classes with subclasses take a type
 * parameter and are abstract and classes without subclasses do not take a type parameter
 * and are final. A body for the getThis method is declared in each final subclass.
 * 
 * 
 * In all three cases, the class or interface is the base class of a type hierarchy, and the type
parameter stands for a specific subclass of the base class. Thus, P in TaxPayer<P> stands
for the specific kind of tax payer, such as Person or Trust; just as T in Comparable<T>
stands for the specific class being compared, such as String; or E in Enum<E> stands for
the specific enumerated type, such as Season.
The tax payer class contains a field for the tax strategy and a method that passes the
tax payer to the tax strategy, as well as a recursive declaration for P just like the one
used in TaxPayer.


But the compiler rejects the above with a type error. The problem is that this has type
TaxPayer<P>, whereas the argument to computeTax must have type P. Indeed, within
each specific tax payer class, such as Person or Trust, it is the case that this does have
type P; for example, Person extends TaxPayer<Person>, so P is the same as Person within
this class. So, in fact, this will have the same type as P, but the type system does not
know that!
We can fix this problem with a trick. In the base class TaxPayer<P> we define an abstract
method getThis that is intended to return the same value as this but gives it the type
P. The method is instantiated in each class that corresponds to a specific kind of tax
payer, such as Person or Trust, where the type of this is indeed the same as the type
P.


The differences from the previous code are in bold. Occurrences of this are replaced by
calls to getThis; the method getThis is declared abstract in the base class and it is
instantiated appropriately in each final subclass of the base class. The base class Tax
Payer<P> must be declared abstract because it declares the type for getThis but doesn’t
declare the body. The body for getThis is declared in the final subclasses Person and
Trust.
Since Trust is declared final, it cannot have subclasses. Say we wanted a subclass
NonProfitTrust of Trust. Then not only would we have to remove the final declaration
on the class Trust, we would also need to add a type parameter to it.
 * ***/


abstract class TaxPayer<P extends TaxPayer<P>> {
	public long income; // in cents
	private TaxStrategy<P> strategy;

	public TaxPayer(long income, TaxStrategy<P> strategy) {
		this.income = income;
		this.strategy = strategy;
	}

	protected abstract P getThis();

	public long getIncome() {
		return income;
	}

	public long computeTax() {
		return strategy.computeTax(getThis());
	}
}

class Person extends TaxPayer<Person> {
	public Person(long income, TaxStrategy<Person> strategy) {
		super(income, strategy);
	}

	protected Person getThis() {
		return this;
	}
}

class Trust extends TaxPayer<Trust> {
	private boolean nonProfit;

	public Trust(long income, boolean nonProfit, TaxStrategy<Trust> strategy) {
		super(income, strategy);
		this.nonProfit = nonProfit;
	}

	protected Trust getThis() {
		return this;
	}

	public boolean isNonProfit() {
		return nonProfit;
	}
}

interface TaxStrategy<P extends TaxPayer<P>> {
	public long computeTax(P p);
}

class DefaultTaxStrategy<P extends TaxPayer<P>> implements TaxStrategy<P> {
	private static final double RATE = 0.40;

	public long computeTax(P payer) {
		return Math.round(payer.getIncome() * RATE);
	}
}

class DodgingTaxStrategy<P extends TaxPayer<P>> implements TaxStrategy<P> {
	public long computeTax(P payer) {
		return 0;
	}
}

class TrustTaxStrategy extends DefaultTaxStrategy<Trust> {
	public long computeTax(Trust trust) {
		return trust.isNonProfit() ? 0 : super.computeTax(trust);
	}
}


public class Tax {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
