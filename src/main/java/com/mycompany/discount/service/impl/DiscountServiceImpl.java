package com.mycompany.discount.service.impl;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.discount.dao.UserInfoDAO;
import com.mycompany.discount.dto.InvoiceDTO;
import com.mycompany.discount.dto.ProductDTO;
import com.mycompany.discount.dto.UserInfoDTO;
import com.mycompany.discount.function.CalculateInvoiceTotal;
import com.mycompany.discount.function.GetPayableAmount;
import com.mycompany.discount.service.DiscountService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	private UserInfoDAO userInfoDao;
	
	private BiFunction<UserInfoDTO, ProductDTO, ProductDTO> getDiscount = new GetPayableAmount();
	private Function<InvoiceDTO, InvoiceDTO> calculateInvoiceTotal = new CalculateInvoiceTotal();

	@Override
	public InvoiceDTO applyDiscount(final InvoiceDTO invoice, final String userId) {

		final UserInfoDTO userInfo = userInfoDao.getUserInfo(userId);

		return calculateInvoiceTotal.apply(InvoiceDTO
				.builder().products(invoice.getProducts().stream()
						.map((product) -> getDiscount.apply(userInfo, product)).collect(Collectors.toList()))
				.currencyCode(invoice.getCurrencyCode()).build());

	}

}
