package com.mycompany.discount.function;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.mycompany.discount.config.AffiliateDiscount;
import com.mycompany.discount.config.CustomerDiscount;
import com.mycompany.discount.dto.CustomerCategoryDiscount;
import com.mycompany.discount.dto.UserInfoDTO;

public class GetCustomerDiscountTest {

	Function<UserInfoDTO, Double> getAffiliateDiscount;

	@Test
	public void testWithAnAffiliateWithDiscount() {
		List<CustomerCategoryDiscount> customerCategories = new ArrayList<CustomerCategoryDiscount>();
		customerCategories.add(CustomerCategoryDiscount.builder().activeDuration(Duration.ofDays(20))
				.discountPercentage(10.0).build());
		CustomerDiscount discount = CustomerDiscount.builder().enabled(true).customerCategories(customerCategories)
				.build();
		getAffiliateDiscount = new GetCustomerDiscount(discount);

		Double result = getAffiliateDiscount.apply(UserInfoDTO.builder().activeDuration(21).build());

		assertNotNull(result);
		assertEquals(Double.valueOf(10.0), result);

	}
	
	@Test
	public void testWithAnAffiliateWithLessDuration() {
		List<CustomerCategoryDiscount> customerCategories = new ArrayList<CustomerCategoryDiscount>();
		customerCategories.add(CustomerCategoryDiscount.builder().activeDuration(Duration.ofDays(20))
				.discountPercentage(10.0).build());
		CustomerDiscount discount = CustomerDiscount.builder().enabled(true).customerCategories(customerCategories)
				.build();
		getAffiliateDiscount = new GetCustomerDiscount(discount);

		Double result = getAffiliateDiscount.apply(UserInfoDTO.builder().activeDuration(19).build());

		assertNull(result);
	}
	
	@Test
	public void testWithAnAffiliateWithExactDuration() {
		List<CustomerCategoryDiscount> customerCategories = new ArrayList<CustomerCategoryDiscount>();
		customerCategories.add(CustomerCategoryDiscount.builder().activeDuration(Duration.ofDays(20))
				.discountPercentage(10.0).build());
		CustomerDiscount discount = CustomerDiscount.builder().enabled(true).customerCategories(customerCategories)
				.build();
		getAffiliateDiscount = new GetCustomerDiscount(discount);

		Double result = getAffiliateDiscount.apply(UserInfoDTO.builder().activeDuration(20).build());

		assertNotNull(result);
		assertEquals(Double.valueOf(10.0), result);
	}

	@Test
	public void testWithAnAffiliateWitDiscountDisabled() {
		CustomerDiscount discount = CustomerDiscount.builder().enabled(false).build();
		getAffiliateDiscount = new GetCustomerDiscount(discount);

		Double result = getAffiliateDiscount.apply(UserInfoDTO.builder().userType("Affiliate").build());

		assertNull(result);

	}

}
