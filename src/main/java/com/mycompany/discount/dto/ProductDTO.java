package com.mycompany.discount.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author kkshi
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDTO {
	
	/**
	 * 
	 */
	private String productName;
	/**
	 * 
	 */
	@NotNull @NotBlank
	private String productType;
	
	/**
	 * 
	 */
	@NotNull 
	private Double price;
	
	/**
	 * 
	 */
	private Double discount;
	
	/**
	 * 
	 */
	private Double payableAmount;

}
