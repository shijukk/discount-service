package com.mycompany.discount.function;

import java.util.function.BiFunction;

import javax.validation.constraints.NotNull;

import com.mycompany.discount.dto.ProductDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplyDiscount implements BiFunction<ProductDTO, Double, ProductDTO> {

	@Override
	public ProductDTO apply(@NotNull final ProductDTO product, @NotNull final Double discountPercentage) {
		log.debug("Applying discount percentage {} on product '{}' - actual price is {}", discountPercentage,
				product.getProductName(), product.getPrice());
		final Double discountValue = product.getPrice() * discountPercentage / 100;
		return ProductDTO.builder().discount(discountValue).payableAmount(product.getPrice() - discountValue)
				.price(product.getPrice()).productType(product.getProductType()).productName(product.getProductName())
				.build();
	}

}
