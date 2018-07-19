
package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transaction;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;
import com.cg.mypaymentapp.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {
	long millis = System.currentTimeMillis();
	private WalletRepo repo = new WalletRepoImpl();
	Customer customer;

	public Customer createAccount(String name, String mobileNo, BigDecimal amount) {
		repo.startTransaction();
		if (!isValidName(name) || !isValidMobile(mobileNo) || !isValidAmount(amount)) {
			throw new InvalidInputException("Sorry , your details are incorrect");
		}
		Customer customer = new Customer(name, mobileNo, new Wallet(amount));
		if (repo.save(customer) == true)
			repo.commitTransaction();
		return customer;
	}

	public Customer showBalance(String mobileNo) {
		repo.startTransaction();
		if (!isValidMobile(mobileNo)) {
			throw new InvalidInputException("Invalid Mobile number");
		} else {
			Customer customer = repo.findOne(mobileNo);
			if (customer != null) {
				repo.commitTransaction();
				return customer;
			} else
				throw new InvalidInputException("account with mobile number not found ");
		}
	}

	public Customer fundTransfer(String sourceMobileNo, String destinationMobileNo, BigDecimal amount) {
		repo.startTransaction();
		if (!isValidMobile(sourceMobileNo) || !isValidMobile(destinationMobileNo) || !isValidAmount(amount)) {
			throw new InvalidInputException("Sorry , your details are incorrect");
		}
		Customer sourceCustomer = repo.findOne(sourceMobileNo);
		Customer destinationCustomer = repo.findOne(destinationMobileNo);

		if (sourceCustomer != null && destinationCustomer != null) {
			Wallet sourceBalance = sourceCustomer.getWallet();
			Wallet destinationBalance = destinationCustomer.getWallet();
			if (sourceBalance.getBalance().compareTo(amount) > 0) {
				BigDecimal remainingBalance = sourceBalance.getBalance().subtract(amount);
				BigDecimal addedBalance = destinationBalance.getBalance().add(amount);
				sourceBalance.setBalance(remainingBalance);
				destinationBalance.setBalance(addedBalance);
				String transactionType = "Debit";
				Date date = new java.sql.Date(millis);
				Transaction sourceTransaction = new Transaction(sourceMobileNo, transactionType, amount, date);
				Transaction destinationTransaction = new Transaction(destinationMobileNo, transactionType, amount,
						date);
				repo.update(sourceCustomer, sourceTransaction);
				repo.update(destinationCustomer, destinationTransaction);
				repo.commitTransaction();
				return sourceCustomer;
			} else {
				throw new InsufficientBalanceException("insufficient balance");

			}
		} else {
			throw new InvalidInputException("account with mobile number not found ");
		}

	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) {
		repo.startTransaction();
		if (!isValidMobile(mobileNo) || !isValidAmount(amount)) {
			throw new InvalidInputException("Sorry , your details are incorrect");
		}
		Customer customer = repo.findOne(mobileNo);
		if (customer != null) {
			Wallet balance = customer.getWallet();
			balance.setBalance(balance.getBalance().add(amount));

			String transactionType = "Debit";
			Date date = new java.sql.Date(millis);
			Transaction transaction = new Transaction(mobileNo, transactionType, amount, date);
			repo.update(customer, transaction);
			repo.commitTransaction();
			return customer;
		} else {
			throw new InvalidInputException("account with mobile number not found ");
		}
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
		repo.startTransaction();
		if (!isValidMobile(mobileNo) || !isValidAmount(amount)) {
			throw new InvalidInputException("Sorry , your details are incorrect");
		}
		Customer customer = repo.findOne(mobileNo);
		if (customer != null) {
			Wallet balance = customer.getWallet();
			if (balance.getBalance().compareTo(amount) > 0) {
				BigDecimal withdrawedBalance = balance.getBalance().subtract(amount);
				balance.setBalance(withdrawedBalance);
				BigDecimal totalBalance = customer.getWallet().getBalance();
				String transactionType = "Debit";
				Date date = new java.sql.Date(millis);
				Transaction transaction = new Transaction(mobileNo, transactionType, amount, date);
				repo.update(customer, transaction);
				repo.commitTransaction();
				return customer;
			} else {
				throw new InsufficientBalanceException("Insufficient balance ");
			}
		} else {
			throw new InvalidInputException("account with mobile number not found ");
		}
	}

	private boolean isValidMobile(String mobileNo) {
		if (String.valueOf(mobileNo).matches("[1-9][0-9]{9}")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isValidAmount(BigDecimal amount) {
		BigDecimal val = new BigDecimal("0");
		if (amount.compareTo(val) > 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isValidName(String name) {
		if (name.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public ArrayList<Transaction> miniStatement(String mobileno) {
		ArrayList<Transaction> transaction = repo.miniStatement(mobileno);
		return transaction;
	}
}