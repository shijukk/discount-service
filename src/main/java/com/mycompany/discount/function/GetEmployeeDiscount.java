package com.mycompany.discount.function;

import java.util.function.ToDoubleFunction;

import com.mycompany.discount.config.EmployeeDiscount;
import com.mycompany.discount.dto.UserInfoDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class GetEmployeeDiscount implements ToDoubleFunction<UserInfoDTO> {

	private EmployeeDiscount employeeDiscount;

	@Override
	public double applyAsDouble(final UserInfoDTO user) {
		log.info("Applying employeeDiscount configured : {}", employeeDiscount.toString());
		if (employeeDiscount.isEnabled()) {
			return employeeDiscount.getDiscountPercentage();
		}
		return 0.0;
	}

}
