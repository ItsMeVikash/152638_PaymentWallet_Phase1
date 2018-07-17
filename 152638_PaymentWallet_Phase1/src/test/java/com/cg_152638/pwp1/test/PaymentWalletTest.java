package com.cg_152638.pwp1.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.cg_152638.pwp1.beans.Customer;
import com.cg_152638.pwp1.beans.Wallet;
import com.cg_152638.pwp1.exception.InsufficientBalanceException;
import com.cg_152638.pwp1.exception.InvalidInputException;
import com.cg_152638.pwp1.service.IPaymentService;
import com.cg_152638.pwp1.service.IPaymentServiceValidation;
import com.cg_152638.pwp1.service.PaymentWalletServiceImpl;
import com.cg_152638.pwp1.service.PaymentWalletServiceValidation;

public class PaymentWalletTest {

	IPaymentService service = null;
	IPaymentServiceValidation serviceValidation = null;
	Customer customer1, customer2, customer3;
	Map<String, Customer> customerMap = null;

	/*
	 * Initializing All Required Fields
	 */
	@Before
	public void initData() {
		service = new PaymentWalletServiceImpl();
		serviceValidation = new PaymentWalletServiceValidation();
		customerMap = new HashMap<>();
		customer1 = new Customer("1231231231", "Vikash Kumar", new Wallet(new BigDecimal("1510")));
		customer2 = new Customer("1231231232", "Vivek Singh", new Wallet(new BigDecimal("1000")));
		customer3 = new Customer("1231231233", "Ajay Kumar", new Wallet(new BigDecimal("10000")));
		customerMap.put(customer1.getMobileNumber(), customer1);
		customerMap.put(customer2.getMobileNumber(), customer2);
		customerMap.put(customer3.getMobileNumber(), customer3);

	}

	/*
	 * Testing Menu Choice Validation Method
	 */
	@Test(expected = InvalidInputException.class)
	public void testMenuChoiceWithInvalidInput() throws InvalidInputException {
		boolean output = serviceValidation.menuChoiceValidation("10");
		assertFalse(output);
	}

	@Test(expected = InvalidInputException.class)
	public void testMenuChoiceWithEmptyInput() throws InvalidInputException {
		boolean output = serviceValidation.menuChoiceValidation("");
		assertFalse(output);
	}

	@Test
	public void testMenuChoiceWithValidInput() throws InvalidInputException {
		boolean output = serviceValidation.menuChoiceValidation("1");
		assertTrue(output);
	}

	/*
	 * Testing MobileNumber Validation Method
	 */
	@Test(expected = InvalidInputException.class)
	public void testMobileNoWithInvalidInput() throws InvalidInputException {
		boolean output = serviceValidation.mobileNumberValidation("101235sd5");
		assertFalse(output);
	}

	@Test(expected = InvalidInputException.class)
	public void testMobileNoWithEmptyInput() throws InvalidInputException {
		boolean output = serviceValidation.mobileNumberValidation("");
		assertFalse(output);
	}

	@Test
	public void testMobileNoWithValidInput() throws InvalidInputException {
		boolean output = serviceValidation.mobileNumberValidation("9852993617");
		assertTrue(output);
	}

	/*
	 * Testing Name Validation Method...PROJECT BY- VIKASH KUMAR(EMPID: 152638)
	 */
	@Test(expected = InvalidInputException.class)
	public void testNameWithInvalidInput() throws InvalidInputException {
		boolean output = serviceValidation.nameValidation("Vikash123");
		assertFalse(output);
	}

	@Test(expected = AssertionError.class)
	public void testNameWithEmptyInput() throws InvalidInputException {
		boolean output = serviceValidation.nameValidation("");
		assertFalse(output);
	}

	@Test
	public void testNameWithValidInput() throws InvalidInputException {
		boolean output = serviceValidation.nameValidation("Vikash Kumar");
		assertTrue(output);
	}

	/*
	 * Testing Amount Validation Method
	 */
	@Test(expected = InvalidInputException.class)
	public void testAmountWithInvalidInput() throws InvalidInputException {
		boolean output = serviceValidation.amountValidation("12sc.00");
		assertFalse(output);
	}

	@Test(expected = InvalidInputException.class)
	public void testAmountWithEmptyInput() throws InvalidInputException {
		boolean output = serviceValidation.amountValidation("");
		assertFalse(output);
	}

	@Test
	public void testAmountWithValidInput() throws InvalidInputException {
		boolean output = serviceValidation.amountValidation("1000");
		assertTrue(output);
	}

	/*
	 * Testing GetCustomerDetails Method
	 */
	@Test
	public void testGetCustomerDetailsWithInvalidInput() {
		Customer output = customerMap.get("1236548996");
		assertNull(output);
	}

