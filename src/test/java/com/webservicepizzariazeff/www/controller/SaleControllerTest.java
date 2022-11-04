package com.webservicepizzariazeff.www.controller;

import com.webservicepizzariazeff.www.domain.User;
import com.webservicepizzariazeff.www.dto.request.CardRequestDTO;
import com.webservicepizzariazeff.www.dto.request.FormOfPaymentDTO;
import com.webservicepizzariazeff.www.dto.request.SaleRequestDTO;
import com.webservicepizzariazeff.www.service.SaleService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class SaleControllerTest {

    @InjectMocks
    private SaleController saleController;

    @Mock
    private SaleService saleService;

    private static User user;

    private static SaleRequestDTO saleRequestDTO;

    static void setUser() {

        user = User.UserBuilder.builder()
                .id(1L)
                .name("name")
                .username("username")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    static void setSaleRequestDTO() {

        CardRequestDTO cardRequestDTO = CardRequestDTO.CardRequestDTOBuilder.builder()
                .nameOfCardHolder("nameOfCardHolder")
                .cardNumber("1234567890123456")
                .dueDate("12/34")
                .securityCode("123")
                .formOfPaymentDTO(FormOfPaymentDTO.DEBIT)
                .build();

        saleRequestDTO = SaleRequestDTO.SaleRequestDTOBuilder.builder()
                .productsId(List.of(1L, 2L, 3L))
                .addressId(1L)
                .cardRequestDTO(cardRequestDTO)
                .build();
    }

    @BeforeAll
    static void initializeObjects() {

        setUser();
        setSaleRequestDTO();
    }

    @BeforeEach
    void definitionOfBehaviorsForMocks() {

        BDDMockito.when(this.saleService.sale(
                        ArgumentMatchers.any(UserDetails.class),
                        ArgumentMatchers.any(SaleRequestDTO.class),
                        ArgumentMatchers.anyString()
                ))
                .thenReturn(1L);
    }

    @Test
    void sale_returnsTheIdOfThePurchaseThatWasSavedAndAStatusCodeCreated_whenTheValuesOfSaleRequestDTOAreCorrect() {

        Assertions.assertThatCode(() -> this.saleController.sale(user, saleRequestDTO, "pt-BR"))
                .doesNotThrowAnyException();

        Assertions.assertThat(this.saleController.sale(user, saleRequestDTO, "pt-BR"))
                .isNotNull()
                .isEqualTo(new ResponseEntity<>(1L, HttpStatus.CREATED));
    }

    @Test
    void sale_throwsAExceptionOfTypeRuntimeException_whenSomeValueOfSaleRequestDTOIsIncorrect() {

        BDDMockito.when(this.saleService.sale(
                        ArgumentMatchers.any(UserDetails.class),
                        ArgumentMatchers.any(SaleRequestDTO.class),
                        ArgumentMatchers.anyString()
                ))
                .thenThrow(RuntimeException.class);

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> this.saleController.sale(user, saleRequestDTO, "pt-BR"));
    }
}