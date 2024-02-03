package com.equalexperts.shoppingcart.services.impls;

import com.equalexperts.shoppingcart.exceptions.BaseShoppingCartException;
import com.equalexperts.shoppingcart.exceptions.ErrorMessage;
import com.equalexperts.shoppingcart.exceptions.ProductNotFoundException;
import com.equalexperts.shoppingcart.models.ExternalProductApiResponse;
import com.equalexperts.shoppingcart.services.ExternalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.equalexperts.shoppingcart.exceptions.ErrorMessage.*;

/**
 * Implementation of the {@link ExternalProductService} interface.
 * This class is responsible for business logic related to products.
 * It makes use of RestTemplate for calling an external Product API.
 *
 * @author yateshkumar
 */
@Service
public class ExternalProductServiceImpl implements ExternalProductService {

    private final RestTemplate restTemplate;

    @Value("${product-api.base-url}")
    private String productApiBaseUrl;

    /**
     * Constructs an ExternalProductServiceImpl with the specified RestTemplate.
     *
     * @param restTemplate the RestTemplate for making external API requests.
     */
    @Autowired
    public ExternalProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves product information by name from the external API.
     *
     * @param productName the name of the product to retrieve.
     * @return the product information retrieved from the external API.
     * @throws ProductNotFoundException if the product is not found.
     */
    @Override
    public ExternalProductApiResponse getProductByName(String productName) {
        ResponseEntity<ExternalProductApiResponse> responseEntity = null;
        try {
            String productApiUrl = productApiBaseUrl+productName.toLowerCase()+".json";

            responseEntity = restTemplate.getForEntity(productApiUrl, ExternalProductApiResponse.class);

        } catch (HttpClientErrorException ex) {
            handleHttpClientErrorException(ex,productName);
        } catch (Exception ex) {
            handleGenericException(ex);
        }

        if (responseEntity == null || responseEntity.getBody() == null) {
            throw new ProductNotFoundException(PRODUCT_NOT_FOUND, productName);
        }
        return responseEntity.getBody();
    }

    /**
     * Handles HTTP client errors, such as 404 Not Found, during the external API request.
     *
     * @param ex           the HttpClientErrorException thrown during the request.
     * @param productName  the name of the product associated with the error.
     */
    private void handleHttpClientErrorException(HttpClientErrorException ex, String productName) {
        HttpStatus statusCode = ex.getStatusCode();
        if (statusCode == HttpStatus.NOT_FOUND) {
            throw new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND,productName);
        }

        throw new BaseShoppingCartException(EXTERNAL_API_ERROR, ex, ex.getStatusCode());
    }

    /**
     * Handles generic exceptions thrown during the external API request.
     *
     * @param ex the generic exception thrown during the request.
     */
    private void handleGenericException(Exception ex) {

        throw new BaseShoppingCartException(GET_PRODUCT_GENERIC_ERROR, ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}