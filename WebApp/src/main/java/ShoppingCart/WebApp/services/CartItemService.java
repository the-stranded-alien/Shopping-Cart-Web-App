package ShoppingCart.WebApp.services;

import ShoppingCart.WebApp.models.Cart;
import ShoppingCart.WebApp.models.CartItem;

import java.util.Set;

public interface CartItemService {
    Set<CartItem> getAllCartItemsByCart(Cart cart);

    CartItem saveCartItem(CartItem cartItem);
}
