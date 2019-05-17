package com.mycompany.discount.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.ReturnsArgumentAt;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mycompany.discount.config.AffiliateDiscount;
import com.mycompany.discount.config.CustomerDiscount;
import com.mycompany.discount.config.DiscountExclusion;
import com.mycompany.discount.config.EmployeeDiscount;
import com.mycompany.discount.config.InvoiceLevelDiscount;
import com.mycompany.discount.dao.impl.UserInfoDAOImpl;
import com.mycompany.discount.dto.CustomerCategoryDiscount;
import com.mycompany.discount.dto.InvoiceDTO;
import com.mycompany.discount.dto.ProductDTO;
import com.mycompany.discount.dto.UserInfoDTO;
import com.mycompany.discount.exception.UserDoesNotExistException;
import com.mycompany.discount.function.ApplyDiscount;
import com.mycompany.discount.function.CalculateInvoiceTotal;
import com.mycompany.discount.function.CheckProductExclusion;
import com.mycompany.discount.function.GetAffiliateDiscount;
import com.mycompany.discount.function.GetCustomerDiscount;
import com.mycompany.discount.function.GetEmployeeDiscount;
import com.mycompany.discount.service.impl.DiscountServiceImpl;

import lombok.NoArgsConstructor;

/**
 * 
 * @author kkshi
 *
 */
@NoArgsConstructor
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class DiscountServiceTest {

	DiscountService service = new DiscountServiceImpl();

	/**
	 * 
	 */
	@Mock
	UserInfoDAOImpl userInfoDAOImpl;

	/**
	 * 
	 */
	@Mock
	CalculateInvoiceTotal calculateInvoiceTotal;

	@Mock
	GetCustomerDiscount getCustomerDiscount;

	@Mock
	GetAffiliateDiscount getAffiliateDiscount;

	@Mock
	GetEmployeeDiscount getEmployeeDiscount;

	@Mock
	BiFunction<ProductDTO, Double, ProductDTO> applyDiscount;

	@Mock
	CheckProductExclusion checkProductExclusion;

	@BeforeEach
	public void init() {
		List<CustomerCategoryDiscount> customerCategories = new ArrayList<CustomerCategoryDiscount>();
		customerCategories
				.add(CustomerCategoryDiscount.builder().activeDurationDays(20).discountPercentage(10.0).build());
		CustomerDiscount discount = CustomerDiscount.builder().enabled(true).customerCategories(customerCategories)
				.build();
		EmployeeDiscount empDiscount = EmployeeDiscount.builder().enabled(true).discountPercentage(5.0).build();
		AffiliateDiscount affiliateDiscount = AffiliateDiscount.builder().enabled(true).discountPercentage(15.0)
				.build();
		List<String> producttypes = new ArrayList<>();
		producttypes.add("Grocery");
		DiscountExclusion discountExclusion = DiscountExclusion.builder().enabled(true).productTypes(producttypes)
				.build();
		InvoiceLevelDiscount invoiceLevelDiscount = InvoiceLevelDiscount.builder().discountApplyForEach(100.0)
				.flatDiscount(5.0).build();
		lenient().when(userInfoDAOImpl.getUserInfo(eq("Customer1"))).thenReturn(
				UserInfoDTO.builder().userId("Customer1").userType("Customer").activeDurationDays(60).build());
		lenient().when(userInfoDAOImpl.getUserInfo(eq("Employee1")))
				.thenReturn(UserInfoDTO.builder().userId("Employee1").userType("Employee").build());
		lenient().when(userInfoDAOImpl.getUserInfo(eq("Affiliate1")))
				.thenReturn(UserInfoDTO.builder().userId("Affiliate1").userType("Affiliate").build());

		service = new DiscountServiceImpl(userInfoDAOImpl, affiliateDiscount, empDiscount, discount, discountExclusion,
				invoiceLevelDiscount);

	}

	@Test
	public void testApplyCustomerDiscount() {

		InvoiceDTO invoiceDTO = service.applyDiscount(getInvoiceDTO(), "Customer1");

		assertNotNull(invoiceDTO);
		assertNotNull(invoiceDTO.getProducts());
		assertNotNull(invoiceDTO.getProducts().get(0));
		assertEquals(Double.valueOf(10.0), invoiceDTO.getProducts().get(0).getDiscount());
		assertEquals(Double.valueOf(90.0), invoiceDTO.getProducts().get(0).getPayableAmount());
		assertEquals(Double.valueOf(100.0), invoiceDTO.getProducts().get(0).getPrice());
	}

	@Test
	public void testApplyEmployeeDiscount() {

		InvoiceDTO invoiceDTO = service.applyDiscount(getInvoiceDTO(), "Employee1");

		assertNotNull(invoiceDTO);
		assertNotNull(invoiceDTO.getProducts());
		assertNotNull(invoiceDTO.getProducts().get(0));
		assertEquals(Double.valueOf(5.0), invoiceDTO.getProducts().get(0).getDiscount());
		assertEquals(Double.valueOf(95.0), invoiceDTO.getProducts().get(0).getPayableAmount());
		assertEquals(Double.valueOf(100.0), invoiceDTO.getProducts().get(0).getPrice());
	}

	@Test
	public void testApplyAffiliateDiscount() {

		InvoiceDTO invoiceDTO = service.applyDiscount(getInvoiceDTO(), "Affiliate1");

		assertNotNull(invoiceDTO);
		assertNotNull(invoiceDTO.getProducts());
		assertNotNull(invoiceDTO.getProducts().get(0));
		assertEquals(Double.valueOf(15.0), invoiceDTO.getProducts().get(0).getDiscount());
		assertEquals(Double.valueOf(85.0), invoiceDTO.getProducts().get(0).getPayableAmount());
		assertEquals(Double.valueOf(100.0), invoiceDTO.getProducts().get(0).getPrice());
	}

	@Test()
	public void testInvalidUser() {
		assertThrows(UserDoesNotExistException.class, () -> service.applyDiscount(getInvoiceDTO(), "Unknown"));
	}

	private InvoiceDTO getInvoiceDTO() {
		List<ProductDTO> products = new ArrayList<>();
		products.add(ProductDTO.builder().price(100.0).productType("Test").build());

		return InvoiceDTO.builder().products(products).build();

	}
}
