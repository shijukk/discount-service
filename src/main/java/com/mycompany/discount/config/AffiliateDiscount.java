package com.mycompany.discount.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AffiliateDiscount {
	private boolean enabled = true;
	private Double discountPercentage = 25.0;
}
