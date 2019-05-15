package com.mycompany.discount.function;

import java.time.Duration;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycompany.discount.config.CustomerDiscount;
import com.mycompany.discount.dto.UserInfoDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GetCustomerDiscount implements Function<UserInfoDTO, Double> {
	
	@Autowired
	private CustomerDiscount customerDiscount = new CustomerDiscount();

	@Override
	public Double apply(final UserInfoDTO user) {
		final Duration activeDuration = Duration.ofDays(0).plusDays(user.getActiveDuration());
		if(customerDiscount.isEnabled()) {
			 return customerDiscount.getCustomerCategories().stream()
			.filter( category ->   activeDuration.compareTo(category.getActiveDuration()) >= 0)
			.findFirst()
			.map((category) -> {return category.getDiscountPercentage();})
			.orElse(null); 
		}
		return null;
	}

}
