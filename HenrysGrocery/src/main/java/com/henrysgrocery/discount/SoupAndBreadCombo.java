package com.henrysgrocery.discount;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.henrysgrocery.store.assets.Basket;
import com.henrysgrocery.store.assets.ProductType;

public class SoupAndBreadCombo implements Discount {

	private float percentOff;
	private LocalDate validFrom;
	private LocalDate validTill;

	public SoupAndBreadCombo(float percentage, LocalDate from, LocalDate till) {
		this.percentOff = percentage;
		this.validFrom = from;
		this.validTill = till;
	}

	@Override
	public boolean isApplicable(Basket basket, LocalDate purchaseDate) {
		return basket.contains(ProductType.SOUP) && basket.contains(ProductType.BREAD)
				&& basket.productCount(ProductType.SOUP) > 1
				&& validFrom.compareTo(purchaseDate) * purchaseDate.compareTo(validTill) >= 0;
	}

	@Override
	public BigDecimal getDiscount(Basket basket) {
		return BigDecimal.valueOf(basket.productCount(ProductType.SOUP)/2).multiply(basket.getItem(ProductType.BREAD).price().multiply(BigDecimal.valueOf(percentOff)));
	}
}
