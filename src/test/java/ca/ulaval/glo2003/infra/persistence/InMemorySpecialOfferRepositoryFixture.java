package ca.ulaval.glo2003.infra.persistence;

import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import java.time.LocalDate;

public class InMemorySpecialOfferRepositoryFixture {

  private final String restaurantId;
  private final String title;
  private final String description;
  private final LocalDate validFrom;
  private final LocalDate validUntil;

  public InMemorySpecialOfferRepositoryFixture() {
    this(
        "restaurant123",
        "Winter Special",
        "Special winter menu",
        LocalDate.of(2024, 12, 1),
        LocalDate.of(2024, 12, 31));
  }

  private InMemorySpecialOfferRepositoryFixture(
      String restaurantId,
      String title,
      String description,
      LocalDate validFrom,
      LocalDate validUntil) {

    this.restaurantId = restaurantId;
    this.title = title;
    this.description = description;
    this.validFrom = validFrom;
    this.validUntil = validUntil;
  }

  public InMemorySpecialOfferRepositoryFixture withRestaurantId(String restaurantId) {
    return new InMemorySpecialOfferRepositoryFixture(
        restaurantId, this.title, this.description, this.validFrom, this.validUntil);
  }

  public InMemorySpecialOfferRepositoryFixture withTitle(String title) {
    return new InMemorySpecialOfferRepositoryFixture(
        this.restaurantId, title, this.description, this.validFrom, this.validUntil);
  }

  public InMemorySpecialOfferRepositoryFixture withDescription(String description) {
    return new InMemorySpecialOfferRepositoryFixture(
        this.restaurantId, this.title, description, this.validFrom, this.validUntil);
  }

  public InMemorySpecialOfferRepositoryFixture withValidFrom(LocalDate validFrom) {
    return new InMemorySpecialOfferRepositoryFixture(
        this.restaurantId, this.title, this.description, validFrom, this.validUntil);
  }

  public InMemorySpecialOfferRepositoryFixture withValidUntil(LocalDate validUntil) {
    return new InMemorySpecialOfferRepositoryFixture(
        this.restaurantId, this.title, this.description, this.validFrom, validUntil);
  }

  public SpecialOffer build() {
    return new SpecialOffer(restaurantId, title, description, validFrom, validUntil);
  }
}
