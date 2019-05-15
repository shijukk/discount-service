package com.mycompany.discount.function;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycompany.discount.config.AffiliateDiscount;
import com.mycompany.discount.dto.UserInfoDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GetAffiliateDiscount implements Function<UserInfoDTO, Double> {

	private AffiliateDiscount affiliateDiscount= new AffiliateDiscount();

	@Override
	public Double apply(final UserInfoDTO user) {
		if (affiliateDiscount.isEnabled()) {
			return affiliateDiscount.getDiscountPercentage();
		}
		return null;
	}

}
