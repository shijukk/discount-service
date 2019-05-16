package com.mycompany.discount.function;

import java.util.function.ToDoubleFunction;

import com.mycompany.discount.config.AffiliateDiscount;
import com.mycompany.discount.dto.UserInfoDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class GetAffiliateDiscount implements ToDoubleFunction<UserInfoDTO> {

	private AffiliateDiscount affiliateDiscount;

	@Override
	public double applyAsDouble(UserInfoDTO arg0) {
		log.info("Applying affiliateDiscount configured : {}", affiliateDiscount.toString());
		if (affiliateDiscount.isEnabled()) {
			return affiliateDiscount.getDiscountPercentage();
		}
		return 0.0;
	}

}
