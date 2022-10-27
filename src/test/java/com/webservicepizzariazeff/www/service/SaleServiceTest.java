package com.webservicepizzariazeff.www.service;

import com.webservicepizzariazeff.www.domain.*;
import com.webservicepizzariazeff.www.dto.request.CardRequestDTO;
import com.webservicepizzariazeff.www.dto.request.FormOfPaymentDTO;
import com.webservicepizzariazeff.www.dto.request.SaleRequestDTO;
import com.webservicepizzariazeff.www.exception.InvalidCardException;
import com.webservicepizzariazeff.www.repository.PaymentSimulationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
class SaleServiceTest {

    @InjectMocks
    private SaleService saleService;

    @Mock
    private ProductService productService;

    @Mock
    private AddressService addressService;

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private PurchasedProductService purchasedProductService;

    @Mock
    private PaymentSimulationRepository paymentSimulationRepository;

    private static User userForArgument;

    private static SaleRequestDTO saleRequestDTOForArgument;

    private static List<Product> productsForReturn;

    private static Address addressForReturn;

    private static Purchase purchaseForReturn;

    @BeforeAll
    static void setObjects() {

        userForArgument = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        CardRequestDTO cardRequestDTOForSaleRequestDTO = CardRequestDTO.CardRequestDTOBuilder.builder()
                .nameOfCardHolder("nameOfCardHolder")
                .cardNumber("1234567890123456")
                .dueDate("12/34")
                .securityCode("123")
                .formOfPaymentDTO(FormOfPaymentDTO.CREDIT)
                .build();

        saleRequestDTOForArgument = SaleRequestDTO.SaleRequestDTOBuilder.builder()
                .productsId(List.of(1L, 2L, 3L))
                .addressId(1L)
                .cardRequestDTO(cardRequestDTOForSaleRequestDTO)
                .isPaymentThroughTheWebsite(true)
                .build();

        productsForReturn = List.of(
                Product.ProductBuilder.builder()
                        .id(1L)
                        .name("name1")
                        .description("description1")
                        .price(new BigDecimal("10.0"))
                        .type(Type.SWEET_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image1")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(2L)
                        .name("name2")
                        .description("description2")
                        .price(new BigDecimal("20.0"))
                        .type(Type.SWEET_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image2")
                        .build(),
                Product.ProductBuilder.builder()
                        .id(3L)
                        .name("name3")
                        .description("description3")
                        .price(new BigDecimal("30.0"))
                        .type(Type.SWEET_ESFIHA)
                        .priceRating(PriceRating.PROMOTION)
                        .image("/image3")
                        .build()
        );

        addressForReturn = Address.AddressBuilder.builder()
                .id(1L)
                .number("1")
                .road("road")
                .district("district")
                .city("city")
                .state("state")
                .build();

        purchaseForReturn = Purchase.PurchaseBuilder.builder()
                .id(1L)
                .amount(new BigDecimal("60.0"))
                .cardName("nameOfCardHolder")
                .dateAndTime("12/34/5678T12:34")
                .isPaymentThroughTheWebsite(true)
                .isActive(true)
                .isFinished(true)
                .isDelivered(true)
                .user(userForArgument)
                .address(addressForReturn)
                .build();
    }

    @BeforeEach
    void definitionBehaviorsForMockProductService() {

        BDDMockito.when(this.productService.findAllNonPageable())
                .thenReturn(productsForReturn);
    }

    @BeforeEach
    void definitionBehaviorsForMockAddressService() {

        BDDMockito.when(this.addressService.findById(ArgumentMatchers.any(Long.class)))
                .thenReturn(addressForReturn);
    }

    @BeforeEach
    void definitionBehaviorsForMockPaymentSimulationRepository() {

        BDDMockito.when(this.purchaseService.save(ArgumentMatchers.any(Purchase.class)))
                .thenReturn(purchaseForReturn);
    }

    @BeforeEach
    void definitionBehaviorsForMockPurchasedProductService() {

        BDDMockito.doNothing().when(this.purchasedProductService).save(ArgumentMatchers.any(PurchasedProduct.class));
    }

    @BeforeEach
    void definitionBehaviorsForMockPurchaseService() {

        BDDMockito.doNothing().when(this.paymentSimulationRepository).payment(
                ArgumentMatchers.any(CardRequestDTO.class),
                ArgumentMatchers.any(BigDecimal.class),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        );
    }

    @Test
    void sale_makesTheSaleOfProductsAndReturnsThePurchaseId_whenTheArgumentsAreCorrect() {

        Assertions.assertThat(this.saleService.sale(userForArgument, saleRequestDTOForArgument, "pt-BR"))
                .isEqualTo(1L);
    }

    @Test
    void sale_throwsNullPointerException_whenIfTheProductListHasNoPrice() {

        BDDMockito.when(this.productService.findAllNonPageable())
                .thenReturn(List.of(
                        Product.ProductBuilder.builder().id(1L).name("name1").build(),
                        Product.ProductBuilder.builder().id(2L).name("name2").build(),
                        Product.ProductBuilder.builder().id(3L).name("name3").build()
                ));

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> this.saleService.sale(userForArgument, saleRequestDTOForArgument, "pt-BR"));
    }

    @Test
    void sale_throwsResponseStatusException_whenTheUserNotHaveARegisteredAddress() {

        BDDMockito.when(this.addressService.findById(ArgumentMatchers.any(Long.class)))
                .thenThrow(ResponseStatusException.class);

        Assertions.assertThatExceptionOfType(ResponseStatusException.class)
                .isThrownBy(() -> this.saleService.sale(userForArgument, saleRequestDTOForArgument, "pt-BR"));
    }

    @Test
    void sale_throwsNullPointerException_whenSaleRequestDTOHasANullCardRequestDTO() {

        SaleRequestDTO saleThatThrowsNullPointerException = SaleRequestDTO.SaleRequestDTOBuilder.builder()
                .productsId(List.of(1L, 2L, 3L))
                .addressId(1L)
                .isPaymentThroughTheWebsite(true)
                .build();

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> this.saleService.sale(userForArgument, saleThatThrowsNullPointerException, "pt-BR"));
    }

    @Test
    void sale_throwsInvalidCardException_whenSaleRequestDTOHasACardRequestDTOWithInvalidFields() {

        BDDMockito.doThrow(InvalidCardException.class).when(this.paymentSimulationRepository).payment(
                ArgumentMatchers.any(CardRequestDTO.class),
                ArgumentMatchers.any(BigDecimal.class),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString()
        );

        SaleRequestDTO saleThatThrowsInvalidCardException = SaleRequestDTO.SaleRequestDTOBuilder.builder()
                .productsId(List.of(1L, 2L, 3L))
                .addressId(1L)
                .isPaymentThroughTheWebsite(true)
                .cardRequestDTO(CardRequestDTO.CardRequestDTOBuilder.builder()
                        .nameOfCardHolder("1234")
                        .cardNumber("abcd")
                        .dueDate("12/345")
                        .securityCode("123456")
                        .formOfPaymentDTO(FormOfPaymentDTO.FOOD_VOUCHER)
                        .build())
                .build();

        Assertions.assertThatExceptionOfType(InvalidCardException.class)
                .isThrownBy(() -> this.saleService.sale(userForArgument, saleThatThrowsInvalidCardException, "pt-BR"));
    }
}