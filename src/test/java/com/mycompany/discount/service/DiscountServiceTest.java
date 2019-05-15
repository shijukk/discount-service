package com.mycompany.discount.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.ReturnsArgumentAt;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mycompany.discount.dao.impl.UserInfoDAOImpl;
import com.mycompany.discount.dto.InvoiceDTO;
import com.mycompany.discount.dto.ProductDTO;
import com.mycompany.discount.dto.UserInfoDTO;
import com.mycompany.discount.function.CalculateInvoiceTotal;
import com.mycompany.discount.function.GetPayableAmount;
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
	
	
	/**
	 * 
	 */
	@Mock
	GetPayableAmount getPayableAmount ;
	
	/**
	 * 
	 */
	@Mock
	UserInfoDAOImpl userInfoDAOImpl ;
	
	/**
	 * 
	 */
	@Mock
	CalculateInvoiceTotal calculateInvoiceTotal ;
	
		
	@Test
	public void testApplyDiscount() {
		when(userInfoDAOImpl.getUserInfo(any(String.class)))
				.thenReturn(UserInfoDTO.builder().userId("user").build());
		when(getPayableAmount.apply(any(UserInfoDTO.class), any(ProductDTO.class))).then(new ReturnsArgumentAt(ReturnsArgumentAt.LAST_ARGUMENT));
		when(calculateInvoiceTotal.apply(any(InvoiceDTO.class))).then(new ReturnsArgumentAt(ReturnsArgumentAt.LAST_ARGUMENT));
		
		DiscountService service = new DiscountServiceImpl(userInfoDAOImpl, getPayableAmount, calculateInvoiceTotal);
		
		
		service.applyDiscount(getInvoiceDTO(), "test");
		
	}
	
	private InvoiceDTO getInvoiceDTO() {
		List<ProductDTO> products = new ArrayList();
		products.add(ProductDTO.builder().price(100.0).build());
		
		return InvoiceDTO.builder().products(products).build();
		
	}
}
