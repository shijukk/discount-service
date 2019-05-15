package com.mycompany.discount.service;

import com.mycompany.discount.dto.InvoiceDTO;

/**
 * 
 * @author kkshi
 *
 */
public interface DiscountService {
	
	/**
	 * 
	 * @param invoice
	 * @param userId
	 * @return
	 */
	InvoiceDTO applyDiscount(InvoiceDTO invoice, String userId); 
}
