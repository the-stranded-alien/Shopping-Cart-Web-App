package ShoppingCart.WebApp.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private Double unitPrice;
    private Double salesTaxRate;
    private Integer quantityAvailable;

    public Product() {

    }

    public Product(String productName, Double unitPrice, Double salesTaxRate, Integer quantityAvailable) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.salesTaxRate = salesTaxRate;
        this.quantityAvailable = quantityAvailable;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Double getSalesTaxRate() {
        return salesTaxRate;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setSalesTaxRate(Double salesTaxRate) {
        this.salesTaxRate = salesTaxRate;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public void updateProductDetails(String productName, Double unitPrice, Double salesTaxRate, Integer quantityAvailable) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.salesTaxRate = salesTaxRate;
        this.quantityAvailable = quantityAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product other = (Product) o;
        return (id != null && id.equals(other.getId()));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", salesTaxRate=" + salesTaxRate +
                ", quantityAvailable=" + quantityAvailable +
                '}';
    }
}
