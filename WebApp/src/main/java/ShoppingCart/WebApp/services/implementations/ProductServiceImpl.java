package ShoppingCart.WebApp.services.implementations;

import ShoppingCart.WebApp.models.Product;
import ShoppingCart.WebApp.repositories.ProductRepository;
import ShoppingCart.WebApp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public void saveProduct(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> optional = this.productRepository.findById(product.getId());
        if(optional.isPresent()) {
            Product existingProduct = optional.get();
            existingProduct.updateProductDetails(product.getProductName(), product.getUnitPrice(), product.getSalesTaxRate(), product.getQuantityAvailable());
            return this.productRepository.save(existingProduct);
        }
        throw new RuntimeException("Product Not Found !");
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optional = this.productRepository.findById(id);
        Product product = null;
        if(optional.isPresent()) {
            product = optional.get();
        } else {
            throw new RuntimeException("Product with Id : " + id + " Not Found !");
        }
        return product;
    }

    @Override
    public void deleteProductById(Long id) { this.productRepository.deleteById(id); }

}
