package ca.ulaval.glo2003.domain;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.exceptions.MissingParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestaurantDomainServiceTest {

  private static RestaurantDomainServiceFixture restaurantDomainServiceFixture;
  private static RestaurantDomainService restaurantDomainService;

  @BeforeEach
  public void setUp() {
    restaurantDomainServiceFixture = new RestaurantDomainServiceFixture();
    restaurantDomainService = new RestaurantDomainService();
  }

  @Test
  void givenValidOwnerId_whenValidateHeaders_thenNoExceptions() {
    String ownerId = restaurantDomainServiceFixture.build();

    assertDoesNotThrow(() -> restaurantDomainService.validateHeaders(ownerId));
  }

  @Test
  void givenNullOwnerId_whenValidateHeaders_thenThrowsMissingParameterException() {
    String ownerId = restaurantDomainServiceFixture.withOwnerID(null).build();

    MissingParameterException exception =
        assertThrows(
            MissingParameterException.class,
            () -> restaurantDomainService.validateHeaders(ownerId));

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("Missing Owner header");
  }

  @Test
  void givenBlankOwnerId_whenValidateHeaders_thenThrowsMissingParameterException() {
    String ownerId = restaurantDomainServiceFixture.withOwnerID("").build();

    MissingParameterException exception =
        assertThrows(
            MissingParameterException.class,
            () -> restaurantDomainService.validateHeaders(ownerId));

    assertThat(exception).isInstanceOf(MissingParameterException.class);
    assertThat(exception.getMessage()).isEqualTo("Missing Owner header");
  }
}
