package com.henrysgrocery.store.assets;

import java.math.BigDecimal;

public class ItemByUnit implements Item {

	private final Product product;

	ItemByUnit(final Product product) {
		this.product = product;
	}

	public BigDecimal price() {
		return product.unitPrice();
	}
	
	@Override
	public ProductType productType() {
		return product.productType();
	}
}
