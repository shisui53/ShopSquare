package com.masai.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.masai.entities.Customer;
import com.masai.entities.Product;
import com.masai.entities.Transaction;
import com.masai.exceptions.ProductException;
import com.masai.exceptions.TransactionException;

public class ProductServicesImpl implements ProductService {

	@Override
	public String addProduct(Product prod, Map<Integer, Product> products) {
		// TODO Auto-generated method stub
//as our ids are always unique thats why directly putting into products
		products.put(prod.getId(), prod);

		return "\n Product Added Successfully \n";

	}

	@Override
	public void viewAllProducts(Map<Integer, Product> products) throws ProductException {
		// TODO Auto-generated method stub
		if (products != null && products.size() > 0) {
			for (Map.Entry<Integer, Product> me : products.entrySet()) {
				System.out.println(me.getValue());
			}

		} else {
			throw new ProductException("Product List is Empty \n");
		}
	}

	@Override
	public void deleteProduct(int id, Map<Integer, Product> products) throws ProductException {

		// System.out.println(products);
		if (products != null && products.size() > 0) {

			if (products.containsKey(id)) {
				products.remove(id);
				System.out.println("\n Product Deleted Successfully \n");

			} else {
				throw new ProductException("Product not Found \n");
			}

		} else {
			throw new ProductException("Product List is Empty \n");
		}

	}

	@Override
	public String updateProduct(int id, Product prod, Map<Integer, Product> products) throws ProductException {

		if (products != null && products.size() > 0) {

			if (products.containsKey(id)) {
				products.put(id, prod);
				return "\n Product has Successfully Updated \n";
			} else {
				throw new ProductException("Product not found \n");
			}

		} else {
			throw new ProductException("Product List is Empty \n");
		}

	}

}
