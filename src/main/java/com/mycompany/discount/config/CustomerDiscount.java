package com.mycompany.discount.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import com.mycompany.discount.dto.CustomerCategoryDiscount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "customer")
public class CustomerDiscount {
	private List<CustomerCategoryDiscount> customerCategories = new ArrayList<>();
	private boolean enabled = true;

}
