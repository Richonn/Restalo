package ca.ulaval.glo2003.infra.persistence.mongo;

import ca.ulaval.glo2003.domain.SpecialOfferRepository;
import ca.ulaval.glo2003.domain.specialOffer.SpecialOffer;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.SpecialOfferDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.mappers.SpecialOfferDocumentMapper;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MongoSpecialOfferRepository implements SpecialOfferRepository {

  private final Datastore datastore;

  @Inject
  public MongoSpecialOfferRepository(MongoConnection mongoConnection) {
    this.datastore = mongoConnection.getDatastore();
  }

  @Override
  public void save(SpecialOffer specialOffer) {
    SpecialOfferDocument document = SpecialOfferDocumentMapper.toDocument(specialOffer);
    datastore.save(document);
  }

  @Override
  public List<SpecialOffer> getSpecialOffersByRestaurantId(String restaurantId) {
    return datastore
        .find(SpecialOfferDocument.class)
        .filter(Filters.eq("restaurantId", restaurantId))
        .stream()
        .map(SpecialOfferDocumentMapper::toDomain)
        .collect(Collectors.toList());
  }
}
