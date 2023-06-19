package com.masai;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.masai.entities.Customer;
import com.masai.entities.Product;
import com.masai.entities.Transaction;
import com.masai.exceptions.DuplicateDataException;
import com.masai.exceptions.InvalidDetailsException;
import com.masai.exceptions.ProductException;
import com.masai.exceptions.TransactionException;
import com.masai.service.CustomerService;
import com.masai.service.CustomerServiceImpl;
import com.masai.service.ProductService;
import com.masai.service.ProductServicesImpl;
import com.masai.service.TransactionService;
import com.masai.service.TransactionServiceImpl;
import com.masai.utility.Admin;
import com.masai.utility.FileExists;
import com.masai.utility.IDGeneration;

public class Main {

	// admin functionality
	private static void adminFunctionality(Scanner sc, Map<Integer, Product> products, Map<String, Customer> customers,
			List<Transaction> transactions) throws InvalidDetailsException, ProductException, TransactionException {
		// admin login

		adminLogin(sc);

		ProductService prodService = new ProductServicesImpl();
		CustomerService cusService = new CustomerServiceImpl();
		TransactionService trnsactionService = new TransactionServiceImpl();
		int choice = 0;
		try {
			do {
				System.out.println("Please Select one Option");
				System.out.println("Press 1 View all the product");
				System.out.println("Press 2 View all customers");
				System.out.println("Press 3 View all transactions");
				System.out.println("Press 4 Log out");
				choice = sc.nextInt();

				switch (choice) {
				case 1:
					adminViewAllProducts(products, prodService);
					break;
				case 2:
					adminViewAllCustomers(customers, cusService);
					break;
				case 3:
					adminViewAllTransactions(transactions, trnsactionService);
					break;
				case 4:
					System.out.println("Admin has Successfully Logout");
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + choice);
				}

			} while (choice <= 6);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void adminLogin(Scanner sc) throws InvalidDetailsException {

		System.out.println("Enter the Username");
		String userName = sc.next();
		System.out.println("Enter the Password");
		String password = sc.next();
		if (userName.equals(Admin.username) && password.equals(Admin.password)) {

			System.out.println("Successfully login");
		} else {
			throw new InvalidDetailsException("Invalid Admin Credentials");
		}
	}

	public static void adminViewAllProducts(Map<Integer, Product> products, ProductService prodService)
			throws ProductException {
		prodService.viewAllProducts(products);
	}

	public static void adminViewAllCustomers(Map<String, Customer> customers, CustomerService cusService)
			throws ProductException {
		List<Customer> list = cusService.viewAllCustomers(customers);

		for (Customer c : list) {
			System.out.println(c);
		}
	}

	public static void adminViewAllTransactions(List<Transaction> transactions, TransactionService trnsactionService)
			throws TransactionException {
		List<Transaction> allTransactions = trnsactionService.viewAllTransactions(transactions);

		for (Transaction tr : allTransactions) {
			System.out.println(tr);
		}

	}

	private static void vendorsFunctionality(Scanner sc, Map<Integer, Product> products, Map<String, Customer> customers,
			List<Transaction> transactions) throws InvalidDetailsException, ProductException, TransactionException {

		ProductService prodService = new ProductServicesImpl();
		CustomerService cusService = new CustomerServiceImpl();
		TransactionService trnsactionService = new TransactionServiceImpl();
		int choice = 0;
		try {
			do {
				System.out.println("Please Select one Option");
				System.out.println("Press 1 Add the product");
				System.out.println("Press 2 View all the product");
				System.out.println("Press 3 Delete the product");
				System.out.println("Press 4 Update the product");
				System.out.println("Press 5 View all transactions");
				choice = sc.nextInt();

				switch (choice) {
				case 1:
					String added = vendorsAddProduct(sc, products, prodService);
					System.out.println(added);
					break;
				case 2:
					vendorsViewAllProducts(products, prodService);
					break;
				case 3:
					vendorsDeleteProduct(sc, products, prodService);
					break;
				case 4:
					String upt = vendorsUpdateProduct(sc, products, prodService);
					System.out.println(upt);
					break;
				case 5:
					adminViewAllTransactions(transactions, trnsactionService);
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + choice);
				}

			} while (choice <= 6);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static String vendorsAddProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService) {

		String str = null;
		System.out.println("Enter the Product Details");
		System.out.println("Enter the Product Name");
		String name = sc.next();
		System.out.println("Enter the Product Quantity");
		int qty = sc.nextInt();
		System.out.println("Enter the Product Price per Quantity");
		double price = sc.nextDouble();
		System.out.println("Enter the Product Category");
		String cate = sc.next();

		Product prod = new Product(IDGeneration.generateId(), name, qty, price, cate);

		str = prodService.addProduct(prod, products);// considering all details are valid

		return str;

	}

	public static void vendorsViewAllProducts(Map<Integer, Product> products, ProductService prodService)
			throws ProductException {
		prodService.viewAllProducts(products);
	}

	public static void vendorsDeleteProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService)
			throws ProductException {

		System.out.println("Enter the Id of Product You want to Delete");
		int id = sc.nextInt();
		prodService.deleteProduct(id, products);
	}

