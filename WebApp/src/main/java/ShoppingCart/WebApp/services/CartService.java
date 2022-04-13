package ShoppingCart.WebApp.services;

import ShoppingCart.WebApp.models.Cart;
import ShoppingCart.WebApp.models.CartItem;

public interface CartService {
    void generateCart();

    Cart getCartById(Long id);

    void saveCartItemToCart(Long id, CartItem cartItem);
}
