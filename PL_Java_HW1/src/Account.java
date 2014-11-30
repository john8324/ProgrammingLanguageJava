/*****************************************************************
 CS4001301 Programming Languages                   

 Programming Assignment #1

 Java programming using subtype, subclass, and exception handling

 To compile: %> javac Application.java

 To execute: %> java Application

 ******************************************************************/

import java.util.*;

class BankingException extends Exception {
	BankingException() {
		super();
	}

	BankingException(String s) {
		super(s);
	}
}

interface WithdrawableAccount {
	double withdraw(double amount) throws BankingException;

	double withdraw(double amount, Date withdrawDate) throws BankingException;
}

interface DepositableAccount {
	double deposit(double amount) throws BankingException;
}

interface FullFunctionalAccount extends WithdrawableAccount, DepositableAccount {
}

public abstract class Account {

	// protected variables to store common attributes for every bank accounts
	protected String accountName;
	protected double accountBalance;
	protected double accountInterestRate;
	protected Date openDate;
	protected Date lastInterestDate;

	// public methods for every bank accounts
	public String name() {
		return (accountName);
	}

	public double balance() {
		return (accountBalance);
	}

	abstract double computeInterest(Date interestDate) throws BankingException;

	public double computeInterest() throws BankingException {
		Date interestDate = new Date();
		return (computeInterest(interestDate));
	}
}

/*
 * Derived class: CheckingAccount
 * 
 * Description: Interest is computed daily; there's no fee for withdraw; there
 * is a minimum balance of $1000.
 */

class CheckingAccount extends Account implements FullFunctionalAccount {

	CheckingAccount(String s, double firstDeposit) {
		accountName = s;
		accountBalance = firstDeposit;
		accountInterestRate = 0.12;
		openDate = new Date();
		lastInterestDate = openDate;
	}

	CheckingAccount(String s, double firstDeposit, Date firstDate) {
		accountName = s;
		accountBalance = firstDeposit;
		accountInterestRate = 0.12;
		openDate = firstDate;
		lastInterestDate = openDate;
	}

	public double withdraw(double amount, Date withdrawDate)
			throws BankingException {
		// minimum balance is 1000, raise exception if violated
		if ((accountBalance - amount) < 1000) {
			throw new BankingException("Underfraft from checking account name:"
					+ accountName);
		} else {
			accountBalance -= amount;
			return (accountBalance);
		}
	}

	public double computeInterest(Date interestDate) throws BankingException {
		if (interestDate.before(lastInterestDate)) {
			throw new BankingException(
					"Invalid date to compute interest for account name"
							+ accountName);
		}

		int numberOfDays = (int) ((interestDate.getTime() - lastInterestDate
				.getTime()) / 86400000.0);
		System.out.println("Number of days since last interest is "
				+ numberOfDays);
		double interestEarned = (double) numberOfDays / 365.0
				* accountInterestRate * accountBalance;
		System.out.println("Interest earned is " + interestEarned);
		lastInterestDate = interestDate;
		accountBalance += interestEarned;
		return (accountBalance);
	}

	public double withdraw(double amount) throws BankingException {
		return withdraw(amount, new Date());
	}

	public double deposit(double amount) throws BankingException {
		accountBalance += amount;
		return accountBalance;
	}
}

/*
 * Derived class: SavingAccount
 * 
 * Description: monthly interest; fee of $1 for every transaction, except the
 * first three per month are free; no minimum balance.
 */

// TODO The first three transaction per month are NO fee!

class SavingAccount extends Account implements FullFunctionalAccount {

	SavingAccount(String s, double firstDeposit) {
		accountName = s;
		accountBalance = firstDeposit;
		accountInterestRate = 0.12;
		openDate = new Date();
		lastInterestDate = openDate;
	}

	SavingAccount(String s, double firstDeposit, Date firstDate) {
		accountName = s;
		accountBalance = firstDeposit;
		accountInterestRate = 0.12;
		openDate = firstDate;
		lastInterestDate = openDate;
	}

	public double deposit(double amount) throws BankingException {
		accountBalance += amount;
		// fee $1
		// TODO The first three transaction per month are NO fee!
		return --accountBalance;
	}

	public double withdraw(double amount, Date withdrawDate)
			throws BankingException {
		// fee $1, raise exception if violated
		// TODO The first three transaction per month are NO fee!
		if ((accountBalance - amount) < 1) {
			throw new BankingException("Underfraft from saving account name:"
					+ accountName);
		} else {
			accountBalance -= amount;
			return (accountBalance);
		}
	}

