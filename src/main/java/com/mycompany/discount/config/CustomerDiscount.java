package com.mycompany.discount.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.mycompany.discount.dto.CustomerCategoryDiscount;

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
@Component
public class CustomerDiscount {
	private List<CustomerCategoryDiscount> customerCategories = new ArrayList<CustomerCategoryDiscount>();
	private boolean enabled = true;
	 {
		customerCategories.add(new CustomerCategoryDiscount());
	}
}
