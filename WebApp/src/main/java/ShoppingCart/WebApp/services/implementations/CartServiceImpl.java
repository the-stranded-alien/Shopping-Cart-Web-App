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
            cart.setDiscountDetails(10.0D, 5);
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
                cart.updateCartTotals(0, 0.0D, 0.0D, 0.0D);
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
                cart.updateCartTotals(itemCount, totalWithoutSalesTax, salesTax, cartTotal);
            }
            cart.setDiscountDetails(10.00, 5);
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
                double subSalesTaxVal = (double) Math.round((newQuantity * productToAdd.getUnitPrice() * (productToAdd.getSalesTaxRate() / 100)) * 100) / 100;
                double subTotalWithoutTax = (double) Math.round((newQuantity * productToAdd.getUnitPrice()) * 100) / 100;
                if(newQuantity >= thresholdQuantityForDiscount) {
                    double discountedAmount = subTotalWithoutTax - (subTotalWithoutTax * discountRate / 100);
                    double salesTaxAfterDiscount = (productToAdd.getSalesTaxRate() / 100) * discountedAmount;
                    double newSubTotal = (double) Math.round((discountedAmount + salesTaxAfterDiscount) * 100) / 100;
                    existingCartItem.updateQuantityAndTotals(newQuantity, discountedAmount, salesTaxAfterDiscount, newSubTotal);
                } else {
                    double newSubTotal = (double) Math.round((subTotalWithoutTax + subSalesTaxVal) * 100) / 100;
                    existingCartItem.updateQuantityAndTotals(newQuantity, subTotalWithoutTax, subSalesTaxVal, newSubTotal);
                }
                this.cartRepository.save(cart);
                return;
            }
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(productToAdd);
        double subSalesTaxVal = (double) Math.round((cartItem.getItemQuantity() *
                productToAdd.getUnitPrice() * (productToAdd.getSalesTaxRate() / 100)) * 100) / 100;
        double subTotalWithoutTax = (double) Math.round((cartItem.getItemQuantity() * productToAdd.getUnitPrice()) * 100) / 100;
        if(cartItem.getItemQuantity() >= thresholdQuantityForDiscount) {
            double discountedAmount = subTotalWithoutTax - (subTotalWithoutTax * discountRate / 100);
            double salesTaxAfterDiscount = (productToAdd.getSalesTaxRate() / 100) * discountedAmount;
            double subTotalAfterDiscount = (double) Math.round((discountedAmount + salesTaxAfterDiscount) * 100) / 100;
            newCartItem.updateQuantityAndTotals(cartItem.getItemQuantity(), discountedAmount, salesTaxAfterDiscount, subTotalAfterDiscount);
        } else {
            double subTotal = (double) Math.round((subSalesTaxVal + subTotalWithoutTax) * 100) / 100;
            newCartItem.updateQuantityAndTotals(cartItem.getItemQuantity(), subTotalWithoutTax, subSalesTaxVal, subTotal);
        }
        cart.addCartItem(newCartItem);
        this.cartRepository.save(cart);
        return;
    }
}
