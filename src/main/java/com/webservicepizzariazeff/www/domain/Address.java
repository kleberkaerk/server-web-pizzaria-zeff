package com.webservicepizzariazeff.www.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", length = 10)
    private String number;

    @Column(name = "road", length = 150)
    private String road;

    @Column(name = "district", length = 150)
    private String district;

    @Column(name = "city", length = 150)
    private String city;

    @Column(name = "state", length = 150)
    private String state;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected Address() {
    }

    private Address(Long id, String number, String road, String district, String city, String state, User user) {
        this.id = id;
        this.number = number;
        this.road = road;
        this.district = district;
        this.city = city;
        this.state = state;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getRoad() {
        return road;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", road='" + road + '\'' +
                ", district='" + district + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return id.equals(address.id) && number.equals(address.number) && road.equals(address.road) && district.equals(address.district) && city.equals(address.city) && state.equals(address.state) && user.equals(address.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, road, district, city, state, user);
    }

    public static final class AddressBuilder {
        private Long id;
        private String number;
        private String road;
        private String district;
        private String city;
        private String state;
        private User user;

        private AddressBuilder() {
        }

        public static AddressBuilder builder() {
            return new AddressBuilder();
        }

        public AddressBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AddressBuilder number(String number) {
            this.number = number;
            return this;
        }

        public AddressBuilder road(String road) {
            this.road = road;
            return this;
        }

        public AddressBuilder district(String district) {
            this.district = district;
            return this;
        }

        public AddressBuilder city(String city) {
            this.city = city;
            return this;
        }

        public AddressBuilder state(String state) {
            this.state = state;
            return this;
        }

        public AddressBuilder user(User user) {
            this.user = user;
            return this;
        }

        public Address build() {
            return new Address(id, number, road, district, city, state, user);
        }
    }
}
