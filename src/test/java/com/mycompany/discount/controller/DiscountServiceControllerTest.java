package com.mycompany.discount.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientException;

import com.mycompany.discount.dto.ErrorResponseDTO;
import com.mycompany.discount.dto.InvoiceDTO;
import com.mycompany.discount.dto.ProductDTO;

/**
 * 
 * @author kkshi
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DiscountServiceControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String customerUserId = "USER12345";

	/**
	 * @throws MalformedURLException
	 * @throws RestClientException
	 * 
	 */
	@Test
	public void testControllerWithoutMandatoryElements() throws RestClientException, MalformedURLException {

		List<ProductDTO> products = new ArrayList<>();
		products.add(ProductDTO.builder().price(100.0).build());
		HttpHeaders headers = new HttpHeaders();
		headers.add("userId", "testValue");
		HttpEntity<InvoiceDTO> entity = new HttpEntity<>(InvoiceDTO.builder().products(products).build(), headers);
		ErrorResponseDTO errorResponse = restTemplate.postForObject(
				new URL("http://localhost:" + port + "/applyDiscount").toString(), entity, ErrorResponseDTO.class);

		assertNotNull(errorResponse);
		assertEquals("400.000.001", errorResponse.getErrorCode());

	}

	@Test
	public void testControllerWithValidRequest() throws RestClientException, MalformedURLException {

		List<ProductDTO> products = new ArrayList<>();
		products.add(ProductDTO.builder().price(100.0).productType("Test").build());
		HttpHeaders headers = new HttpHeaders();
		headers.add("userId", "USR1002");
		HttpEntity<InvoiceDTO> entity = new HttpEntity<>(
				InvoiceDTO.builder().products(products).currencyCode("AED").build(), headers);
		InvoiceDTO response = restTemplate.postForObject(
				new URL("http://localhost:" + port + "/applyDiscount").toString(), entity, InvoiceDTO.class);

		assertNotNull(response);
		assertNotNull(response.getNetPayableAmount());

	}
	
	@Test
	public void testControllerWithInvalidUser() throws RestClientException, MalformedURLException {

		List<ProductDTO> products = new ArrayList<>();
		products.add(ProductDTO.builder().price(100.0).productType("Test").build());
		HttpHeaders headers = new HttpHeaders();
		headers.add("userId", "Invalid");
		HttpEntity<InvoiceDTO> entity = new HttpEntity<>(
				InvoiceDTO.builder().products(products).currencyCode("AED").build(), headers);
		ErrorResponseDTO errorResponse = restTemplate.postForObject(
				new URL("http://localhost:" + port + "/applyDiscount").toString(), entity, ErrorResponseDTO.class);

		assertNotNull(errorResponse);
		assertEquals("400.000.002", errorResponse.getErrorCode());

	}

}
