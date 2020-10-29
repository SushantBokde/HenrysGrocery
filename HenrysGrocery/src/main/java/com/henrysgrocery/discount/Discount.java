package com.henrysgrocery.discount;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.henrysgrocery.store.assets.Basket;

public interface Discount {
	boolean isApplicable(Basket basket, LocalDate purchaseDate);
	
	BigDecimal getDiscount (Basket basket); 
}