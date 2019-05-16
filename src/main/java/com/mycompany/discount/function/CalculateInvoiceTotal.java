package com.mycompany.discount.function;

import java.util.function.UnaryOperator;

import javax.validation.constraints.NotNull;

import com.mycompany.discount.config.InvoiceLevelDiscount;
import com.mycompany.discount.dto.InvoiceDTO;
import com.mycompany.discount.dto.ProductDTO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalculateInvoiceTotal implements UnaryOperator<InvoiceDTO> {

	@NotNull
	private InvoiceLevelDiscount invoiceLevelDiscount;

	@Override
	public InvoiceDTO apply(final InvoiceDTO invoice) {
		Double totalPrice = 0.0;
		Double totalProductDiscount = 0.0;
		for (ProductDTO product : invoice.getProducts()) {
			totalPrice = totalPrice + product.getPrice();
			totalProductDiscount = totalProductDiscount + product.getDiscount();
		}
		Double totalPayable = totalPrice - totalProductDiscount;
		Double additionalDiscount = 0.0;
		int flatDiscountApplicableCount = 0;
		if (null != invoiceLevelDiscount.getDiscountApplyForEach() && null != invoiceLevelDiscount.getFlatDiscount()) {
			flatDiscountApplicableCount = (int) (totalPayable / invoiceLevelDiscount.getDiscountApplyForEach());
			additionalDiscount = flatDiscountApplicableCount * invoiceLevelDiscount.getFlatDiscount();
		}
		return InvoiceDTO.builder().products(invoice.getProducts()).netPayableAmount(totalPayable - additionalDiscount)
				.currencyCode(invoice.getCurrencyCode()).additionalDiscount(additionalDiscount)
				.totalDiscount(totalProductDiscount + additionalDiscount).totalProductDiscount(totalProductDiscount)
				.totalPrice(totalPrice).build();
	}

}
