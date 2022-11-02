package com.webservicepizzariazeff.www.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "purchases")
@SuppressWarnings("java:S107")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "date_and_time", length = 16)
    private String dateAndTime;

    @Column(name = "card_name", length = 45)
    private String cardName;

    @Column(name = "active")
    private boolean isActive;

    @Column(name = "finished")
    private boolean isFinished;

    @Column(name = "delivered")
    private boolean isDelivered;

    @Column(name = "payment_through_the_website")
    private boolean isPaymentThroughTheWebsite;

    @OneToMany(mappedBy = "purchase")
    private List<PurchasedProduct> purchasedProducts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    protected Purchase() {
    }

    private Purchase(
            Long id,
            BigDecimal amount,
            String dateAndTime,
            String cardName,
            boolean isActive,
            boolean isFinished,
            boolean isDelivered,
            boolean isPaymentThroughTheWebsite,
            User user,
            Address address,
            List<PurchasedProduct> purchasedProducts
    ) {
        this.id = id;
        this.amount = amount;
        this.dateAndTime = dateAndTime;
        this.cardName = cardName;
        this.isActive = isActive;
        this.isFinished = isFinished;
        this.isDelivered = isDelivered;
        this.isPaymentThroughTheWebsite = isPaymentThroughTheWebsite;
        this.user = user;
        this.address = address;
        this.purchasedProducts = purchasedProducts;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getCardName() {
        return cardName;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public boolean isPaymentThroughTheWebsite() {
        return isPaymentThroughTheWebsite;
    }

    public User getUser() {
        return user;
    }

    public Address getAddress() {
        return address;
    }

    public List<PurchasedProduct> getPurchasedProducts() {
        return purchasedProducts;
    }

    @PrePersist
    public void prePersist(){

        this.isActive = true;
        this.isFinished = false;
        this.isDelivered = false;
        this.dateAndTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm"));
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", amount=" + amount +
                ", dateAndTime='" + dateAndTime + '\'' +
                ", cardName='" + cardName + '\'' +
                ", isActive=" + isActive +
                ", isFinished=" + isFinished +
                ", isDelivered=" + isDelivered +
                ", isPaymentThroughTheWebsite=" + isPaymentThroughTheWebsite +
                ", purchasedProducts=" + purchasedProducts +
                ", user=" + user +
                ", address=" + address +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return isActive == purchase.isActive &&
                isFinished == purchase.isFinished &&
                isDelivered == purchase.isDelivered &&
                isPaymentThroughTheWebsite == purchase.isPaymentThroughTheWebsite &&
                Objects.equals(id, purchase.id) &&
                Objects.equals(amount, purchase.amount) &&
                Objects.equals(dateAndTime, purchase.dateAndTime) &&
                Objects.equals(cardName, purchase.cardName) &&
                Objects.equals(purchasedProducts, purchase.purchasedProducts) &&
                Objects.equals(user, purchase.user) &&
                Objects.equals(address, purchase.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, dateAndTime, cardName, isActive, isFinished, isDelivered, isPaymentThroughTheWebsite, purchasedProducts, user, address);
    }

    public static final class PurchaseBuilder {
        private Long id;
        private BigDecimal amount;
        private String dateAndTime;
        private String cardName;
        private boolean isActive;
        private boolean isFinished;
        private boolean isDelivered;
        private boolean isPaymentThroughTheWebsite;
        private List<PurchasedProduct> purchasedProducts;
        private User user;
        private Address address;

        private PurchaseBuilder() {
        }

        public static PurchaseBuilder builder() {
            return new PurchaseBuilder();
        }

        public PurchaseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PurchaseBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public PurchaseBuilder dateAndTime(String dateAndTime) {
            this.dateAndTime = dateAndTime;
            return this;
        }

        public PurchaseBuilder cardName(String cardName) {
            this.cardName = cardName;
            return this;
        }

        public PurchaseBuilder isActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public PurchaseBuilder isFinished(boolean isFinished) {
            this.isFinished = isFinished;
            return this;
        }

        public PurchaseBuilder isDelivered(boolean isDelivered) {
            this.isDelivered = isDelivered;
            return this;
        }

        public PurchaseBuilder isPaymentThroughTheWebsite(boolean isPaymentThroughTheWebsite) {
            this.isPaymentThroughTheWebsite = isPaymentThroughTheWebsite;
            return this;
        }

        public PurchaseBuilder purchasedProducts(List<PurchasedProduct> purchasedProducts) {
            this.purchasedProducts = purchasedProducts;
            return this;
        }

        public PurchaseBuilder user(User user) {
            this.user = user;
            return this;
        }

        public PurchaseBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public Purchase build() {
            return new Purchase(id, amount, dateAndTime, cardName, isActive, isFinished, isDelivered, isPaymentThroughTheWebsite, user, address, purchasedProducts);
        }
    }
}
