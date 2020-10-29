package com.henrysgrocery.store.assets;

import java.math.BigDecimal;

public interface Item {
	BigDecimal price();
	ProductType productType();
}
