package com.henrysgrocery.store;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.henrysgrocery.discount.Discount;
import com.henrysgrocery.discount.DiscountService;
import com.henrysgrocery.store.assets.Basket;
import com.henrysgrocery.store.assets.Product;
import com.henrysgrocery.store.assets.ProductCatalogue;
import com.henrysgrocery.store.assets.ProductType;

public class Store {
	
	public static Map<ProductType,Product> stockItems = ProductCatalogue.getProductsInStore();
	public static List<Discount> discounts = DiscountService.getDiscounts();
	public static Basket basket = new Basket();
	static Scanner scan = new Scanner(System.in);

	public static void main (String args[]) {
		
		System.out.println("********************* Welcome to Henry's Grocery Store *********************");
		System.out.println("Please enter number of Apples you want to buy: ");
		
		int numberOfApples = scan.nextInt();
		addProductToBasket(ProductType.APPLE, numberOfApples);
		
		System.out.println("Please enter number of loaves of Bread you want to buy: ");
		
		int quanityOfBread = scan.nextInt();
		addProductToBasket(ProductType.BREAD, quanityOfBread);
		
		System.out.println("How many bottles of Milk you want to buy: ");
		
		int numberOfMilkBottles = scan.nextInt();
		addProductToBasket(ProductType.MILK, numberOfMilkBottles);
		
		System.out.println("How any tins of Soup you want to buy: ");
		
		int numberOfSoupTins = scan.nextInt();
		addProductToBasket(ProductType.SOUP, numberOfSoupTins);
		
		System.out.println("Please enter day of purchase:");
		int dayOfMonth = scan.nextInt();
		System.out.println("Please enter month of purchase:");
		int month = scan.nextInt();
		System.out.println("Please enter year of purchase:");
		int year = scan.nextInt();
		LocalDate purchaseDate = LocalDate.of(year, month, dayOfMonth);
		scan.close();
		
		BigDecimal subTotal = basket.subTotal();
		System.out.println("Subtotal : "+ subTotal);
		BigDecimal discount = getTotalDiscount(basket, purchaseDate);
		System.out.println("Discount : "+ discount);
		
		System.out.println("Total after discount : "+ subTotal.subtract(discount));
		
		System.out.println("*********************** Thank you for shopping with us ***********************");
	}
	
	public static void addProductToBasket(ProductType type, int quantity) {
		for (int i=0; i<quantity; i++) {
			basket.add(stockItems.get(type).oneOf());
		}		
	}
	
	public static BigDecimal getTotalDiscount(Basket basket, LocalDate purchaseDate) {
		BigDecimal totalDiscount = BigDecimal.ZERO;
		for(Discount d: discounts) {
			if (d.isApplicable(basket, purchaseDate)) {
				totalDiscount= totalDiscount.add(d.getDiscount(basket));
			}
		}
		return totalDiscount;
	}
	
	
}
