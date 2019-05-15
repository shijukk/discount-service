package com.mycompany.discount.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.mycompany.discount.config.AffiliateDiscount;
import com.mycompany.discount.config.CustomerDiscount;
import com.mycompany.discount.config.DiscountExclusion;
import com.mycompany.discount.config.EmployeeDiscount;
import com.mycompany.discount.dto.CustomerCategoryDiscount;
import com.mycompany.discount.dto.ProductDTO;
import com.mycompany.discount.dto.UserInfoDTO;

public class GetPayableAmountTest {

	static GetPayableAmount getPayableAmount;

	@Mock
	static GetCustomerDiscount getCustomerDiscount;

	@Mock
	static GetAffiliateDiscount getAffiliateDiscount;

	@Mock
	static GetEmployeeDiscount getEmployeeDiscount;

	@Mock
	static BiFunction<ProductDTO, Double, ProductDTO> applyDiscount = new ApplyDiscount();

	@Mock
	static CheckProductExclusion checkProductExclusion = new CheckProductExclusion();

	@BeforeAll
	public static void init() {
		List<CustomerCategoryDiscount> customerCategories = new ArrayList<CustomerCategoryDiscount>();
		customerCategories.add(CustomerCategoryDiscount.builder().activeDuration(Duration.ofDays(20))
				.discountPercentage(10.0).build());
		CustomerDiscount discount = CustomerDiscount.builder().enabled(true).customerCategories(customerCategories)
				.build();
		getCustomerDiscount = new GetCustomerDiscount(discount);

		EmployeeDiscount empDiscount = EmployeeDiscount.builder().enabled(true).discountPercentage(5.0).build();
		getEmployeeDiscount = new GetEmployeeDiscount(empDiscount);

		AffiliateDiscount affiliateDiscount = AffiliateDiscount.builder().enabled(true).discountPercentage(15.0)
				.build();
		getAffiliateDiscount = new GetAffiliateDiscount(affiliateDiscount);

		List<String> producttypes = new ArrayList<>();
		producttypes.add("Grocery");
		DiscountExclusion discountExclusion = DiscountExclusion.builder().enabled(true).productTypes(producttypes)
				.build();
		checkProductExclusion = new CheckProductExclusion(discountExclusion);

		getPayableAmount = new GetPayableAmount(getCustomerDiscount, getAffiliateDiscount, getEmployeeDiscount,
				applyDiscount, checkProductExclusion);
	}

	@Test
	public void testWithCustomerAndProductTypeNotExcluded() {

		ProductDTO result = getPayableAmount.apply(
				UserInfoDTO.builder().userType("Customer").activeDuration(20).build(),
				ProductDTO.builder().price(100.0).productType("Test").build());

		assertNotNull(result);
		assertEquals(Double.valueOf(90.0), result.getPayableAmount());

	}
	
	@Test
	public void testWithAffiliate() {

		ProductDTO result = getPayableAmount.apply(
				UserInfoDTO.builder().userType("Affiliate").activeDuration(20).build(),
				ProductDTO.builder().price(100.0).productType("Test").build());

		assertNotNull(result);
		assertEquals(Double.valueOf(85.0), result.getPayableAmount());

	}
	
	@Test
	public void testWithEmployee() {

		ProductDTO result = getPayableAmount.apply(
				UserInfoDTO.builder().userType("Employee").activeDuration(20).build(),
				ProductDTO.builder().price(100.0).productType("Test").build());

		assertNotNull(result);
		assertEquals(Double.valueOf(95.0), result.getPayableAmount());

	}
	
	@Test
	public void testWithProductTypeExcluded() {

		ProductDTO result = getPayableAmount.apply(
				UserInfoDTO.builder().userType("Customer").activeDuration(20).build(),
				ProductDTO.builder().price(100.0).productType("Grocery").build());

		assertNotNull(result);
		assertEquals(Double.valueOf(100.0), result.getPayableAmount());

	}

}
