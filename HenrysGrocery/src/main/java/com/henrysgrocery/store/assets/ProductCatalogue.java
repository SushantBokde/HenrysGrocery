package com.henrysgrocery.store.assets;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProductCatalogue {
	private static Map<ProductType ,Product> productMap = new HashMap<ProductType, Product>();
	
	public static Map<ProductType ,Product> getProductsInStore() {
		productMap.put(ProductType.APPLE, ProductBuilder.anApple().retailPrice(BigDecimal.valueOf(0.10)).build());
		productMap.put(ProductType.BREAD, ProductBuilder.aLoafOfBread().retailPrice(BigDecimal.valueOf(0.80)).build());
		productMap.put(ProductType.MILK, ProductBuilder.aBottleOfMilk().retailPrice(BigDecimal.valueOf(1.30)).build());
		productMap.put(ProductType.SOUP, ProductBuilder.aTinOfSoup().retailPrice(BigDecimal.valueOf(0.65)).build());
	
		return Collections.unmodifiableMap(productMap);
	}
	
}
