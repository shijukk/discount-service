package com.mycompany.discount.dto;

import java.time.Duration;

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
public class CustomerCategoryDiscount {

	private long activeDurationDays ;
	private Double discountPercentage = 10.0;
	private boolean enabled = true;
}
