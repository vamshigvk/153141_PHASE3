package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transaction;

public interface WalletService {
	public Customer createAccount(String name, String mobileno, BigDecimal d);

	public Customer showBalance(String mobileno);

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount);

	public Customer depositAmount(String mobileNo, BigDecimal amount);

	public Customer withdrawAmount(String mobileNo, BigDecimal amount);

	public ArrayList<Transaction> miniStatement(String mobileno);

}
