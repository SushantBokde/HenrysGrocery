package com.henrysgrocery.discount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.henrysgrocery.store.assets.Basket;
import com.henrysgrocery.store.assets.ProductType;

public class DiscountService {
	private static List<Discount> discounts = new ArrayList<Discount>();

	public static final SingleProductPercentageDiscount appleDiscount = new SingleProductPercentageDiscount(ProductType.APPLE, 0.10f,
			LocalDate.now().plusDays(3), LocalDate.of(2020, 11, 30));
	public static final SoupAndBreadCombo multiProductDiscount = new SoupAndBreadCombo(0.50f, LocalDate.now().minusDays(1),
			LocalDate.now().plusDays(6));
	
	public static List<Discount> getDiscounts() {

		discounts.add(appleDiscount);
		discounts.add(multiProductDiscount);
		
		return discounts;
	}
	
	public static BigDecimal applyDiscount(Basket basket, LocalDate purchaseDate) {
		BigDecimal totalDiscount = BigDecimal.ZERO;
		for (Discount d : discounts) {
			if (d.isApplicable(basket, purchaseDate)) {
				totalDiscount.add(d.getDiscount(basket));
			}
		}
		return totalDiscount;
	}
}
