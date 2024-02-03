
package com.equalexperts.shoppingcart.controllers;


import com.equalexperts.shoppingcart.models.AddProductRequest;
import com.equalexperts.shoppingcart.models.CartTotals;
import com.equalexperts.shoppingcart.models.ShoppingCart;
import com.equalexperts.shoppingcart.models.ShoppingCartDTO;
import com.equalexperts.shoppingcart.services.ShoppingCartService;
import org.mockito.InjectMocks;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static com.equalexperts.shoppingcart.utils.ShoppingCartTestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartControllerTest {

    @Mock
    private ShoppingCartService shoppingCartService;

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    private static final String TEST_PRODUCT = "TestProduct";

    @Test
    public void testAddProductToCart_Success() {
        AddProductRequest request = getAddProductRequest(TEST_PRODUCT,1,1L);
        CartTotals expectedTotals = new CartTotals(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(11));
        ShoppingCartDTO expectedCart = getShoppingCartDTO(1L, TEST_PRODUCT, BigDecimal.valueOf(5L),1,expectedTotals);
        when(shoppingCartService.addProductToShoppingCart(request)).thenReturn(expectedCart);

        ResponseEntity<?> response = shoppingCartController.addProductToCart(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCart, response.getBody());
    }

    @Test
    public void testGetShoppingCart_Success() {
        String userId = "123";
        CartTotals expectedTotals = new CartTotals(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.valueOf(11));
        ShoppingCartDTO shoppingCartDTO = getShoppingCartDTO(1L, TEST_PRODUCT, BigDecimal.valueOf(5L),1,expectedTotals);
        when(shoppingCartService.getShoppingCart(anyString())).thenReturn(shoppingCartDTO);

        ResponseEntity<?> response = shoppingCartController.getShoppingCart(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(shoppingCartDTO, response.getBody());
    }

}
