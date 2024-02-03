package com.equalexperts.shoppingcart.controllers;

import com.equalexperts.shoppingcart.models.AddProductRequest;
import com.equalexperts.shoppingcart.models.ShoppingCartDTO;
import com.equalexperts.shoppingcart.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controller class for managing shopping cart operations.
 *
 * @author yateshkumar
 */
@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    /**
     * Constructs a ShoppingCartController with the specified ShoppingCartService.
     *
     * @param shoppingCartService the shopping cart service.
     */
    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * Handles the request to add a product to the shopping cart.
     *
     * @param addProductRequest the request containing product information.
     * @return a ResponseEntity with the updated shopping cart.
     */
    @PostMapping(value = "/add-product")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductRequest addProductRequest) {
        ShoppingCartDTO shoppingCart = shoppingCartService.addProductToShoppingCart(addProductRequest);
        return ResponseEntity.ok(shoppingCart);
    }

    /**
     * Handles the request to get the shopping cart.
     *
     * @param userAccountId the account ID of the logged-in user.
     * @return a ResponseEntity with the calculated cart totals.
     */
    @GetMapping("/{userAccountId}")
    public ResponseEntity<?> getShoppingCart(@PathVariable("userAccountId") String userAccountId) {
        ShoppingCartDTO shoppingCart = shoppingCartService.getShoppingCart(userAccountId);
        return ResponseEntity.ok(shoppingCart);
    }
}
