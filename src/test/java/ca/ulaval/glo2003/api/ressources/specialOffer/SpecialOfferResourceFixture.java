package ca.ulaval.glo2003.api.ressources.specialOffer;

import ca.ulaval.glo2003.api.dto.common.SpecialOfferDto;
import ca.ulaval.glo2003.api.dto.request.SpecialOfferRequestDto;
import java.time.LocalDate;

public class SpecialOfferResourceFixture {

  public SpecialOfferRequestDto givenValidSpecialOfferRequest() {
    SpecialOfferDto offerDto =
        new SpecialOfferDto(
            "Special Title", "Special Description", LocalDate.now(), LocalDate.now().plusDays(5));

    return new SpecialOfferRequestDto(offerDto);
  }
}
