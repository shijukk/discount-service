package com.mycompany.discount.function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.ToDoubleFunction;

import org.junit.jupiter.api.Test;

import com.mycompany.discount.config.AffiliateDiscount;
import com.mycompany.discount.config.EmployeeDiscount;
import com.mycompany.discount.dto.UserInfoDTO;

public class GetEmployeeDiscountTest {

	ToDoubleFunction<UserInfoDTO> getEmployeeDiscount;

	@Test
	public void testWithAnAffiliateWithDiscount() {
		EmployeeDiscount discount = EmployeeDiscount.builder().enabled(true).discountPercentage(10.0).build();
		getEmployeeDiscount = new GetEmployeeDiscount(discount);

		double result = getEmployeeDiscount.applyAsDouble(UserInfoDTO.builder().userType("Employee").build());

		assertEquals(10.0, result);

	}

	@Test
	public void testWithAnAffiliateWitDiscountDisabled() {
		AffiliateDiscount discount = AffiliateDiscount.builder().enabled(false).discountPercentage(10.0).build();
		getEmployeeDiscount = new GetAffiliateDiscount(discount);

		double result = getEmployeeDiscount.applyAsDouble(UserInfoDTO.builder().userType("Employee").build());

		assertEquals(0, result);

	}

}
