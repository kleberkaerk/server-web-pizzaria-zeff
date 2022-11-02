package com.webservicepizzariazeff.www.dto.response;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Type;

import java.math.BigDecimal;
import java.util.Objects;

@SuppressWarnings("java:S107")
public class ProductResponseDTO {

    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Type type;
    private final PriceRating priceRating;
    private final String image;
    private final boolean isStocked;

    private ProductResponseDTO(Long id, String name, String description, BigDecimal price, Type type, PriceRating priceRating, String image, boolean isStocked) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.priceRating = priceRating;
        this.image = image;
        this.isStocked = isStocked;
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

    public boolean isStocked() {
        return isStocked;
    }

    @Override
    public String toString() {
        return "ProductResponseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", type=" + type +
                ", priceRating=" + priceRating +
                ", image='" + image + '\'' +
                ", isStocked=" + isStocked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductResponseDTO that = (ProductResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    public static final class ProductResponseDTOBuilder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Type type;
        private PriceRating priceRating;
        private String image;
        private boolean isStocked;

        private ProductResponseDTOBuilder() {
        }

        public static ProductResponseDTOBuilder builder() {
            return new ProductResponseDTOBuilder();
        }

        public ProductResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProductResponseDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductResponseDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductResponseDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductResponseDTOBuilder type(Type type) {
            this.type = type;
            return this;
        }

        public ProductResponseDTOBuilder priceRating(PriceRating priceRating) {
            this.priceRating = priceRating;
            return this;
        }

        public ProductResponseDTOBuilder image(String image) {
            this.image = image;
            return this;
        }

        public ProductResponseDTOBuilder isStocked(boolean isStocked) {
            this.isStocked = isStocked;
            return this;
        }

        public ProductResponseDTO build() {
            return new ProductResponseDTO(id, name, description, price, type, priceRating, image, isStocked);
        }
    }
}
