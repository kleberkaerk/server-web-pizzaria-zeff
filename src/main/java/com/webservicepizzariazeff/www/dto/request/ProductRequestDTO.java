package com.webservicepizzariazeff.www.dto.request;

import com.webservicepizzariazeff.www.domain.PriceRating;
import com.webservicepizzariazeff.www.domain.Type;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductRequestDTO {

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String description;

    @NotNull
    private final BigDecimal price;

    @NotNull
    private final Type type;

    @NotNull
    private final PriceRating priceRating;

    private ProductRequestDTO(String name, String description, BigDecimal price, Type type, PriceRating priceRating) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.priceRating = priceRating;
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

    @Override
    public String toString() {
        return "ProductRequestDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", type=" + type +
                ", priceRating=" + priceRating +
                '}';
    }

    public static final class ProductRequestDTOBuilder {
        private @NotEmpty String name;
        private @NotEmpty String description;
        private @NotNull BigDecimal price;
        private @NotNull Type type;
        private @NotNull PriceRating priceRating;

        private ProductRequestDTOBuilder() {
        }

        public static ProductRequestDTOBuilder builder() {
            return new ProductRequestDTOBuilder();
        }

        public ProductRequestDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductRequestDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductRequestDTOBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductRequestDTOBuilder type(Type type) {
            this.type = type;
            return this;
        }

        public ProductRequestDTOBuilder priceRating(PriceRating priceRating) {
            this.priceRating = priceRating;
            return this;
        }

        public ProductRequestDTO build() {
            return new ProductRequestDTO(name, description, price, type, priceRating);
        }
    }
}
