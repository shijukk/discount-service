package com.mycompany.discount.function;

import java.util.function.Predicate;

import com.mycompany.discount.config.DiscountExclusion;
import com.mycompany.discount.dto.ProductDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CheckProductExclusion implements Predicate<ProductDTO> {
	
	private DiscountExclusion exclusion = new DiscountExclusion();

	@Override
	public boolean test(final ProductDTO product) {
		if(exclusion.isEnabled()) {
			 return exclusion.getProductTypes().stream()
			.filter( type ->   product.getProductType().equalsIgnoreCase(type))
			.findFirst()
			.map((category) -> {return true;})
			.orElse(false); 
		}
		return false;
	}

	
}
