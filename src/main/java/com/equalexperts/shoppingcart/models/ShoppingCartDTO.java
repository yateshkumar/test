package com.equalexperts.shoppingcart.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingCartDTO {

    private Long userAccountId;

    private Set<CartItemDTO> cartItems = new HashSet<>();

    private CartTotals cartTotals;

    public ShoppingCartDTO(Set<CartItemDTO> cartItems){
        this.cartItems = cartItems;
    }
}
