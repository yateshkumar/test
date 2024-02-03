package com.equalexperts.shoppingcart.services;

import com.equalexperts.shoppingcart.exceptions.BaseShoppingCartException;
import com.equalexperts.shoppingcart.exceptions.ProductNotFoundException;
import com.equalexperts.shoppingcart.models.ExternalProductApiResponse;
import com.equalexperts.shoppingcart.services.impls.ExternalProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalProductServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExternalProductServiceImpl externalProductService;


    @Test
    public void getProductByName_Success() {
       
        String productName = "testProduct";
        ExternalProductApiResponse expectedResponse = new ExternalProductApiResponse();
        ResponseEntity<ExternalProductApiResponse> responseEntity = ResponseEntity.ok(expectedResponse);
        
        when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenReturn(responseEntity);
        ExternalProductApiResponse actualResponse = externalProductService.getProductByName(productName);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getProductByName_ProductNotFound() {
        String productName = "nonexistentProduct";
        ResponseEntity<ExternalProductApiResponse> responseEntity = ResponseEntity.notFound().build();

        when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenReturn(responseEntity);

        assertThrows(ProductNotFoundException.class, () -> externalProductService.getProductByName(productName));
    }

    @Test
    public void getProductByName_HttpClientError() {
        String productName = "errorProduct";

        when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(ProductNotFoundException.class, () -> externalProductService.getProductByName(productName));
    }

    @Test
    public void getProductByName_HttpClientError1() {
        String productName = "errorProduct";

        when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(BaseShoppingCartException.class, () -> externalProductService.getProductByName(productName));
    }

    @Test
    public void getProductByName_GenericException() {
       
        String productName = "genericErrorProduct";
        
        when(restTemplate.getForEntity(any(String.class), any(Class.class))).thenThrow(new RuntimeException());

        assertThrows(BaseShoppingCartException.class, () -> externalProductService.getProductByName(productName));
    }
}
