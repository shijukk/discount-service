package com.mycompany.discount.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class ProductDTO {
	
	/**
	 * 
	 */
	private String productName;
	/**
	 * 
	 */
	@NotNull @NotBlank
	@ApiModelProperty( required = true)
	private String productType;
	
	/**
	 * 
	 */
	@NotNull 
	@ApiModelProperty( required = true)
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
