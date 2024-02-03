package com.equalexperts.shoppingcart.services;

import com.equalexperts.shoppingcart.exceptions.ProductNotFoundException;
import com.equalexperts.shoppingcart.models.ExternalProductApiResponse;

/**
 * Service interface for managing product-related operations.
 * This interface defines operations related to product details.
 *
 * @author yateshkumar
 */
public interface ExternalProductService {

    /**
     * Retrieve product details by product name.
     *
     * @param productName the name of the product.
     * @return DTO with the product details.
     * @throws ProductNotFoundException if the product is not found.
     */
    ExternalProductApiResponse getProductByName(String productName);
}