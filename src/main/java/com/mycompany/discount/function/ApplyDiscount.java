package com.mycompany.discount.function;

import java.util.function.BiFunction;

import com.mycompany.discount.dto.ProductDTO;

public class ApplyDiscount implements BiFunction<ProductDTO, Double, ProductDTO> {

	@Override
	public ProductDTO apply(final ProductDTO product, final Double discount) {
		final Double discountValue = product.getPrice() * discount / 100;
		return ProductDTO.builder()
				.discount(discountValue)
				.payableAmount(product.getPrice() - discountValue)
				.price(product.getPrice())
				.productType(product.getProductType())
				.productName(product.getProductName())
				.build();
	}

}
