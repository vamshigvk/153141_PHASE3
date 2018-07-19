package com.cg.mypaymentapp.repo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.cg.mypaymentapp.util.JPAUtil;
import com.cg.mypaymentapp.*;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transaction;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletServiceImpl;

public class WalletRepoImpl implements WalletRepo {
	Customer customer = null;
	private EntityManager entityManager;
	ArrayList<Transaction> transaction;
	
	public WalletRepoImpl() {
		entityManager = JPAUtil.getEntityManager();
	}

	public boolean save(Customer customer) {
		try {// id will be passed when object is passed
			entityManager.persist(customer);
		} catch (Exception e) {
			throw new InvalidInputException(e);
		}
		return true;
	}

	public Customer findOne(String mobileNo) {
		return entityManager.find(Customer.class, mobileNo);
	}

	@Override
	public void startTransaction() {
		entityManager.getTransaction().begin();
	}

	@Override
	public void commitTransaction() {
		entityManager.getTransaction().commit();
	}

	@Override
	public void update(Customer customer, Transaction transaction) {
		entityManager.merge(customer);
		entityManager.persist(transaction);
	}

	@Override
	public ArrayList<Transaction> miniStatement(String mobileno) {
		String qstr = "select transaction from Transaction transaction where transaction.mobileno=:pmobileno";
		TypedQuery<Transaction> query = entityManager.createQuery(qstr, Transaction.class);
		query.setParameter("pmobileno", mobileno);
		return (ArrayList<Transaction>) query.getResultList();
	}
}