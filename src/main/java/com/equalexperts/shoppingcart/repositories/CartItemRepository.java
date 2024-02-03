package com.equalexperts.shoppingcart.repositories;

import com.equalexperts.shoppingcart.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on {@link CartItem} entities.
 *
 * @author yateshkumar
 */
@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {

}