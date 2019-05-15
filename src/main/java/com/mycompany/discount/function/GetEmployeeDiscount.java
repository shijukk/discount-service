package com.mycompany.discount.function;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycompany.discount.config.EmployeeDiscount;
import com.mycompany.discount.dto.UserInfoDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeDiscount implements Function<UserInfoDTO, Double> {

	@Autowired
	private EmployeeDiscount employeeDiscount = new EmployeeDiscount();

	@Override
	public Double apply(final UserInfoDTO user) {
		if (employeeDiscount.isEnabled()) {
			return employeeDiscount.getDiscountPercentage();
		}
		return null;
	}

}
