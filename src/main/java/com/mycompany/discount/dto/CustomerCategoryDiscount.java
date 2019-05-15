package com.mycompany.discount.dto;

import java.time.Duration;

import org.apache.commons.lang.time.DurationFormatUtils;

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

	private Duration activeDuration = Duration.ofDays(30);
	private Double discountPercentage = 10.0;
	private boolean enabled = true;
}
