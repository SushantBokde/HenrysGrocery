package com.henrysgrocery.discount;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.henrysgrocery.store.assets.Basket;
import com.henrysgrocery.store.assets.ProductType;


public class SingleProductPercentageDiscount implements Discount {
	private ProductType productType;
	private float percentOff;
	private LocalDate validFrom;
	private LocalDate validTill;
	
	public SingleProductPercentageDiscount(ProductType productType, float percentOff, LocalDate validFrom,
			LocalDate validTill) {
		super();
		this.productType = productType;
		this.percentOff = percentOff;
		this.validFrom = validFrom;
		this.validTill = validTill;
	}

	@Override
	public boolean isApplicable(Basket basket, LocalDate purchaseDate) {
		return basket.contains(productType)
				&& validFrom.compareTo(purchaseDate) * purchaseDate.compareTo(validTill) >= 0;
	}

	@Override
	public BigDecimal getDiscount(Basket basket) {	
		return BigDecimal.valueOf(basket.productCount(this.productType) * percentOff).multiply(basket.getItem(this.productType).price());
	}

}
