package com.equalexperts.shoppingcart.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemDTO {

    private String name;

    private BigDecimal price;

    private int quantity;

    public Set<CartItemDTO> addAllCartItems(Set<CartItem> cartItems) {
        Set<CartItemDTO> cartItemDTOSet = new HashSet<>();
        for(CartItem cartItem : cartItems){
            cartItemDTOSet.add(new CartItemDTO(cartItem.getName(),cartItem.getPrice(),cartItem.getQuantity()));
        }
        return cartItemDTOSet;
    }
}
