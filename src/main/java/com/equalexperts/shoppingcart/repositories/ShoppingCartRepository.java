package com.equalexperts.shoppingcart.repositories;

import com.equalexperts.shoppingcart.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on {@link ShoppingCart} entities.
 *
 * @author yateshkumar
 */
@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

}