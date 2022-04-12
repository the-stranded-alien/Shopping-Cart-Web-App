package ShoppingCart.WebApp.services;

import ShoppingCart.WebApp.models.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    void saveProduct(Product product);
    Product updateProduct(Product product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
}
