package com.mycompany.discount.function;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.mycompany.discount.config.AffiliateDiscount;
import com.mycompany.discount.dto.UserInfoDTO;

public class GetAffiliateDiscountTest {

	Function<UserInfoDTO, Double> getAffiliateDiscount;
	
	@Test
	public void testWithAnAffiliateWithDiscount() {
		AffiliateDiscount discount = AffiliateDiscount.builder().enabled(true).discountPercentage(10.0).build();
		getAffiliateDiscount = new GetAffiliateDiscount(discount);
		
		Double result = getAffiliateDiscount.apply(UserInfoDTO.builder().userType("Affiliate").build());
		
		assertNotNull(result);
		assertEquals(Double.valueOf(10.0), result);
		
	}
	
	@Test
	public void testWithAnAffiliateWitDiscountDisabled() {
		AffiliateDiscount discount = AffiliateDiscount.builder().enabled(false).discountPercentage(10.0).build();
		getAffiliateDiscount = new GetAffiliateDiscount(discount);
		
		Double result = getAffiliateDiscount.apply(UserInfoDTO.builder().userType("Affiliate").build());
		
		assertNull(result);
		
	}
	
}
