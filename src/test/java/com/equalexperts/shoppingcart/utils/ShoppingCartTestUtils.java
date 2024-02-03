package com.equalexperts.shoppingcart.utils;

import com.equalexperts.shoppingcart.models.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ShoppingCartTestUtils {

    public static AddProductRequest getAddProductRequest(String productName, int quantity, Long userAccountId) {
        AddProductRequest request = new AddProductRequest();
        request.setProductName(productName);
        request.setQuantity(String.valueOf(quantity));
        request.setUserAccountId(String.valueOf(userAccountId));
        return request;
    }

    public static ShoppingCart getShoppingCart(Long id, String cartItemName, BigDecimal cartItemPrice, int cartItemQuantity) {
        ShoppingCart expectedCart = new ShoppingCart();
        Set<CartItem> cartItems = new HashSet<>();
        cartItems.add(getCartItem(id, cartItemName,cartItemPrice,cartItemQuantity));
        expectedCart.setCartItems(cartItems);
        return expectedCart;
    }

    public static ShoppingCartDTO getShoppingCartDTO(Long id, String cartItemName, BigDecimal cartItemPrice, int cartItemQuantity, CartTotals cartTotals) {
        ShoppingCartDTO expectedCart = new ShoppingCartDTO();
        Set<CartItemDTO> cartItems = new HashSet<>();
        cartItems.add(getCartItemDTO(cartItemName,cartItemPrice,cartItemQuantity));
        expectedCart.setCartItems(cartItems);
        expectedCart.setCartTotals(cartTotals);
        return expectedCart;
    }

    private static CartItemDTO getCartItemDTO(String cartItemName, BigDecimal cartItemPrice, int cartItemQuantity) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setName(cartItemName);
        cartItemDTO.setPrice(cartItemPrice);
        cartItemDTO.setQuantity(cartItemQuantity);
        return cartItemDTO;
    }

    public static CartItem getCartItem(Long id, String cartItemName, BigDecimal cartItemPrice, int cartItemQuantity){
        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        cartItem.setQuantity(cartItemQuantity);
        cartItem.setName(cartItemName);
        cartItem.setPrice(cartItemPrice);
        return  cartItem;
    }

    public static ExternalProductApiResponse getExternalProductApiResponse(String title, BigDecimal price) {
        ExternalProductApiResponse externalProductApiResponse = new ExternalProductApiResponse();
        externalProductApiResponse.setPrice(price);
        externalProductApiResponse.setTitle(title);
        return externalProductApiResponse;
    }
}
