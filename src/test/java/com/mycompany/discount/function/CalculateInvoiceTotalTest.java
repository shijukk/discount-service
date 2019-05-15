package com.mycompany.discount.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.mycompany.discount.dto.InvoiceDTO;
import com.mycompany.discount.dto.ProductDTO;

public class CalculateInvoiceTotalTest {

	Function<InvoiceDTO, InvoiceDTO> calculateInvoiceTotal = new CalculateInvoiceTotal();
	
	@Test
	public void testWithOneProductAndAdditionDiscount() {
		final Double price = 150.0;
		final Double discount = 10.0;
		final Double additionalDiscount = 5.0;
		
		List<ProductDTO> products = new ArrayList<ProductDTO>();
		products.add(ProductDTO.builder().price(price).discount(discount).build());
		InvoiceDTO invoice = InvoiceDTO.builder().products(products)
				.build();
		InvoiceDTO result = calculateInvoiceTotal.apply(invoice);
		
		assertNotNull(result);
		assertEquals(Double.valueOf(price - discount - additionalDiscount), result.getNetPayableAmount());
		
	}
	
	@Test
	public void testWithTwoProductAndAdditionDiscount() {
		final Double price = 150.0;
		final Double discount = 10.0;
		final Double additionalDiscount = 10.0;
		
		List<ProductDTO> products = new ArrayList<ProductDTO>();
		products.add(ProductDTO.builder().price(price).discount(discount).build());
		products.add(ProductDTO.builder().price(price).discount(discount).build());
		
		InvoiceDTO invoice = InvoiceDTO.builder().products(products)
				.build();
		InvoiceDTO result = calculateInvoiceTotal.apply(invoice);
		
		assertNotNull(result);
		assertEquals(Double.valueOf(2*(price - discount) - additionalDiscount), result.getNetPayableAmount());
		
	}
	
	@Test
	public void testWithOneProductAndWithOutAdditionDiscount() {
		final Double price = 100.0;
		final Double discount = 10.0;
		final Double additionalDiscount = 0.0;
		
		List<ProductDTO> products = new ArrayList<ProductDTO>();
		products.add(ProductDTO.builder().price(price).discount(discount).build());
		InvoiceDTO invoice = InvoiceDTO.builder().products(products)
				.build();
		InvoiceDTO result = calculateInvoiceTotal.apply(invoice);
		
		assertNotNull(result);
		assertEquals(Double.valueOf(price - discount - additionalDiscount), result.getNetPayableAmount());
		
	}
	
}
