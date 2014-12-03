/*****************************************************************
 CS4001301 Programming Languages                   

 Programming Assignment #1

 Java programming using subtype, subclass, and exception handling

 To compile: %> javac Application.java

 To execute: %> java Application

 ******************************************************************/

import java.util.*;

public class Application {

	public static void main(String args[]) {
		Account[] accountList;

		accountList = new Account[4];

		// build 4 different accounts in the same array
		accountList[0] = new CheckingAccount("John Smith", 1500.0);
		accountList[1] = new SavingAccount("William Hurt", 1200.0);
		accountList[2] = new CDAccount("Woody Allison", 1000.0);
		accountList[3] = new LoanAccount("Judi Foster", -1500.0);

		// compute interest for all accounts
		for (int count = 0; count < accountList.length; count++) {
			double newBalance;
			try {
				newBalance = accountList[count].computeInterest();
				System.out.println("Account <" + accountList[count].name()
						+ "> now has $" + newBalance + " balance\n");
			} catch (Exception e) {
				stdExceptionPrinting(e, accountList[count].balance());
			}
		}

		// compute past interest for all accounts
		for (int count = 0; count < accountList.length; count++) {
			double newBalance;
			Date d = new Date(10);
			try {
				newBalance = accountList[count].computeInterest(d); // Exception
				System.out.println("Account <" + accountList[count].name()
						+ "> now has $" + newBalance + " balance\n");
			} catch (Exception e) {
				stdExceptionPrinting(e, accountList[count].balance());
			}
		}

		DepositableAccount da = new SavingAccount("Rose Angle", 5000);
		try {
			da.deposit(100);
			da.deposit(100);
			da.deposit(100);
			da.deposit(100); // fee
			da.deposit(100); // fee
			System.out.println("Account <" + da.name() + "> now has $"
					+ da.balance() + " balance\n");
		} catch (Exception e) {
			stdExceptionPrinting(e, da.balance());
		}

		da = new LoanAccount("Angel Beta", -500);
		try {
			da.deposit(100);
			da.deposit(100);
			da.deposit(100);
			da.deposit(100); // fee
			da.deposit(100); // fee
			System.out.println("Account <" + da.name() + "> now has $"
					+ da.balance() + " balance\n");
			da.deposit(100); // Exception
		} catch (Exception e) {
			stdExceptionPrinting(e, da.balance());
		}

		da = new CheckingAccount("Lisa Check", 5000);
		try {
			da.deposit(100);
			da.deposit(100);
			da.deposit(100);
			da.deposit(100);
			da.deposit(100);
			System.out.println("Account <" + da.name() + "> now has $"
					+ da.balance() + " balance\n");
		} catch (Exception e) {
			stdExceptionPrinting(e, da.balance());
		}

		da = null;

		WithdrawableAccount wa = new SavingAccount("Vyv Unu", 5000);
		try {
			wa.withdraw(1000);
			wa.withdraw(1000);
			wa.withdraw(1000);
			System.out.println("Account <" + wa.name() + "> now has $"
					+ wa.balance() + " balance\n");
			wa.withdraw(1000); // fee
			wa.withdraw(1000); // Exception
		} catch (Exception e) {
			stdExceptionPrinting(e, wa.balance());
		}

		wa = new CDAccount("Charo Dead", 5000);
		try {
			wa.withdraw(1000); // fee
			wa.withdraw(1000); // fee
			wa.withdraw(1000); // fee
			System.out.println("Account <" + wa.name() + "> now has $"
					+ wa.balance() + " balance\n");
		} catch (Exception e) {
			stdExceptionPrinting(e, wa.balance());
		}

		wa = new CheckingAccount("Chex Dead", 5000);
		try {
			wa.withdraw(1000);
			wa.withdraw(1000);
			wa.withdraw(1000);
			wa.withdraw(1000);
			System.out.println("Account <" + wa.name() + "> now has $"
					+ wa.balance() + " balance\n");
			wa.withdraw(10); // Exception
		} catch (Exception e) {
			stdExceptionPrinting(e, wa.balance());
		}

		wa = null;

		FullFunctionalAccount fa = new SavingAccount("Sasa Full", 5000);
		try {
			fa.withdraw(2000);
			fa.withdraw(1000);
			fa.withdraw(1000);
			System.out.println("Account <" + fa.name() + "> now has $"
					+ fa.balance() + " balance\n");
			fa.deposit(100); // fee
			fa.withdraw(1000); // fee
			System.out.println("Account <" + fa.name() + "> now has $"
					+ fa.balance() + " balance\n");
		} catch (Exception e) {
			stdExceptionPrinting(e, fa.balance());
		}

		fa = new CheckingAccount("Zen Full", 5000);
		try {
			fa.deposit(1000);
			fa.withdraw(2000);
			fa.withdraw(1000);
			fa.withdraw(1000);
			System.out.println("Account <" + fa.name() + "> now has $"
					+ fa.balance() + " balance\n");
			fa.withdraw(1000);
			System.out.println("Account <" + fa.name() + "> now has $"
					+ fa.balance() + " balance\n");
			fa.withdraw(2000); // Exception
		} catch (Exception e) {
			stdExceptionPrinting(e, fa.balance());
		}

		fa = null;

		SavingAccount sa = new SavingAccount("Sasa Sa", 5000);
		try {
			Date d = new Date(10);
			sa.withdraw(2000, d);
			sa.withdraw(1000, d);
			sa.withdraw(1000, d);
			sa.withdraw(100, d); // fee
			System.out.println("Account <" + sa.name() + "> now has $"
					+ sa.balance() + " balance\n");
			
			sa.withdraw(100);
			System.out.println("Account <" + sa.name() + "> now has $"
					+ sa.balance() + " balance\n");
		} catch (Exception e) {
			stdExceptionPrinting(e, sa.balance());
		}

	}

	static void stdExceptionPrinting(Exception e, double balance) {
		System.out.println("EXCEPTION: Banking system throws a " + e.getClass()
				+ " with message: \n\t" + "MESSAGE: " + e.getMessage());
		System.out.println("\tAccount balance remains $" + balance + "\n");
	}
}