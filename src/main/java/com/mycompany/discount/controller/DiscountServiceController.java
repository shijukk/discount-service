package com.mycompany.discount.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.discount.dto.ErrorResponseDTO;
import com.mycompany.discount.dto.InvoiceDTO;
import com.mycompany.discount.service.DiscountService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author kkshi
 *
 */
@RestController()
@Slf4j
public class DiscountServiceController {

	@Autowired
	private DiscountService service;

	/**
	 * 
	 * @param invoice
	 * @return
	 */
	@ApiOperation(value = "Apply the configured discount for the products and total invoice", nickname = "applyDicount")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid Request", response = ErrorResponseDTO.class),
			@ApiResponse(code = 500, message = "Internal server error", response = ErrorResponseDTO.class),
			@ApiResponse(code = 200, message = "Successfully applied the discount", response = InvoiceDTO.class) })
	@PostMapping(path = "/applyDiscount", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public InvoiceDTO applyDicount(@Valid @RequestBody final InvoiceDTO invoice,
			@RequestHeader(name = "userId") final String userId) {
		log.info("applyDicount service called.");
		log.info(invoice.toString());
		return service.applyDiscount(invoice, userId);
	}

}
