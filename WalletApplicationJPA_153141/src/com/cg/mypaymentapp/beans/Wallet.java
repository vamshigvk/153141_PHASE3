package com.cg.mypaymentapp.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wallet")
public class Wallet implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private BigDecimal balance;

	public Wallet(int id) {
		super();
		this.id = id;
	}

	public Wallet(BigDecimal amount) {
		this.balance = amount;
	}

	public Wallet() {
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return ", balance=" + balance;
	}
	
}
