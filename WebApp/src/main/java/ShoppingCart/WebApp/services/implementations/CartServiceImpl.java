package ShoppingCart.WebApp.services.implementations;

import ShoppingCart.WebApp.models.Cart;
import ShoppingCart.WebApp.models.CartItem;
import ShoppingCart.WebApp.models.Product;
import ShoppingCart.WebApp.repositories.CartRepository;
import ShoppingCart.WebApp.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void generateCart() {
        if(!cartRepository.existsById(1L)) {
            Cart cart = new Cart();
            cart.setDiscountRate(10.00D);
            cart.setThresholdQuantityForDiscount(5);
            this.cartRepository.save(cart);
        }
        return;
    }

    @Override
    public Cart getCartById(Long id) {
        Optional<Cart> optionalCart = this.cartRepository.findById(id);
        if(optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            Set<CartItem> cartItems = cart.getCartItems();
            if(cartItems.isEmpty()) {
                cart.setNumberOfItemsInCart(0);
                cart.setCartTotalWithoutSalesTax(0.0D);
                cart.setCartTotalSalesTax(0.0D);
                cart.setCartTotal(0.0D);
            } else {
                int itemCount = 0;
                double totalWithoutSalesTax = 0.0;
                double salesTax = 0.0;
                double cartTotal = 0.0;
                for(CartItem item : cartItems) {
                    itemCount += item.getItemQuantity();
                    totalWithoutSalesTax += item.getSubTotalWithoutTax();
                    salesTax += item.getSubSalesTax();
                    cartTotal += item.getSubTotal();
                }
                totalWithoutSalesTax = (double) Math.round(totalWithoutSalesTax * 100) / 100;
                salesTax = (double) Math.round(salesTax * 100) / 100;
                cartTotal = (double) Math.round(cartTotal * 100) / 100;
                cart.setNumberOfItemsInCart(itemCount);
                cart.setCartTotalWithoutSalesTax(totalWithoutSalesTax);
                cart.setCartTotalSalesTax(salesTax);
                cart.setCartTotal(cartTotal);
            }
            cart.setThresholdQuantityForDiscount(5);
            cart.setDiscountRate(10.0D);
            return cart;
        }
        return new Cart();
    }

    @Override
    public void saveCartItemToCart(Long id, CartItem cartItem) {
        Cart cart = this.cartRepository.getById(id);
        Set<CartItem> itemsAlreadyPresent = cart.getCartItems();
        Product productToAdd = cartItem.getProduct();
        int thresholdQuantityForDiscount = cart.getThresholdQuantityForDiscount();
        double discountRate = cart.getDiscountRate();
        for(CartItem existingCartItem : itemsAlreadyPresent) {
            if(existingCartItem.getProduct().equals(productToAdd)) {
                Integer newQuantity = existingCartItem.getItemQuantity() + cartItem.getItemQuantity();
                existingCartItem.setItemQuantity(newQuantity);
                existingCartItem.setSalesTaxRate(productToAdd.getSalesTaxRate());
                double subSalesTaxVal = (double) Math.round((newQuantity * productToAdd.getUnitPrice() * (productToAdd.getSalesTaxRate() / 100)) * 100) / 100;
                double subTotalWithoutTax = (double) Math.round((newQuantity * productToAdd.getUnitPrice()) * 100) / 100;
                if(newQuantity >= thresholdQuantityForDiscount) {
                    double discountedAmount = subTotalWithoutTax - (subTotalWithoutTax * discountRate / 100);
                    double salesTaxAfterDiscount = (productToAdd.getSalesTaxRate() / 100) * discountedAmount;
                    existingCartItem.setSubTotalWithoutTax((double) Math.round(discountedAmount * 100) / 100);
                    existingCartItem.setSubSalesTax((double) Math.round(salesTaxAfterDiscount * 100) / 100);
                    existingCartItem.setSubTotal((double) Math.round((discountedAmount + salesTaxAfterDiscount) * 100) / 100);
                } else {
                    existingCartItem.setSubSalesTax(subSalesTaxVal);
                    existingCartItem.setSubTotalWithoutTax(subTotalWithoutTax);
                    existingCartItem.setSubTotal((double) Math.round((subTotalWithoutTax + subSalesTaxVal) * 100) / 100);
                }
                this.cartRepository.save(cart);
                return;
            }
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(productToAdd);
        newCartItem.setItemQuantity(cartItem.getItemQuantity());
        newCartItem.setSubSalesTax(productToAdd.getSalesTaxRate());
        double subSalesTaxVal = (double) Math.round((cartItem.getItemQuantity() *
                productToAdd.getUnitPrice() * (productToAdd.getSalesTaxRate() / 100)) * 100) / 100;
        double subTotalWithoutTax = (double) Math.round((cartItem.getItemQuantity() * productToAdd.getUnitPrice()) * 100) / 100;
        if(cartItem.getItemQuantity() >= thresholdQuantityForDiscount) {
            double discountedAmount = subTotalWithoutTax - (subTotalWithoutTax * discountRate / 100);
            double salesTaxAfterDiscount = (productToAdd.getSalesTaxRate() / 100) * discountedAmount;
            newCartItem.setSubTotalWithoutTax((double) Math.round(discountedAmount * 100) / 100);
            newCartItem.setSubSalesTax((double) Math.round(salesTaxAfterDiscount * 100) / 100);
            newCartItem.setSubTotal((double) Math.round((discountedAmount + salesTaxAfterDiscount) * 100) / 100);
        } else {
            newCartItem.setSubSalesTax(subSalesTaxVal);
            newCartItem.setSubTotalWithoutTax(subTotalWithoutTax);
            newCartItem.setSubTotal((double) Math.round((subSalesTaxVal + subTotalWithoutTax) * 100) / 100);
        }
        cart.addCartItem(newCartItem);
        this.cartRepository.save(cart);
        return;
    }
}
