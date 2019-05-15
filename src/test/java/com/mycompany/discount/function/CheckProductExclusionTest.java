package com.mycompany.discount.function;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.mycompany.discount.config.DiscountExclusion;
import com.mycompany.discount.dto.ProductDTO;

public class CheckProductExclusionTest {

	Predicate<ProductDTO> checkProductExclusion = new CheckProductExclusion();

	@Test
	public void testWithOneProductAndAdditionDiscount() {
		List<String> producttypes = new ArrayList<>();
		producttypes.add("Grocery");
		DiscountExclusion discountExclusion = DiscountExclusion.builder().enabled(true).productTypes(producttypes)
				.build();
		checkProductExclusion = new CheckProductExclusion(discountExclusion);

		Boolean result = checkProductExclusion.test(ProductDTO.builder().productType("Test").build());

		assertNotNull(result);
		assertFalse(result);
	}
	
	@Test
	public void testWithExcludedProduct() {
		List<String> producttypes = new ArrayList<>();
		producttypes.add("Grocery");
		DiscountExclusion discountExclusion = DiscountExclusion.builder().enabled(true).productTypes(producttypes)
				.build();
		checkProductExclusion = new CheckProductExclusion(discountExclusion);

		Boolean result = checkProductExclusion.test(ProductDTO.builder().productType("Grocery").build());

		assertNotNull(result);
		assertTrue(result);
	}

	

}
