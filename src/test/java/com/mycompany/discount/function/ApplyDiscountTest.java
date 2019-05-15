package com.mycompany.discount.function;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import com.mycompany.discount.dto.ProductDTO;

public class ApplyDiscountTest {

	BiFunction<ProductDTO, Double, ProductDTO> applyDiscount = new ApplyDiscount();
	
	@Test
	public void testSuccessfulCalculation() {
		final Double price = 100.0;
		final Double discount = 10.0;
		
		ProductDTO product = ProductDTO.builder().price(price)
				.build();
		ProductDTO result = applyDiscount.apply(product, discount);
		
		assertNotNull(result);
		assertEquals(Double.valueOf(price - discount), result.getPayableAmount());
		
	}
	
	@Test
	public void testProductInformation() {
		final Double price = 100.0;
		final String productName = "TestName";
		final String productType = "testCategory";
		final Double discount = 10.0;
		
		ProductDTO product = ProductDTO.builder().price(price)
				.productName(productName)
				.productType(productType)
				.build();
		
		
		ProductDTO result = applyDiscount.apply(product, discount);
		
		assertNotNull(result);
		assertEquals(result.getPayableAmount(), Double.valueOf(price - discount));
		assertEquals(product.getProductName(), productName);
		assertEquals(product.getProductType(), productType);
	}
	
	@Test
	public void testPriceNull() {
		final String productName = "TestName";
		final String productType = "testCategory";
		final Double discount = 10.0;
		
		ProductDTO product = ProductDTO.builder()
				.productName(productName)
				.productType(productType)
				.build();
		
		
		
		assertThatNullPointerException().isThrownBy(()-> applyDiscount.apply(product, discount));
	}
}
