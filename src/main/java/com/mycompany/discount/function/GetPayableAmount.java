package com.mycompany.discount.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import com.mycompany.discount.dto.ProductDTO;
import com.mycompany.discount.dto.UserInfoDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GetPayableAmount implements BiFunction<UserInfoDTO, ProductDTO, ProductDTO> {

	Function<UserInfoDTO, Double> getCustomerDiscount = new GetCustomerDiscount();
	Function<UserInfoDTO, Double> getAffiliateDiscount = new GetAffiliateDiscount();
	Function<UserInfoDTO, Double> getEmployeeDiscount = new GetEmployeeDiscount();
	BiFunction<ProductDTO, Double, ProductDTO> applyDiscount = new ApplyDiscount();
	Predicate<ProductDTO> checkProductExclusion = new CheckProductExclusion();

	@Override
	public ProductDTO apply(final UserInfoDTO arg0, final ProductDTO arg1) {

		Double discount = 0.0;
		if (!checkProductExclusion.test(arg1)) {
			switch(arg0.getUserType()) {
				case "Customer":
					discount = getCustomerDiscount.apply(arg0);
					break;
				case "Affiliate":
					discount = getAffiliateDiscount.apply(arg0);
					break;
				case "Employee":
					discount = getEmployeeDiscount.apply(arg0);
					break;
				default:
					discount = 0.0;
			}
			 
			if (discount == null) {
				discount = 0.0;
			}
		}
		
		return applyDiscount.apply(arg1, discount);
	}

}