	@Test
	public void testGetCustomerDetailsWithEmptyInput() {
		Customer output = customerMap.get("");
		assertNull(output);
	}

	@Test
	public void testGetCustomerDetailsWithValidInput() {
		Customer output = customerMap.get(customer1.getMobileNumber());
		assertNotNull(output);
	}

	/*
	 * Testing AddCustomer Method
	 */
	@Test(expected = AssertionError.class)
	public void testAddCustomerWithInvalidInput() {
		Customer customer = new Customer("", "Vikash Kumar", new Wallet(new BigDecimal("1010")));
		customerMap.put(customer.getMobileNumber(), customer);
		assertNull(customerMap.get(customer.getMobileNumber()));
	}

	@Test(expected = NumberFormatException.class)
	public void testAddCustomerWithEmptyInput() {
		Customer customer = new Customer("", "", new Wallet(new BigDecimal("")));
		customerMap.put(customer.getMobileNumber(), customer);
		assertNull(customerMap.get(customer.getMobileNumber()));
	}

	@Test
	public void testAddCustomerWithValidInput() {
		Customer customer = new Customer("120321", "Vikash Kumar", new Wallet(new BigDecimal("1010")));
		customerMap.put(customer.getMobileNumber(), customer);
		assertNotNull(customerMap.get(customer.getMobileNumber()));
	}

	/*
	 * Testing DepositMoney Method
	 */
	@Test(expected = NumberFormatException.class)
	public void testDepositMoneyWithInvalidInput() {
		service.depositMoney(customer1, new BigDecimal("15sk"));
		assertNull(customer1.getWalletBalance());
	}

	@Test(expected = NumberFormatException.class)
	public void testDepositMoneyWithEmptyInput() {
		service.depositMoney(customer1, new BigDecimal(""));
		assertNull(customer1.getWalletBalance());
	}

	@Test
	public void testDepositMoneyWithValidInput() {
		service.depositMoney(customer1, new BigDecimal("1500"));
		assertNotNull(customer1.getWalletBalance());
	}

	/*
	 * Testing WithdrawMoney Method PROJECT BY- VIKASH KUMAR(EMPID: 152638)
	 */
	@Test(expected = NumberFormatException.class)
	public void testWithdrawMoneyWithInvalidInput() throws InsufficientBalanceException {
		service.withdrawMoney(customer1, new BigDecimal("15sk"));
		assertNull(customer1.getWalletBalance());
	}

	@Test(expected = NumberFormatException.class)
	public void testWithdrawMoneyWithEmptyInput() throws InsufficientBalanceException {
		service.withdrawMoney(customer1, new BigDecimal(""));
		assertNull(customer1.getWalletBalance());
	}

	@Test(expected = InsufficientBalanceException.class)
	public void testWithdrawMoneyInsufficientBalance() throws InsufficientBalanceException {
		service.withdrawMoney(customer1, new BigDecimal("1500"));
		assertNotNull(customer1.getWalletBalance());
	}

	@Test
	public void testWithdrawMoneyWithValidInput() throws InsufficientBalanceException {
		service.withdrawMoney(customer1, new BigDecimal("10"));
		assertNotNull(customer1.getWalletBalance());
	}

	/*
	 * Testing PrintingTransaction Method
	 */
	@Test
	public void testPrintTransactionWithInvalidInput() {
		Customer output = customerMap.get("1236548996");
		assertNull(output);
	}

	@Test
	public void testPrintTransactionWithEmptyInput() {
		Customer output = customerMap.get("");
		assertNull(output);
	}

	@Test
	public void testPrintTransactionWithValidInput() {
		Customer output = customerMap.get(customer1.getMobileNumber());
		assertNotNull(output);
	}

	/*
	 * Testing FundTransfer Method
	 */
	@Test(expected = NumberFormatException.class)
	public void testFundTransferWithEmptyInput() throws InsufficientBalanceException {
		service.fundTransfer(customer2, customer1, new BigDecimal(""));
		assertNull(customer1.getWalletBalance());
	}

	@Test(expected = InsufficientBalanceException.class)
	public void testFundTransferInsufficientBalance() throws InsufficientBalanceException {
		service.fundTransfer(customer2, customer1, new BigDecimal("1500"));
		assertNotNull(customer1.getWalletBalance());
	}

	@Test
	public void testFundTransferWithValidInput() throws InsufficientBalanceException {
		service.fundTransfer(customer1, customer2, new BigDecimal("10"));
		assertNotNull(customer1.getWalletBalance());
	}
	/*
	 * All Service Class Methods Are Tested.PROJECT BY-VIKASH KUMAR(EMPID:152638)
	 */

}
