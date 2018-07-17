package com.cg_152638.pwp1.repo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.cg_152638.pwp1.beans.Customer;

public class PaymentWalletRepoImpl implements IPaymentRepo {

	private static Map<String, Customer> customerMap = null;
	private static Map<String, StringBuilder> printingList = null;
	static {
		customerMap = new HashMap<>();
		printingList = new HashMap<String, StringBuilder>();
	}

	@Override
	public Customer getCustomerDetails(String mobileNumber) {
		return customerMap.get(mobileNumber);
	}

	@Override
	public boolean addCustomer(Customer newCustomer) {
		boolean result = false;
		if (customerMap.get(newCustomer.getMobileNumber()) == null) {
			customerMap.put(newCustomer.getMobileNumber(), newCustomer);
			StringBuilder builder = new StringBuilder("Account Created on \t" + LocalDateTime.now()
					+ "\n--------------------------------------------------------------------------\n"
					+ String.format("%-10s  %-20s%-30s %-20s", "Amount ", "Transaction Type", "Date & Time", "Balance")
					+ "\n--------------------------------------------------------------------------\n");
			printingList.put(newCustomer.getMobileNumber(), builder);
			result = true;
		}
		return result;
	}

	@Override
	public void depositMoney(Customer customer, BigDecimal depositableAmount) {
		if (customerMap.get(customer.getMobileNumber()) != null) {
			customer.setWalletBalance(customer.getWalletBalance().add(depositableAmount));
			StringBuilder builder = new StringBuilder();
			builder.append(String.format("\n" + "%-10s  %-20s%-30s %-1s", "\u20B9 " + depositableAmount, "Deposited",
					LocalDateTime.now(), "\u20B9 " + customer.getWalletBalance()));
			printingList.put(customer.getMobileNumber(), printingList.get(customer.getMobileNumber()).append(builder));
		}
	}

	@Override
	public void withdrawMoney(Customer customer, BigDecimal withdrawableAmount) {
		if (customerMap.get(customer.getMobileNumber()) != null) {
			customer.setWalletBalance(customer.getWalletBalance().subtract(withdrawableAmount));
			StringBuilder builder = new StringBuilder();
			builder.append(String.format("\n" + "%-10s  %-20s%-30s %-1s", "\u20B9 " + withdrawableAmount, "Withdrawn",
					LocalDateTime.now(), "\u20B9 " + customer.getWalletBalance()));
			printingList.put(customer.getMobileNumber(), printingList.get(customer.getMobileNumber()).append(builder));
		}
	}

	@Override
	public StringBuilder printTransaction(String mobileNumber) {
		return printingList.get(mobileNumber);
	}

	@Override
	public void fundTransfer(Customer sendCustomer, Customer recCustomer, BigDecimal transferAmount) {
		if (customerMap.get(sendCustomer.getMobileNumber()) != null) {
			if (customerMap.get(recCustomer.getMobileNumber()) != null) {
				sendCustomer.setWalletBalance(sendCustomer.getWalletBalance().subtract(transferAmount));
				recCustomer.setWalletBalance(recCustomer.getWalletBalance().add(transferAmount));
				StringBuilder builder = new StringBuilder();
				builder.append(String.format("\n" + "%-10s  %-20s%-30s %-1s", "\u20B9  " + transferAmount,
						"Trans.-> "+recCustomer.getMobileNumber(), LocalDateTime.now(), "\u20B9 " + sendCustomer.getWalletBalance()));
				printingList.put(sendCustomer.getMobileNumber(),
						printingList.get(sendCustomer.getMobileNumber()).append(builder));

				// receiver printingList
				StringBuilder recBuilder = new StringBuilder();
				recBuilder.append(String.format("\n" + "%-10s  %-20s%-30s %-1s", "\u20B9  " + transferAmount,
						"Trans.<- "+sendCustomer.getMobileNumber(), LocalDateTime.now(), "\u20B9 " + recCustomer.getWalletBalance()));
				printingList.put(recCustomer.getMobileNumber(),
						printingList.get(recCustomer.getMobileNumber()).append(recBuilder));
			}
		}
	}

}
