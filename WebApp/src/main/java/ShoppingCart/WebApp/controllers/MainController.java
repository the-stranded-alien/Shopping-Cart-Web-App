package ShoppingCart.WebApp.controllers;

import ShoppingCart.WebApp.models.Cart;
import ShoppingCart.WebApp.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private CartService cartService;

    @GetMapping("/")
    public String root() {
        this.cartService.generateCart();
        return "index";
    }
}
