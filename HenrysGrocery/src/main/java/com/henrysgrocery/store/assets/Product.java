package com.henrysgrocery.store.assets;

import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This is a representation of product in store. It is a single item with unit
 * type and price specified. For example: 1 tin of soup
 */
public class Product {
	private ProductType productType;
	private BigDecimal pricePerUnit;
	private UnitType unitType;

	public Product(final ProductType productType, final BigDecimal pricePerUnit, final UnitType unitType) {
		this.productType = productType;
		this.pricePerUnit = pricePerUnit;
		this.unitType = unitType;
	}

	public BigDecimal unitPrice() {
		return pricePerUnit;
	}

	public UnitType unitType() {
		return unitType;
	}

	public ProductType productType() {
		return productType;
	}

    public Item oneOf() {
        return new ItemByUnit(this);
    }
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Product)) {
			return false;
		}
		Product product = (Product) o;
		return Objects.equals(productType, product.productType) && 
				Objects.equals(pricePerUnit, product.pricePerUnit) && 
				Objects.equals(unitType, product.unitType);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(productType)
				.append(pricePerUnit)
				.append(unitType)
				.toHashCode();
	}
}
