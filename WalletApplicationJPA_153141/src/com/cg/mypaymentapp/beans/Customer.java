package com.cg.mypaymentapp.beans;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.engine.Cascade;

@Entity
@Table(name = "customers")
// @NamedQueries(@NamedQuery(name = "getAllTransactions", query = "SELECT * FROM
// Customers where mobileno=?"))
public class Customer implements Serializable {
	private String name;
	@Id
	private String mobileNo;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private Wallet wallet;

	public Customer(String mobileNo) {
		super();
		this.mobileNo = mobileNo;
	}

	public Customer() {
		super();
	}

	public Customer(String name2, String mobileNo2, Wallet wallet2) {
		this.name = name2;
		mobileNo = mobileNo2;
		wallet = wallet2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	@Override
	public String toString() {
		return "Customer name=" + name + ", mobileNo=" + mobileNo + wallet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mobileNo == null) ? 0 : mobileNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (mobileNo == null) {
			if (other.mobileNo != null)
				return false;
		} else if (!mobileNo.equals(other.mobileNo))
			return false;
		return true;
	}
}