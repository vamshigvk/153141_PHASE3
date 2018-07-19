package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.cg.mypaymentapp.beans.Transaction;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.exception.InvalidInputException;

public interface WalletRepo {

	public boolean save(Customer customer) throws InvalidInputException;

	public Customer findOne(String mobileNo) throws InvalidInputException;

	public void update(Customer customer, Transaction transaction);

	public void startTransaction();

	public void commitTransaction();

	public ArrayList<Transaction> miniStatement(String mobileno);
}
