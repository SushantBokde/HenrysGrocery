package com.henrysgrocery.store.assets;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class BasketTest {
	
	private Basket basket;
	
	// margin of error when comparing BigDecimal values in assert statements
	private static final BigDecimal DELTA = new BigDecimal("0.001");

	@Before
	public void initialise() {
		basket = new Basket();	
	}	
	
	@Test
	public void addItemsToBasket() {
		ProductCatalogue.getProductsInStore().forEach((k,v) -> basket.add(v.oneOf()));
		assertThat(basket.items().size(), is(4));
		assertTrue(basket.contains(ProductType.APPLE));
		assertTrue(basket.contains(ProductType.BREAD));
		assertTrue(basket.contains(ProductType.MILK));
		assertTrue(basket.contains(ProductType.SOUP));
		assertThat(basket.productCount(ProductType.APPLE), is(Long.valueOf(1)));
		assertThat(basket.productCount(ProductType.BREAD), is(Long.valueOf(1)));
		assertThat(basket.productCount(ProductType.MILK), is(Long.valueOf(1)));
		assertThat(basket.productCount(ProductType.SOUP), is(Long.valueOf(1)));
		
		assertThat(basket.subTotal(), is(closeTo(new BigDecimal("2.85"), DELTA)));
		
	}

}
