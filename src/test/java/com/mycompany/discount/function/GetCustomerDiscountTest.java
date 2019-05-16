package com.mycompany.discount.function;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

import org.junit.jupiter.api.Test;

import com.mycompany.discount.config.CustomerDiscount;
import com.mycompany.discount.dto.CustomerCategoryDiscount;
import com.mycompany.discount.dto.UserInfoDTO;

public class GetCustomerDiscountTest {

	ToDoubleFunction<UserInfoDTO> getAffiliateDiscount;

	@Test
	public void testWithAnAffiliateWithDiscount() {
		List<CustomerCategoryDiscount> customerCategories = new ArrayList<CustomerCategoryDiscount>();
		customerCategories.add(CustomerCategoryDiscount.builder().activeDuration(Duration.ofDays(20))
				.discountPercentage(10.0).build());
		CustomerDiscount discount = CustomerDiscount.builder().enabled(true).customerCategories(customerCategories)
				.build();
		getAffiliateDiscount = new GetCustomerDiscount(discount);

		double result = getAffiliateDiscount.applyAsDouble(UserInfoDTO.builder().activeDuration(21).build());

		assertEquals(10.0, result);

	}

	@Test
	public void testWithAnAffiliateWithLessDuration() {
		List<CustomerCategoryDiscount> customerCategories = new ArrayList<CustomerCategoryDiscount>();
		customerCategories.add(CustomerCategoryDiscount.builder().activeDuration(Duration.ofDays(20))
				.discountPercentage(10.0).build());
		CustomerDiscount discount = CustomerDiscount.builder().enabled(true).customerCategories(customerCategories)
				.build();
		getAffiliateDiscount = new GetCustomerDiscount(discount);

		double result = getAffiliateDiscount.applyAsDouble(UserInfoDTO.builder().activeDuration(19).build());

		assertEquals(0.0, result);
	}

	@Test
	public void testWithAnAffiliateWithExactDuration() {
		List<CustomerCategoryDiscount> customerCategories = new ArrayList<CustomerCategoryDiscount>();
		customerCategories.add(CustomerCategoryDiscount.builder().activeDuration(Duration.ofDays(20))
				.discountPercentage(10.0).build());
		CustomerDiscount discount = CustomerDiscount.builder().enabled(true).customerCategories(customerCategories)
				.build();
		getAffiliateDiscount = new GetCustomerDiscount(discount);

		double result = getAffiliateDiscount.applyAsDouble(UserInfoDTO.builder().activeDuration(20).build());

		assertEquals(10.0, result);
	}

	@Test
	public void testWithAnAffiliateWitDiscountDisabled() {
		CustomerDiscount discount = CustomerDiscount.builder().enabled(false).build();
		getAffiliateDiscount = new GetCustomerDiscount(discount);

		double result = getAffiliateDiscount.applyAsDouble(UserInfoDTO.builder().userType("Affiliate").build());

		assertEquals(0.0, result);

	}

}
