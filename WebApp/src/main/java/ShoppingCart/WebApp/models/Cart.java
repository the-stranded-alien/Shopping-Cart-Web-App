package ShoppingCart.WebApp.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(
            mappedBy = "cart",
            cascade = {CascadeType.ALL}
    )
    private Set<CartItem> cartItems = new HashSet<>();

    private Integer numberOfItemsInCart;
    private Double cartTotalWithoutSalesTax;
    private Double cartTotalSalesTax;
    private Double cartTotal;
    private Integer thresholdQuantityForDiscount;
    private Double discountRate;

    public Cart() {

    }

    public Cart(Integer numberOfItemsInCart, Double cartTotalWithoutSalesTax, Double cartTotalSalesTax, Double cartTotal, Integer thresholdQuantityForDiscount, Double discountRate) {
        this.numberOfItemsInCart = numberOfItemsInCart;
        this.cartTotalWithoutSalesTax = cartTotalWithoutSalesTax;
        this.cartTotalSalesTax = cartTotalSalesTax;
        this.cartTotal = cartTotal;
        this.thresholdQuantityForDiscount = thresholdQuantityForDiscount;
        this.discountRate = discountRate;
    }

    public Long getId() {
        return id;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public Integer getNumberOfItemsInCart() {
        return numberOfItemsInCart;
    }

    public Double getCartTotalWithoutSalesTax() {
        return cartTotalWithoutSalesTax;
    }

    public Double getCartTotalSalesTax() {
        return cartTotalSalesTax;
    }

    public Double getCartTotal() {
        return cartTotal;
    }

    public Integer getThresholdQuantityForDiscount() {
        return thresholdQuantityForDiscount;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void setNumberOfItemsInCart(Integer numberOfItemsInCart) {
        this.numberOfItemsInCart = numberOfItemsInCart;
    }

    public void setCartTotalWithoutSalesTax(Double cartTotalWithoutSalesTax) {
        this.cartTotalWithoutSalesTax = cartTotalWithoutSalesTax;
    }

    public void setCartTotalSalesTax(Double cartTotalSalesTax) {
        this.cartTotalSalesTax = cartTotalSalesTax;
    }

    public void setCartTotal(Double cartTotal) {
        this.cartTotal = cartTotal;
    }

    public void setThresholdQuantityForDiscount(Integer thresholdQuantityForDiscount) {
        this.thresholdQuantityForDiscount = thresholdQuantityForDiscount;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setCart(this);
    }

    public void addCartItems(Set<CartItem> cartItems) {
        this.cartItems.addAll(cartItems);
        for(CartItem cartItem : cartItems) {
            cartItem.setCart(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart other = (Cart) o;
        return (id != null && id.equals(other.getId()));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "numberOfItemsInCart=" + numberOfItemsInCart +
                ", cartTotalWithoutSalesTax=" + cartTotalWithoutSalesTax +
                ", cartTotalSalesTax=" + cartTotalSalesTax +
                ", cartTotal=" + cartTotal +
                ", thresholdQuantityForDiscount=" + thresholdQuantityForDiscount +
                ", discountRate=" + discountRate +
                '}';
    }
}
