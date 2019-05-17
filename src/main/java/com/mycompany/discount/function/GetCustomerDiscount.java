package com.mycompany.discount.function;

import java.time.Duration;
import java.util.function.ToDoubleFunction;

import com.mycompany.discount.config.CustomerDiscount;
import com.mycompany.discount.dto.CustomerCategoryDiscount;
import com.mycompany.discount.dto.UserInfoDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class GetCustomerDiscount implements ToDoubleFunction<UserInfoDTO> {

	private CustomerDiscount customerDiscount;

	@Override
	public double applyAsDouble(final UserInfoDTO user) {
		log.info("customerDiscount configured : {}", customerDiscount.toString());
		if (customerDiscount.isEnabled()) {
			return customerDiscount.getCustomerCategories().stream()
					.filter(category -> user.getActiveDurationDays() >= category.getActiveDurationDays()).findFirst()
					.map(CustomerCategoryDiscount::getDiscountPercentage).orElse(0.0);
		}
		return 0.0;
	}

}