	public double computeInterest(Date interestDate) throws BankingException {
		if (interestDate.before(lastInterestDate)) {
			throw new BankingException(
					"Invalid date to compute interest for account name"
							+ accountName);
		}

		int numberOfMonths = (int) ((interestDate.getTime() - lastInterestDate
				.getTime()) / 86400000.0 / 30.0);
		System.out.println("Number of months since last interest is "
				+ numberOfMonths);
		double interestEarned = (double) numberOfMonths / 12.0
				* accountInterestRate * accountBalance;
		System.out.println("Interest earned is " + interestEarned);
		lastInterestDate = interestDate;
		accountBalance += interestEarned;
		return (accountBalance);
	}

	public double withdraw(double amount) throws BankingException {
		return withdraw(amount, new Date());
	}
}

/*
 * Derived class: CDAccount
 * 
 * Description: monthly interest; fixed amount and duration (e.g., you can open
 * 1 12-month CD for $5000; for the next 12 months you can't deposit anything
 * and withdrawals cost a $250 fee); at the end of the duration the interest
 * payments stop and you can withdraw w/o fee.
 */

// TODO 12month fee250

class CDAccount extends Account implements WithdrawableAccount {

	CDAccount(String s, double firstDeposit) {
		accountName = s;
		accountBalance = firstDeposit;
		accountInterestRate = 0.12;
		openDate = new Date();
		lastInterestDate = openDate;
	}

	CDAccount(String s, double firstDeposit, Date firstDate) {
		accountName = s;
		accountBalance = firstDeposit;
		accountInterestRate = 0.12;
		openDate = firstDate;
		lastInterestDate = openDate;
	}

	public double withdraw(double amount, Date withdrawDate)
			throws BankingException {
		if ((accountBalance - amount) < 0) {
			throw new BankingException("Underfraft from CD account name:"
					+ accountName);
		} else {
			accountBalance -= amount;
			return (accountBalance);
		}
	}

	public double computeInterest(Date interestDate) throws BankingException {
		if (interestDate.before(lastInterestDate)) {
			throw new BankingException(
					"Invalid date to compute interest for account name"
							+ accountName);
		}

		int numberOfMonths = (int) ((interestDate.getTime() - lastInterestDate
				.getTime()) / 86400000.0 / 30.0);
		System.out.println("Number of months since last interest is "
				+ numberOfMonths);
		double interestEarned = (double) numberOfMonths / 12.0
				* accountInterestRate * accountBalance;
		System.out.println("Interest earned is " + interestEarned);
		lastInterestDate = interestDate;
		accountBalance += interestEarned;
		return (accountBalance);
	}

	public double withdraw(double amount) throws BankingException {
		return withdraw(amount, new Date());
	}
}

/*
 * Derived class: LoanAccount
 * 
 * Description: like a saving account, but the balance is "negative" (you owe
 * the bank money, so a deposit will reduce the amount of the loan); you can't
 * withdraw (i.e., loan more money) but of course you can deposit (i.e., pay off
 * part of the loan).
 */

// TODO LoanAccount

class LoanAccount extends Account implements DepositableAccount {

	LoanAccount(String s, double firstDeposit) {
		accountName = s;
		accountBalance = firstDeposit;
		accountInterestRate = 0.12;
		openDate = new Date();
		lastInterestDate = openDate;
	}

	LoanAccount(String s, double firstDeposit, Date firstDate) {
		accountName = s;
		accountBalance = firstDeposit;
		accountInterestRate = 0.12;
		openDate = firstDate;
		lastInterestDate = openDate;
	}

	public double computeInterest(Date interestDate) throws BankingException {
		if (interestDate.before(lastInterestDate)) {
			throw new BankingException(
					"Invalid date to compute interest for account name"
							+ accountName);
		}

		int numberOfMonths = (int) ((interestDate.getTime() - lastInterestDate
				.getTime()) / 86400000.0 / 30.0);
		System.out.println("Number of months since last interest is "
				+ numberOfMonths);
		double interestEarned = (double) numberOfMonths / 12.0
				* accountInterestRate * accountBalance;
		System.out.println("Interest earned is " + interestEarned);
		lastInterestDate = interestDate;
		accountBalance += interestEarned;
		return (accountBalance);
	}

	public double deposit(double amount) throws BankingException {
		accountBalance += amount;
		// TODO Auto-generated method stub
		return 0;
	}
}
