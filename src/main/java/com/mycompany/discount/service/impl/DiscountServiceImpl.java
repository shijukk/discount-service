package com.mycompany.discount.service.impl;

import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.discount.config.AffiliateDiscount;
import com.mycompany.discount.config.CustomerDiscount;
import com.mycompany.discount.config.DiscountExclusion;
import com.mycompany.discount.config.EmployeeDiscount;
import com.mycompany.discount.config.InvoiceLevelDiscount;
import com.mycompany.discount.dao.UserInfoDAO;
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
import com.mycompany.discount.service.DiscountService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	private UserInfoDAO userInfoDao;

	@Autowired
	private AffiliateDiscount affiliateDiscount;

	@Autowired
	private EmployeeDiscount employeeDiscount;

	@Autowired
	private CustomerDiscount customerDiscount;

	@Autowired
	private DiscountExclusion discountExclusion;

	@Autowired
	private InvoiceLevelDiscount invoiceLevelDiscount;

	@Override
	public InvoiceDTO applyDiscount(final InvoiceDTO invoice, final String userId) {
		UnaryOperator<InvoiceDTO> calculateInvoiceTotal = new CalculateInvoiceTotal(invoiceLevelDiscount);
		BiFunction<ProductDTO, Double, ProductDTO> updateDiscount = this::updateDiscount;

		final UserInfoDTO user = userInfoDao.getUserInfo(userId);
		if (null == user) {
			throw new UserDoesNotExistException("User not found in the repository :" + userId);
		}
		final Double userDiscount = getUserDiscount(user);

		return calculateInvoiceTotal.apply(InvoiceDTO
				.builder().products(invoice.getProducts().stream()
						.map(product -> {return updateDiscount.apply(product, userDiscount);}).collect(Collectors.toList()))
				.currencyCode(invoice.getCurrencyCode()).build());

	}

	private Double getUserDiscount(@NotNull final UserInfoDTO user) {
		ToDoubleFunction<UserInfoDTO> getCustomerDiscount = new GetCustomerDiscount(customerDiscount);
		ToDoubleFunction<UserInfoDTO> getAffiliateDiscount = new GetAffiliateDiscount(affiliateDiscount);
		ToDoubleFunction<UserInfoDTO> getEmployeeDiscount = new GetEmployeeDiscount(employeeDiscount);

		Double discount;

		switch (user.getUserType()) {
		case "Customer":
			discount = getCustomerDiscount.applyAsDouble(user);
			break;
		case "Affiliate":
			discount = getAffiliateDiscount.applyAsDouble(user);
			break;
		case "Employee":
			discount = getEmployeeDiscount.applyAsDouble(user);
			break;
		default:
			discount = 0.0;
		}
		log.debug("Identified discount for user {} is {}.", user.getUserId(), discount);
		return discount;
	}

	private ProductDTO updateDiscount(@NotNull final ProductDTO product, @NotNull final Double discount) {
		BiFunction<ProductDTO, Double, ProductDTO> applyDiscount = new ApplyDiscount();
		Predicate<ProductDTO> checkProductExclusion = new CheckProductExclusion(discountExclusion);

		if (!checkProductExclusion.test(product)) {
			return applyDiscount.apply(product, discount);
		}

		return ProductDTO.builder().discount(0.0).payableAmount(product.getPrice())
				.price(product.getPrice()).productType(product.getProductType()).productName(product.getProductName())
				.build();
	}

}
