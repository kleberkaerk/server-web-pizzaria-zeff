package com.webservicepizzariazeff.www.domain;

import javax.persistence.*;

@Entity
@Table(name = "purchased_products")
public class PurchasedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    protected PurchasedProduct() {
    }

    private PurchasedProduct(Long id, String name, Purchase purchase) {
        this.id = id;
        this.name = name;
        this.purchase = purchase;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public static final class PurchasedProductBuilder {
        private Long id;
        private String name;
        private Purchase purchase;

        private PurchasedProductBuilder() {
        }

        public static PurchasedProductBuilder builder() {
            return new PurchasedProductBuilder();
        }

        public PurchasedProductBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PurchasedProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PurchasedProductBuilder purchase(Purchase purchase) {
            this.purchase = purchase;
            return this;
        }

        public PurchasedProduct build() {
            return new PurchasedProduct(id, name, purchase);
        }
    }
}
