package com.henrysgrocery.store.assets;

import java.math.BigDecimal;

public class ProductBuilder {
	private ProductType productType;
	private UnitType unitType;
	private BigDecimal price = BigDecimal.TEN;

	private ProductBuilder(ProductType productType, UnitType unitType) {
		this.productType = productType;
		this.unitType = unitType;
	}

	public static ProductBuilder anApple() {
        return new ProductBuilder(ProductType.APPLE, UnitType.SINGLE);
    }

	public static ProductBuilder aLoafOfBread() {
		return new ProductBuilder(ProductType.BREAD, UnitType.LOAF);
	}

	public static ProductBuilder aTinOfSoup() {
		return new ProductBuilder(ProductType.SOUP, UnitType.TIN);
	}
	
	public static ProductBuilder aBottleOfMilk() {
		return new ProductBuilder(ProductType.MILK, UnitType.BOTTLE);
	}
	
	public ProductBuilder retailPrice(BigDecimal price) {
        this.price = price;
	    return this;
    }

	public Product build() {
		return new Product(productType, price, unitType);
	}

}
