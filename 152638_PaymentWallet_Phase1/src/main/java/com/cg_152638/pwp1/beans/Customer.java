package com.cg_152638.pwp1.beans;

import java.math.BigDecimal;

public class Customer {
	private String mobileNumber;
	private String name;
	private Wallet wallet;

	public Customer() {
		wallet = new Wallet();
	}

	public Customer(String mobileNumber, String name, Wallet wallet) {
		super();
		this.mobileNumber = mobileNumber;
		this.name = name;
		this.wallet = wallet;
	}

	public BigDecimal getWalletBalance() {
		return wallet.getBalance();
	}

	public void setWalletBalance(BigDecimal balance) {
		wallet.setBalance(balance);
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
