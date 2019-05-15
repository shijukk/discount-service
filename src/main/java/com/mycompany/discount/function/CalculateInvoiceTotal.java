package com.mycompany.discount.function;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.mycompany.discount.dto.InvoiceDTO;
import com.mycompany.discount.dto.ProductDTO;

public class CalculateInvoiceTotal implements Function<InvoiceDTO, InvoiceDTO> {

	@Override
	public InvoiceDTO apply(final InvoiceDTO invoice) {
		Double totalPrice = 0.0;
		Double totalProductDiscount = 0.0;
		for (ProductDTO product : invoice.getProducts()) {
			totalPrice = totalPrice + product.getPrice();
			totalProductDiscount = totalProductDiscount + product.getDiscount();
		}
		Double totalPayable = totalPrice - totalProductDiscount;
		int flatDiscountApplicableCount = Double.valueOf(totalPayable / 100).intValue();
		Double additionalDiscount = flatDiscountApplicableCount * 5.0;
		
		return InvoiceDTO.builder()
				.products(invoice.getProducts())
				.netPayableAmount(totalPayable - additionalDiscount)
				.currencyCode(invoice.getCurrencyCode())
				.additionalDiscount(additionalDiscount)
				.totalDiscount(totalProductDiscount + additionalDiscount)
				.totalProductDiscount(totalProductDiscount)
				.totalPrice(totalPrice)
				.build();
	}

}
