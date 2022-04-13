package ShoppingCart.WebApp.controllers;

import ShoppingCart.WebApp.models.Cart;
import ShoppingCart.WebApp.models.CartItem;
import ShoppingCart.WebApp.models.Product;
import ShoppingCart.WebApp.services.CartItemService;
import ShoppingCart.WebApp.services.CartService;
import ShoppingCart.WebApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public String viewCart(@PathVariable(value="id") Long id, Model model) {
        Cart cart = this.cartService.getCartById(id);
        model.addAttribute("cart", cart);
        return "cart/home";
    }

    @GetMapping("/{id}/addItem")
    public String addCartItem(@PathVariable(value = "id") Long id, Model model) {
        CartItem cartItem = new CartItem();
        Cart cart = this.cartService.getCartById(id);
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("cart", cart);
        model.addAttribute("cartItem", cartItem);
        return "cart/newItem";
    }

    @PostMapping("/{id}/saveItem")
    public String saveCartItem(@PathVariable(value = "id") Long id, @ModelAttribute("cartItem") @RequestBody CartItem cartItem) {
        this.cartService.saveCartItemToCart(id, cartItem);
        return "redirect:/cart/{id}";
    }
}
