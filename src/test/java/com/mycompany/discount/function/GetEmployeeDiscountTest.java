package com.mycompany.discount.function;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.mycompany.discount.config.AffiliateDiscount;
import com.mycompany.discount.config.EmployeeDiscount;
import com.mycompany.discount.dto.UserInfoDTO;

public class GetEmployeeDiscountTest {

	Function<UserInfoDTO, Double> getEmployeeDiscount;
	
	@Test
	public void testWithAnAffiliateWithDiscount() {
		EmployeeDiscount discount = EmployeeDiscount.builder().enabled(true).discountPercentage(10.0).build();
		getEmployeeDiscount = new GetEmployeeDiscount(discount);
		
		Double result = getEmployeeDiscount.apply(UserInfoDTO.builder().userType("Employee").build());
		
		assertNotNull(result);
		assertEquals(Double.valueOf(10.0), result);
		
	}
	
	@Test
	public void testWithAnAffiliateWitDiscountDisabled() {
		AffiliateDiscount discount = AffiliateDiscount.builder().enabled(false).discountPercentage(10.0).build();
		getEmployeeDiscount = new GetAffiliateDiscount(discount);
		
		Double result = getEmployeeDiscount.apply(UserInfoDTO.builder().userType("Employee").build());
		
		assertNull(result);
		
	}
	
}
