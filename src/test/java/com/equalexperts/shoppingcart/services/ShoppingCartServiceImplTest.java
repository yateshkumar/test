package com.equalexperts.shoppingcart.services;

import com.equalexperts.shoppingcart.exceptions.BaseShoppingCartException;
import com.equalexperts.shoppingcart.exceptions.ValidationFailedException;
import com.equalexperts.shoppingcart.mappers.ProductToCartItemMapper;
import com.equalexperts.shoppingcart.mappers.ShoppingCartMapper;
import com.equalexperts.shoppingcart.models.*;
import com.equalexperts.shoppingcart.repositories.CartItemRepository;
import com.equalexperts.shoppingcart.repositories.ShoppingCartRepository;
import com.equalexperts.shoppingcart.services.impls.ShoppingCartServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static com.equalexperts.shoppingcart.utils.ShoppingCartTestUtils.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceImplTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @Mock
    private ExternalProductService externalProductService;

    @Mock
    private ProductToCartItemMapper productToCartItemMapper;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    private static final String TEST_PRODUCT = "TestProduct";

    @Test
    public void addProductToShoppingCart_Success() {

        AddProductRequest addProductRequest = getAddProductRequest(TEST_PRODUCT,2,1L);

        ExternalProductApiResponse externalProductApiResponse = getExternalProductApiResponse(TEST_PRODUCT,BigDecimal.TEN);

        CartItem cartItem = getCartItem(1L, TEST_PRODUCT,BigDecimal.TEN,2);

        ShoppingCart existingShoppingCart = getShoppingCart(1L, TEST_PRODUCT, BigDecimal.TEN,2);

        CartTotals expectedTotals = new CartTotals(BigDecimal.valueOf(40), BigDecimal.valueOf(5), BigDecimal.valueOf(45));

        ShoppingCartDTO shoppingCartDTO = getShoppingCartDTO(1L, TEST_PRODUCT, BigDecimal.TEN, 2, expectedTotals);

        when(externalProductService.getProductByName(any())).thenReturn(externalProductApiResponse);
        when(productToCartItemMapper.mapProductToCartItem(any(), anyInt())).thenReturn(cartItem);
        when(shoppingCartRepository.findById(anyLong())).thenReturn(Optional.of(new ShoppingCart()));
        when(shoppingCartRepository.save(any())).thenReturn(existingShoppingCart);
        when(shoppingCartMapper.mapToShoppingCartDTO(any(),any())).thenReturn(shoppingCartDTO);

        ShoppingCartDTO updatedShoppingCart = shoppingCartService.addProductToShoppingCart(addProductRequest);

        assertNotNull(updatedShoppingCart);
        assertEquals(1, updatedShoppingCart.getCartItems().size());
        assertTrue(BigDecimal.valueOf(40).compareTo(updatedShoppingCart.getCartTotals().getSubtotal()) == 0);
        assertTrue(BigDecimal.valueOf(5).compareTo(updatedShoppingCart.getCartTotals().getTax())==0);
        assertTrue(BigDecimal.valueOf(45).compareTo(updatedShoppingCart.getCartTotals().getTotal())==0);
    }

    @Test
    public void addProductToShoppingCart_ValidationFailed_UserAccountIdNull() {
        AddProductRequest addProductRequest = getAddProductRequest(TEST_PRODUCT,2,null);
        addProductRequest.setUserAccountId(null);

        assertThrows(ValidationFailedException.class,
                () -> shoppingCartService.addProductToShoppingCart(addProductRequest));
    }

    @Test
    public void addProductToShoppingCart_ValidationFailed_InvalidUserAccountId() {
        AddProductRequest addProductRequest = getAddProductRequest(TEST_PRODUCT,2,1L);
        addProductRequest.setUserAccountId("1.1");

        assertThrows(ValidationFailedException.class,
                () -> shoppingCartService.addProductToShoppingCart(addProductRequest));

        addProductRequest.setUserAccountId("abc");
        assertThrows(ValidationFailedException.class,
                () -> shoppingCartService.addProductToShoppingCart(addProductRequest));
    }

    @Test
    public void addProductToShoppingCart_ValidationFailed_ProductNameNull() {
        AddProductRequest addProductRequest = getAddProductRequest(null,2,1L);

        assertThrows(ValidationFailedException.class,
                () -> shoppingCartService.addProductToShoppingCart(addProductRequest));
    }

    @Test
    public void addProductToShoppingCart_ValidationFailed_QuantityNull() {
        AddProductRequest addProductRequest = getAddProductRequest(TEST_PRODUCT,0,1L);
        addProductRequest.setQuantity(null);
        assertThrows(ValidationFailedException.class,
                () -> shoppingCartService.addProductToShoppingCart(addProductRequest));
    }

    @Test
    public void addProductToShoppingCart_ValidationFailed_InvalidQuantity() {
        AddProductRequest addProductRequest = getAddProductRequest(TEST_PRODUCT,0,1L);

        assertThrows(ValidationFailedException.class,
                () -> shoppingCartService.addProductToShoppingCart(addProductRequest));
    }

    @Test
    public void addProductToShoppingCart_ValidationFailed_MaximumAllowedQuantityExceed() {
        AddProductRequest addProductRequest = getAddProductRequest(TEST_PRODUCT,22,1L);

        assertThrows(ValidationFailedException.class,
                () -> shoppingCartService.addProductToShoppingCart(addProductRequest));
    }

    @Test
    public void addProductToShoppingCart_AddRequestEmpty() {
        assertThrows(ValidationFailedException.class,
                () -> shoppingCartService.addProductToShoppingCart(null));
    }

    @Test
    public void addProductToShoppingCart_SameItemAlreadyExists() {
        AddProductRequest addProductRequest = getAddProductRequest(TEST_PRODUCT,2,1L);

        ExternalProductApiResponse externalProductApiResponse = getExternalProductApiResponse(TEST_PRODUCT,BigDecimal.TEN);

        ShoppingCart existingShoppingCart = getShoppingCart(1L,TEST_PRODUCT,BigDecimal.TEN,3);

        CartItem cartItem = getCartItem(1L,TEST_PRODUCT,BigDecimal.TEN,2);

        CartTotals expectedTotals = new CartTotals(BigDecimal.valueOf(50), BigDecimal.valueOf(6.25), BigDecimal.valueOf(56.25));

        ShoppingCartDTO shoppingCartDTO = getShoppingCartDTO(1L,TEST_PRODUCT,BigDecimal.TEN,5,expectedTotals);

        when(externalProductService.getProductByName(any())).thenReturn(externalProductApiResponse);
        when(productToCartItemMapper.mapProductToCartItem(any(), anyInt())).thenReturn(cartItem);
        when(shoppingCartRepository.findById(anyLong())).thenReturn(Optional.of(existingShoppingCart));
        when(shoppingCartRepository.save(any())).thenReturn(existingShoppingCart);
        when(shoppingCartMapper.mapToShoppingCartDTO(any(), any())).thenReturn(shoppingCartDTO);
        ShoppingCartDTO updatedShoppingCart = shoppingCartService.addProductToShoppingCart(addProductRequest);

        assertNotNull(updatedShoppingCart);
        assertEquals(1, updatedShoppingCart.getCartItems().size());
        assertEquals(5, updatedShoppingCart.getCartItems().iterator().next().getQuantity());
        assertTrue(BigDecimal.valueOf(50).compareTo(updatedShoppingCart.getCartTotals().getSubtotal()) == 0);
        assertTrue(BigDecimal.valueOf(6.25).compareTo(updatedShoppingCart.getCartTotals().getTax())==0);
        assertTrue(BigDecimal.valueOf(56.25).compareTo(updatedShoppingCart.getCartTotals().getTotal())==0);
    }

    @Test
    public void getShoppingCart_Success() {
        ShoppingCart shoppingCart = getShoppingCart(1L,TEST_PRODUCT,BigDecimal.TEN,2);

        when(shoppingCartRepository.findById(anyLong())).thenReturn(Optional.of(shoppingCart));

        ShoppingCartDTO shoppingCartDTO = shoppingCartService.getShoppingCart("1");

        assertNotNull(shoppingCartDTO);
        assertNotNull(shoppingCartDTO.getCartTotals());
        assertEquals(BigDecimal.valueOf(20), shoppingCartDTO.getCartTotals().getSubtotal());
        assertTrue(BigDecimal.valueOf(2.50).compareTo(shoppingCartDTO.getCartTotals().getTax()) == 0);
        assertTrue(BigDecimal.valueOf(22.50).compareTo(shoppingCartDTO.getCartTotals().getTotal()) == 0);
    }

    @Test
    public void getShoppingCart_ShoppingCartEmpty() {
        when(shoppingCartRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(BaseShoppingCartException.class,
                () -> shoppingCartService.getShoppingCart("1"));
    }

}
