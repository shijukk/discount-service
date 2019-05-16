package com.mycompany.discount.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
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
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel
public class InvoiceDTO {

	/**
	 * 
	 */
	@NotNull
	@NotEmpty
	@Valid
	@ApiModelProperty( required = true)
	private List<ProductDTO> products;

	/**
	 * 
	 */
	private Double totalPrice;

	/**
	 * 
	 */
	private Double totalProductDiscount;

	/**
	 * 
	 */
	private Double additionalDiscount;

	/**
	 * 
	 */
	private Double totalDiscount;

	/**
	 * 
	 */
	private Double netPayableAmount;

	/**
	 * 
	 */
	@NotNull
	@ApiModelProperty( required = true)
	private String currencyCode;

}
