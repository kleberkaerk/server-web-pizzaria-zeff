package com.webservicepizzariazeff.www.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 12)
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_rating", length = 13)
    private PriceRating priceRating;

    @Column(name = "image", length = 100)
    private String image;

    protected Product() {
    }

    private Product(Long id, String name, String description, BigDecimal price, Type type, PriceRating priceRating, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.priceRating = priceRating;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Type getType() {
        return type;
    }

    public PriceRating getPriceRating() {
        return priceRating;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", type=" + type +
                ", priceRating=" + priceRating +
                ", image='" + image + '\'' +
                '}';
    }

    public static final class ProductBuilder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Type type;
        private PriceRating priceRating;
        private String image;

        private ProductBuilder() {
        }

        public static ProductBuilder builder() {
            return new ProductBuilder();
        }

        public ProductBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder type(Type type) {
            this.type = type;
            return this;
        }

        public ProductBuilder priceRating(PriceRating priceRating) {
            this.priceRating = priceRating;
            return this;
        }

        public ProductBuilder image(String image) {
            this.image = image;
            return this;
        }

        public Product build() {
            return new Product(id, name, description, price, type, priceRating, image);
        }
    }
}
