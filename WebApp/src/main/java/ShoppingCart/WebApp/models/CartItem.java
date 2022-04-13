package ShoppingCart.WebApp.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CartItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    private Integer itemQuantity;
    private Double salesTaxRate;
    private Double subTotalWithoutTax;
    private Double subSalesTax;
    private Double subTotal;

    public CartItem() {

    }

    public CartItem(Integer itemQuantity, Double salesTaxRate, Double subTotalWithoutTax, Double subSalesTax, Double subTotal) {
        this.itemQuantity = itemQuantity;
        this.salesTaxRate = salesTaxRate;
        this.subTotalWithoutTax = subTotalWithoutTax;
        this.subSalesTax = subSalesTax;
        this.subTotal = subTotal;
    }

    public Long getId() {
        return id;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public Double getSubTotalWithoutTax() {
        return subTotalWithoutTax;
    }

    public Double getSubSalesTax() {
        return subSalesTax;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public Product getProduct() {
        return product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void setSubTotalWithoutTax(Double subTotalWithoutTax) {
        this.subTotalWithoutTax = subTotalWithoutTax;
    }

    public void setSubSalesTax(Double subSalesTax) {
        this.subSalesTax = subSalesTax;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getSalesTaxRate() {
        return salesTaxRate;
    }

    public void setSalesTaxRate(Double salesTaxRate) {
        this.salesTaxRate = salesTaxRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof CartItem)) return false;
        CartItem other = (CartItem) o;
        return (id != null && id.equals(other.getId()));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", cart=" + cart +
                ", itemQuantity=" + itemQuantity +
                ", subTotalWithoutTax=" + subTotalWithoutTax +
                ", subSalesTax=" + subSalesTax +
                ", subTotal=" + subTotal +
                '}';
    }
}
