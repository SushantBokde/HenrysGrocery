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

public class SoupAndBreadComboTest {

	private static SoupAndBreadCombo discount;
	private Basket basket;
	private Product soup;
	private Product bread;
	
	// margin of error when comparing BigDecimal values in assert statements
	private static final BigDecimal DELTA = new BigDecimal("0.001");
	
	
	@BeforeClass
	public static void setUpDiscount() {
		discount = new SoupAndBreadCombo(0.50f, LocalDate.now().minusDays(1), LocalDate.now().plusDays(6));
	}
	
	@Before
	public void initialise() {
		basket = new Basket();	
		soup = ProductBuilder.aTinOfSoup().retailPrice(BigDecimal.valueOf(0.65)).build();
		bread = ProductBuilder.aLoafOfBread().retailPrice(BigDecimal.valueOf(0.80)).build();
	}
	
	
	@Test
	public void onlyTwoTinsOfSoupWithinDiscountPeriod() {
		basket.add(soup.oneOf());
		basket.add(soup.oneOf());
		
		assertThat(discount.isApplicable(basket, LocalDate.now()), is(false));
	}
	
	@Test
	public void twoTinsOfSoupAndOneLoafOfBreadOutsideDiscountPeriod() {
		basket.add(soup.oneOf());
		basket.add(soup.oneOf());
		basket.add(bread.oneOf());
		
		assertThat(discount.isApplicable(basket, LocalDate.now().minusDays(2)), is(false));
	}
	
	@Test
	public void twoTinsOfSoupAndOneLoafOfBreadWithinDiscountPeriod() {
		basket.add(soup.oneOf());
		basket.add(soup.oneOf());
		basket.add(bread.oneOf());
		
		assertThat(discount.isApplicable(basket, LocalDate.now()), is(true));
		assertThat(discount.getDiscount(basket), is(closeTo(new BigDecimal("0.4"), DELTA)));
	}
	
	/**
	 * Test: buy 2 tins of soup with two loaves of bread purchased within discount period
	 * Expected: discount applicable to only 1 loaf of bread
	 */
	@Test
	public void twoTinsOfSoupAndTwoLoavesOfBreadWithinDiscountPeriod() {
		basket.add(soup.oneOf());
		basket.add(soup.oneOf());
		basket.add(bread.oneOf());
		basket.add(bread.oneOf());
		
		assertThat(discount.isApplicable(basket, LocalDate.now()), is(true));
		assertThat(discount.getDiscount(basket), is(closeTo(new BigDecimal("0.4"), DELTA)));
	}
	
	@Test
	public void fourTinsOfSoupAndTwoLoavesOfBreadWithinDiscountPeriod() {
		basket.add(soup.oneOf());
		basket.add(soup.oneOf());
		basket.add(soup.oneOf());
		basket.add(soup.oneOf());
		basket.add(bread.oneOf());
		basket.add(bread.oneOf());
		
		assertThat(discount.isApplicable(basket, LocalDate.now()), is(true));
		assertThat(discount.getDiscount(basket), is(closeTo(new BigDecimal("0.8"), DELTA)));
	}
	
	/**
	 * Price a basket containing: 3 tins of soup and 2 loaves of bread, bought today,
	 * Expected total cost = 3.15;
	 */
	@Test
	public void threeTinsOfSoupAndTwoLoavesOfBreadBoughtToday() {
		basket.add(soup.oneOf());
		basket.add(soup.oneOf());
		basket.add(soup.oneOf());
		basket.add(bread.oneOf());
		basket.add(bread.oneOf());
		
		assertThat(discount.isApplicable(basket, LocalDate.now()), is(true));
		assertThat(basket.subTotal(), is(closeTo(new BigDecimal("3.55"), DELTA)));
		assertThat(discount.getDiscount(basket), is(closeTo(new BigDecimal("0.40"), DELTA)));
		assertThat(basket.subTotal().subtract(discount.getDiscount(basket)), is(closeTo(new BigDecimal("3.15"), DELTA)));
	}
	

}
