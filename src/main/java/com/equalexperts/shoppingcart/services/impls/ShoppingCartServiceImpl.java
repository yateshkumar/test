package com.equalexperts.shoppingcart.services.impls;

import com.equalexperts.shoppingcart.exceptions.BaseShoppingCartException;
import com.equalexperts.shoppingcart.exceptions.ErrorMessage;
import com.equalexperts.shoppingcart.exceptions.ValidationFailedException;
import com.equalexperts.shoppingcart.mappers.ProductToCartItemMapper;
import com.equalexperts.shoppingcart.mappers.ShoppingCartMapper;
import com.equalexperts.shoppingcart.models.*;
import com.equalexperts.shoppingcart.repositories.CartItemRepository;
import com.equalexperts.shoppingcart.repositories.ShoppingCartRepository;
import com.equalexperts.shoppingcart.services.ExternalProductService;
import com.equalexperts.shoppingcart.services.ShoppingCartService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.regex.Pattern;

import static com.equalexperts.shoppingcart.exceptions.ErrorMessage.*;

/**
 * Implementation of the {@link ShoppingCartService} interface.
 *
 * @author yateshkumar
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ExternalProductService externalProductService;
    private final ProductToCartItemMapper productToCartItemMapper;
    private static final int maxAllowedQuantity = 20;
    private static final Pattern POSITIVE_NUMBER_WITHOUT_ZERO_PATTERN = Pattern.compile("^[1-9]\\d*$");

    /**
     * Constructs a ShoppingCartServiceImpl with the specified repositories, mapper, and external product service.
     *
     * @param shoppingCartRepository    the repository for shopping carts.
     * @param cartItemRepository        the repository for cart items.
     * @param shoppingCartMapper        the mapper for shopping cart.
     * @param productToCartItemMapper   the mapper for converting products to cart items.
     * @param externalProductService    the external service for retrieving product information.
     */
    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   CartItemRepository cartItemRepository,
                                   ShoppingCartMapper shoppingCartMapper,
                                   ProductToCartItemMapper productToCartItemMapper,
                                   ExternalProductService externalProductService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.shoppingCartMapper = shoppingCartMapper;
        this.productToCartItemMapper = productToCartItemMapper;
        this.externalProductService = externalProductService;
    }

    /**
     * Adds a product to the shopping cart.
     *
     * @param addProductRequest the request containing user account ID, product name, and quantity.
     * @return the updated shopping cart.
     * @throws BaseShoppingCartException if there is an issue with the shopping cart.
     */
    @Override
    @Transactional
    public ShoppingCartDTO addProductToShoppingCart(AddProductRequest addProductRequest) {

        // Validate input
        validateCartItem(addProductRequest);

        // Obtain product details including price from external API
        ExternalProductApiResponse externalProductApiResponse = externalProductService.getProductByName(
                addProductRequest.getProductName());

        // map the product to item
        CartItem cartItem = productToCartItemMapper.mapProductToCartItem(externalProductApiResponse,
                Integer.parseInt(addProductRequest.getQuantity()));

        long userAccountId = Long.parseLong(addProductRequest.getUserAccountId());
        // fetch the existing ShoppingCart
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userAccountId)
                .orElse(new ShoppingCart());

        // add CartItem to ShoppingCart
        ShoppingCart updatedShoppingCart = addCartItemToShoppingCartAssociatedWithUserAccountId(userAccountId, shoppingCart, cartItem);

        return shoppingCartMapper.mapToShoppingCartDTO(updatedShoppingCart,getCartTotals(userAccountId));
    }

    /**
     * Returns the current state of the shopping cart (all the cart items, subtotal, tax, and total).
     *
     * @param userAccountId the account ID of the logged-in user.
     * @return the current statue of the shopping cart.
     */
    @Override
    public ShoppingCartDTO getShoppingCart(String userAccountId) {

        validateUserAccountId(userAccountId);

        long longUserAccountId = Long.parseLong(userAccountId);
        Optional<ShoppingCart> shoppingCartFromDB = shoppingCartRepository.findById(longUserAccountId);

        if(!shoppingCartFromDB.isPresent()){
            throw new BaseShoppingCartException(SHOPPING_CART_EMPTY, HttpStatus.NOT_FOUND);
        }

        Set<CartItem> cartItems = shoppingCartFromDB.get().getCartItems();
        return new ShoppingCartDTO(longUserAccountId, new CartItemDTO().addAllCartItems(cartItems), getCartTotals(longUserAccountId));
    }

    /**
     *  Returns the cart totals i.e. subtotal, tax and total.
     * @param userAccountId the account ID of the logged-in user.
     * @return the cart totals
     */
    private CartTotals getCartTotals(Long userAccountId) {
        BigDecimal subtotal = calculateSubtotal(userAccountId);
        BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(0.125)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotal.add(tax).setScale(2, RoundingMode.UP);
        return new CartTotals(subtotal,tax,total);
    }

    /**
     * Calculate the subtotal of the products in the shopping cart.
     *
     * @param userAccountId the account ID of the logged-in user.
     * @return the subtotal value
     */
    private BigDecimal calculateSubtotal(Long userAccountId) {

        // Calculate subtotal by summing the prices of all products
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(userAccountId);
        if(!shoppingCart.isPresent()) {
            throw new BaseShoppingCartException(SHOPPING_CART_EMPTY, HttpStatus.NOT_FOUND);
        }

        return shoppingCart.get().getCartItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Validates the input parameters in the {@link AddProductRequest} before processing.
     *
     * @param addProductRequest the request containing information about the product to be added.
     * @throws ValidationFailedException if the validation fails.
     */
    private void validateCartItem(AddProductRequest addProductRequest) {

        if(addProductRequest == null){
            throw new ValidationFailedException(ADD_PRODUCT_REQUEST_NOT_FOUND);
        }

        validateUserAccountId(addProductRequest.getUserAccountId());

        validateProductName(addProductRequest.getProductName());

        validateQuantity(addProductRequest.getQuantity());
    }

    /**
     * Validates the input userAccountId in the {@link AddProductRequest}
     *
     * @param userAccountId the account ID of the logged-in user.
     * @throws ValidationFailedException if the validation fails.
     */
    private static void validateUserAccountId(String userAccountId) {

        boolean isValidUserAccountId;
        if(StringUtils.isEmpty(userAccountId) || StringUtils.isBlank(userAccountId.trim())){
            throw new ValidationFailedException(MANDATORY_FIELD_NOT_FOUND,"userAccountId");
        }
        try {
            isValidUserAccountId = POSITIVE_NUMBER_WITHOUT_ZERO_PATTERN.matcher(userAccountId).matches();
        }catch (NumberFormatException e){
            throw new ValidationFailedException(INVALID_PARAMETER_VALUE,"userAccountId", userAccountId,e);
        }
        if(!isValidUserAccountId){
            throw new ValidationFailedException(INVALID_PARAMETER_VALUE,"userAccountId",userAccountId);
        }

        // validate the userAccountId with actual user account stored in the db
    }

    /**
     * Validates the input productName in the {@link AddProductRequest}
     *
     * @param productName the name of the product
     * @throws ValidationFailedException if the validation fails.
     */
    private static void validateProductName(String productName) {

        if(StringUtils.isEmpty(productName) || StringUtils.isBlank(productName.trim())){
            throw new ValidationFailedException(MANDATORY_FIELD_NOT_FOUND,"productName");
        }
    }

    /**
     * Validates the input parameter quantity in the {@link AddProductRequest}
     *
     * @param quantity the quantity of the product
     * @throws ValidationFailedException if the validation fails.
     */
    private static void validateQuantity(String quantity) {

        boolean isValidQuantity;
        if(StringUtils.isEmpty(quantity)){
            throw new ValidationFailedException(MANDATORY_FIELD_NOT_FOUND, "quantity");
        }
        try {
            isValidQuantity = POSITIVE_NUMBER_WITHOUT_ZERO_PATTERN.matcher(quantity).matches();
        }catch (NumberFormatException e){
            throw new ValidationFailedException(INVALID_PARAMETER_VALUE, "quantity", quantity, e);
        }
        if(!isValidQuantity){
            throw new ValidationFailedException(INVALID_PARAMETER_VALUE,"quantity",quantity);
        }
        if(Integer.parseInt(quantity) > maxAllowedQuantity){
            throw new ValidationFailedException(MAX_ALLOWED_QUANTITY_EXCEED,quantity,String.valueOf(maxAllowedQuantity));
        }
    }

    /**
     * Adds a CartItem to the ShoppingCart, handling scenarios where the same item is being added.
     *
     * @param userAccountId the account ID of the logged-in user.
     * @param shoppingCart the shopping cart to update.
     * @param cartItem     the cart item to add.
     * @return ShoppingCart
     */
    private ShoppingCart addCartItemToShoppingCartAssociatedWithUserAccountId(long userAccountId, ShoppingCart shoppingCart, CartItem cartItem) {

        Set<CartItem> cartItems = shoppingCart.getCartItems();

        // set user account id to map
        if(shoppingCart.getUserAccountId() == null){
            shoppingCart.setUserAccountId(userAccountId);
        }

        // check if same cart item is being added in the shopping cart.
        Optional<CartItem> existingCartItemOptional = cartItems.stream().filter(p -> p.getName().equals(
                cartItem.getName())).findFirst();

        if(existingCartItemOptional.isPresent()){
            // handle case for same item being added in the shopping cart.
            CartItem existingCartItem = existingCartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity()+ existingCartItem.getQuantity());
            cartItems.removeIf(item -> item.getName().equals(existingCartItem.getName()));
            //cartItems.remove(existingCartItem);

            // remove the entry in CartItem as when ShoppingCart will be saved, a CartItem will also be saved.
            cartItemRepository.deleteById(existingCartItem.getId());
        }

        cartItems.add(cartItem);
        shoppingCart.addCartItems(cartItems);
        // Save the ShoppingCart
        return shoppingCartRepository.save(shoppingCart);
    }
}