package com.henrysgrocery.store.assets;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Basket {
	private final List<Item> items;
	
	public Basket() {
        this.items = new ArrayList<>();
    }

    public void add(final Item item) {
        this.items.add(item);
    }
	
	List<Item> items() {
        return Collections.unmodifiableList(items);
    }

	public BigDecimal subTotal() {
        return items.stream().map(Item::price)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
    }
	
	public boolean contains(ProductType productType) {
		return items.stream()
					.anyMatch(i -> i.productType() == productType);
	}
	
	public long productCount (ProductType type) {
		return items.stream().filter(i ->i.productType().equals(type)).count();
	}
	
	public Item getItem (ProductType type) {
		return items.stream().filter(i ->i.productType().equals(type)).findFirst().get();
	}
}
