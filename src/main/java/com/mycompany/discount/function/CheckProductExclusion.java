package com.mycompany.discount.function;

import java.util.function.Predicate;

import com.mycompany.discount.config.DiscountExclusion;
import com.mycompany.discount.dto.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class CheckProductExclusion implements Predicate<ProductDTO> {

	private DiscountExclusion exclusion;

	@Override
	public boolean test(final ProductDTO product) {
		log.debug("Product exclusion check for '{}' of type '{}' ", product.getProductName(), product.getProductType());
		if (exclusion.isEnabled()) {
			return exclusion.getProductTypes().stream().filter(type -> product.getProductType().equalsIgnoreCase(type))
					.findFirst().map(category -> true).orElse(false);
		}
		return false;
	}

}
