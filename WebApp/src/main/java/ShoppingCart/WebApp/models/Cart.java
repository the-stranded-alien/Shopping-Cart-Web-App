package ShoppingCart.WebApp.models;

import org.springframework.data.relational.core.sql.In;

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

    public Integer getThresholdQuantityForDiscount() {
        return thresholdQuantityForDiscount;
    }

    public Double getDiscountRate() {
        return discountRate;
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

    public void setDiscountDetails(Double discountRate, Integer thresholdQuantityForDiscount) {
        this.discountRate = discountRate;
        this.thresholdQuantityForDiscount = thresholdQuantityForDiscount;
    }

    public void updateCartTotals(Integer numberOfItemsInCart, Double cartTotalWithoutSalesTax, Double cartTotalSalesTax, Double cartTotal) {
        this.numberOfItemsInCart = numberOfItemsInCart;
        this.cartTotalWithoutSalesTax = cartTotalWithoutSalesTax;
        this.cartTotalSalesTax = cartTotalSalesTax;
        this.cartTotal = cartTotal;
    }

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setCart(this);
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
