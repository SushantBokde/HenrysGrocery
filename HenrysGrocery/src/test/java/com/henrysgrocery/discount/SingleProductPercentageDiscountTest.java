package com.henrysgrocery.discount;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.henrysgrocery.store.assets.Basket;
import com.henrysgrocery.store.assets.Product;
import com.henrysgrocery.store.assets.ProductBuilder;
import com.henrysgrocery.store.assets.ProductType;

public class SingleProductPercentageDiscountTest {
	
	private static SingleProductPercentageDiscount discount;
	private Basket basket;
	private Product apple;
	
	// margin of error when comparing BigDecimal values in assert statements
	private static final BigDecimal DELTA = new BigDecimal("0.0001");
	
	
	@BeforeClass
	public static void setUpDiscount() {
		discount = new SingleProductPercentageDiscount(ProductType.APPLE,0.10f, LocalDate.now().plusDays(3), LocalDate.of(2020,11,30));
	}
	
	@Before
	public void initialise() {
		basket = new Basket();	
		apple = ProductBuilder.anApple().retailPrice(BigDecimal.valueOf(0.10)).build();
	}
	
	@Test
	public void singleProductPurchasedWithinDiscountPeriod() {
		
		basket.add(apple.oneOf());
		
		assertThat(discount.isApplicable(basket, LocalDate.now().plusDays(8)), is(true));
		assertThat(discount.getDiscount(basket), is(closeTo(new BigDecimal("0.01"), DELTA)));
	}
	
	@Test
	public void singleProductPurchasedOutOfDiscountPeriod() {
		
		basket.add(apple.oneOf());		
		assertThat(discount.isApplicable(basket, LocalDate.now()), is(false));
	}
	
	@Test
	public void multipleProductPurchasedWithinDiscountPeriod() {
		
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		
		assertThat(discount.isApplicable(basket, LocalDate.now().plusDays(10)), is(true));
		assertThat(discount.getDiscount(basket), is(closeTo(new BigDecimal("0.05"), DELTA)));
	}
	
	/**
	 * Price a basket containing: 6 apples and a bottle of milk, bought today,
	 * Expected total cost = 1.90;
	 */
	@Test
	public void sixApplesAndaBottleOfMilkBoughtToday() {
		
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		
		basket.add(ProductBuilder.aBottleOfMilk().retailPrice(BigDecimal.valueOf(1.30)).build().oneOf());
		
		assertThat(discount.isApplicable(basket, LocalDate.now()), is(false));
		assertThat(basket.subTotal(), is(closeTo(new BigDecimal("1.90"), DELTA)));
	}
	
	/**
	 * Price a basket containing: 6 apples and a bottle of milk, bought in 5 days time
	 * Expected total cost = 1.84;
	 */
	@Test
	public void sixApplesAndaBottleOfMilkBoughtInFiveDaysTime() {
		
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		basket.add(apple.oneOf());
		
		basket.add(ProductBuilder.aBottleOfMilk().retailPrice(BigDecimal.valueOf(1.30)).build().oneOf());
		
		assertThat(discount.isApplicable(basket, LocalDate.now().plusDays(5)), is(true));
		assertThat(basket.subTotal(), is(closeTo(new BigDecimal("1.90"), DELTA)));
		assertThat(discount.getDiscount(basket), is(closeTo(new BigDecimal("0.06"), DELTA)));
		assertThat(basket.subTotal().subtract(discount.getDiscount(basket)), is(closeTo(new BigDecimal("1.84"), DELTA)));
	
	}
}