	public static String vendorsUpdateProduct(Scanner sc, Map<Integer, Product> products, ProductService prodService)
			throws ProductException {
		String result = null;
		System.out.println("Enter the Id of the Product which you have to Update");
		int id = sc.nextInt();
		System.out.println("Enter the Updated details ");

		System.out.println("Enter the Product Name");
		String name = sc.next();

		System.out.println("Enter the Product Quantity");
		int qty = sc.nextInt();

		System.out.println("Enter the Product Price per Piece");
		double price = sc.nextDouble();

		System.out.println("Enter the Product Category");
		String cate = sc.next();

		Product update = new Product(id, name, qty, price, cate);

		result = prodService.updateProduct(id, update, products);
		return result;
	}

	public static void vendoViewAllTransactions(List<Transaction> transactions, TransactionService trnsactionService)
			throws TransactionException {
		List<Transaction> allTransactions = trnsactionService.viewAllTransactions(transactions);

		for (Transaction tr : allTransactions) {
			System.out.println(tr);
		}

	}

	// customer functionality
	public static void customerFunctionality(Scanner sc, Map<String, Customer> customers,
			Map<Integer, Product> products, List<Transaction> transactions)
			throws InvalidDetailsException, TransactionException {

		CustomerService cusService = new CustomerServiceImpl();
		ProductService prodService = new ProductServicesImpl();
		TransactionService trnsactionService = new TransactionServiceImpl();

		// Customer login
		System.out.println("Enter the following details to login");
		System.out.println("Enter the Email");
		String email = sc.next();
		System.out.println("Enter the Password");
		String pass = sc.next();
		customerLogin(email,pass, customers, cusService);

		try {
			int choice = 0;
			do {
				System.out.println("Please Select one Option");
				System.out.println("Press 1 View All Products");
				System.out.println("Press 2 Buy a Product");
				System.out.println("Press 3 Add Money to a Wallet");
				System.out.println("Press 4 View Wallet balance");
				System.out.println("Press 5 View My Details");
				System.out.println("Press 6 View My Transactions");
				System.out.println("Press 7 Logout");
				choice = sc.nextInt();

				switch (choice) {
				case 1:
					customerViewAllProducts(products, prodService);
					break;
				case 2:
					String result = customerBuyProduct(sc, email, products, customers, transactions, cusService);
					System.out.println(result);
					break;
				case 3:
					String moneyAdded = customerAddMoneyToWallet(sc, email, customers, cusService);
					System.out.println(moneyAdded);
					break;
				case 4:
					double walletBalance = customerViewWalletBalance(email, customers, cusService);
					System.out.println("Wallet balance is: " + walletBalance);
					break;
				case 5:
					customerViewMyDetails(email, customers, cusService);
					break;
				case 6:
					customerViewCustomerTransactions(email, transactions, trnsactionService);
					break;
				case 7:
					System.out.println("You have Successsfully Logout");
					break;
				default:
					System.out.println("Invalid Option");
					break;
				}

			} while (choice <= 6);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void customerSignup(Scanner sc, Map<String, Customer> customers) throws DuplicateDataException {
		System.out.println("Enter the following Details to Signup");
		System.out.println("Enter your Name ( Ex. abc)");
		String name = sc.next();
		System.out.println("Create Password ( Ex. abc@123)");
		String pass = sc.next();
		System.out.println("Enter the Address ( Ex. Kop,Maharashtra)");
		String address = sc.next();
		System.out.println("Enter the Email id ( Ex. abc@gmail.com)");
		String email = sc.next();
		System.out.println("Enter the Balance to be added into the wallet ( Minimum Rs.100 )");
		double balance = sc.nextDouble();
		Customer cus = new Customer(balance, name, pass, address, email);

		CustomerService cusService = new CustomerServiceImpl();
		cusService.signUp(cus, customers);
		System.out.println("You have Succefully sign up");

	}

	public static void customerLogin(String email,String pass, Map<String, Customer> customers, CustomerService cusService)
			throws InvalidDetailsException {
		cusService.login(email, pass,customers);
		System.out.println("You have Successfully login");

	}

	public static void customerViewAllProducts(Map<Integer, Product> products, ProductService prodService)
			throws ProductException {
		prodService.viewAllProducts(products);
	}

	public static String customerBuyProduct(Scanner sc, String email, Map<Integer, Product> products,
			Map<String, Customer> customers, List<Transaction> transactions, CustomerService cusService)
			throws InvalidDetailsException, ProductException {
		System.out.println("Enter the Product Id");
		int id = sc.nextInt();
		System.out.println("Enter the Quantity you want to Purchase");
		int qty = sc.nextInt();
		cusService.buyProduct(id, qty, email, products, customers, transactions);

		return "You have Successfully Purchased the product";

	}

	public static String customerAddMoneyToWallet(Scanner sc, String email, Map<String, Customer> customers,
			CustomerService cusService) {
		System.out.println("Please Enter the Amount");
		double money = sc.nextDouble();
		boolean added = cusService.addMoneyToWallet(money, email, customers);

		return "Amount: " + money + " Successfully Added to your Wallet";
	}

	public static double customerViewWalletBalance(String email, Map<String, Customer> customers,
			CustomerService cusService) {
		double walletBalance = cusService.viewWalletBalance(email, customers);
		return walletBalance;
	}

	public static void customerViewMyDetails(String email, Map<String, Customer> customers,
			CustomerService cusService) {
		Customer cus = cusService.viewCustomerDetails(email, customers);
		System.out.println("Name : " + cus.getUsername());
		System.out.println("Address : " + cus.getAddress());
		System.out.println("Email : " + cus.getEmail());
		System.out.println("Wallet Balance : " + cus.getWalletBalance());
	}

	public static void customerViewCustomerTransactions(String email, List<Transaction> transactions,
			TransactionService trnsactionService) throws TransactionException {
		List<Transaction> myTransactions = trnsactionService.viewCustomerTransactions(email, transactions);

		for (Transaction tr : myTransactions) {
			System.out.println(tr);
		}
	}

	
	
	public static void main(String[] args) {
//file check
		Map<Integer, Product> products = FileExists.productFile();
		Map<String, Customer> customers = FileExists.customerFile();
		List<Transaction> transactions = FileExists.transactionFile();
//		System.out.println(products.size());
//		System.out.println(customers.size());
//		System.out.println(transactions.size());

		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome , in Product Management System");

		try {

			int preference = 0;
			do {
				System.out.println("*******************************************************************************************");
				System.out.println("				WELCOME TO SHOPSQUARE");
				System.out.println("*******************************************************************************************");
				System.out.println("Press 1 Admin Login");
				System.out.println("Press 2 Vendors Login");
				System.out.println("Press 3 Customer Login");
				System.out.println("Press 4 Custome Signup");
				preference = sc.nextInt();
				switch (preference) {
				case 1:
					adminFunctionality(sc, products, customers, transactions);
					break;
				case 2:
					vendorsFunctionality(sc, products, customers, transactions);
					break;

				case 3:
					customerFunctionality(sc, customers, products, transactions);
					break;
				
				case 4:
					customerSignup(sc, customers);
					break;

				case 0:
					System.out.println("Successfully existed from the system");

					break;

				default:
					throw new IllegalArgumentException("Invalid Option3");
				}

			}

			while (preference != 0);

		} catch (Exception e) {

			System.out.println(e.getMessage());
		} finally {
			// serialization (finally always executed)
			try {
				ObjectOutputStream poos = new ObjectOutputStream(new FileOutputStream("Product.ser"));
				poos.writeObject(products);
				ObjectOutputStream coos = new ObjectOutputStream(new FileOutputStream("Customer.ser"));
				coos.writeObject(customers);

				ObjectOutputStream toos = new ObjectOutputStream(new FileOutputStream("Transactions.ser"));
				toos.writeObject(transactions);
			//	System.out.println("serialized..........");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}

	}

}
