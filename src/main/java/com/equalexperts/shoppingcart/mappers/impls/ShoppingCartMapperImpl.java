package com.equalexperts.shoppingcart.mappers.impls;

import com.equalexperts.shoppingcart.mappers.CartItemMapper;
import com.equalexperts.shoppingcart.mappers.ShoppingCartMapper;
import com.equalexperts.shoppingcart.models.CartTotals;
import com.equalexperts.shoppingcart.models.ShoppingCart;
import com.equalexperts.shoppingcart.models.ShoppingCartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;


/** Implementation of {@link ShoppingCartMapper}
 *
 * @author yateshkumar
 */
@Component
public class ShoppingCartMapperImpl implements ShoppingCartMapper {

    @Autowired
    private CartItemMapper cartItemMapper;

    /** Maps {@link ShoppingCart} & {@link CartTotals} to {@link ShoppingCartDTO}.
     *
     * @param shoppingCart shopping cart entity.
     * @param cartTotals cart totals that includes subtotal, tax and total
     * @return the mapped ShoppingCartDTO
     */
    @Override
    public ShoppingCartDTO mapToShoppingCartDTO(ShoppingCart shoppingCart, CartTotals cartTotals) {
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setUserAccountId(shoppingCart.getUserAccountId());
        shoppingCartDTO.setCartItems(shoppingCart.getCartItems().stream().map(cartItem -> cartItemMapper.mapToCartItemDTO(cartItem)).collect(Collectors.toCollection(HashSet::new)));
        shoppingCartDTO.setCartTotals(cartTotals);
        return shoppingCartDTO;
    }
}
