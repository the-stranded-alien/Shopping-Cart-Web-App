package ShoppingCart.WebApp.controllers;

import ShoppingCart.WebApp.models.Product;
import ShoppingCart.WebApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String viewAllProducts(Model model) {
        List<Product> allProducts = this.productService.getAllProducts();
        model.addAttribute("listProducts", allProducts);
        return "product/home";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product/newProduct";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        this.productService.saveProduct(product);
        return "redirect:/product";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") Product product) {
        Product updatedProduct = this.productService.updateProduct(product);
        return "redirect:/product";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id) {
        this.productService.deleteProductById(id);
        return "redirect:/product";
    }
}
