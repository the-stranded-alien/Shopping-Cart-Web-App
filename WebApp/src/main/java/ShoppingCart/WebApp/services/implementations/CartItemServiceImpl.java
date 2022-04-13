package ShoppingCart.WebApp.services.implementations;

import ShoppingCart.WebApp.models.Cart;
import ShoppingCart.WebApp.models.CartItem;
import ShoppingCart.WebApp.repositories.CartItemRepository;
import ShoppingCart.WebApp.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Set<CartItem> getAllCartItemsByCart(Cart cart) {
        return new HashSet<>(this.cartItemRepository.findAll());
    }

    @Override
    public CartItem saveCartItem(CartItem cartItem) { return this.cartItemRepository.save(cartItem); }

}
