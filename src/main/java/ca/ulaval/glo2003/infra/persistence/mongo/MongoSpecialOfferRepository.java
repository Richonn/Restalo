package ca.ulaval.glo2003.infra.persistence.mongo;

import ca.ulaval.glo2003.domain.SpecialOfferRepository;
import ca.ulaval.glo2003.domain.restaurant.SpecialOffer;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.SpecialOfferDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.mappers.SpecialOfferDocumentMapper;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;

@Singleton
public class MongoSpecialOfferRepository implements SpecialOfferRepository {

  private final Datastore datastore;

  @Inject
  public MongoSpecialOfferRepository(MongoConnection mongoConnection) {
    this.datastore = mongoConnection.getDatastore();
    datastore.getMapper().map(SpecialOfferDocument.class);
    datastore.ensureIndexes();
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

  @Override
  public SpecialOffer getSpecialOfferById(String offerId) {
    SpecialOfferDocument document =
        datastore
            .find(SpecialOfferDocument.class)
            .filter(Filters.eq("_id", new ObjectId(offerId)))
            .first();
    return SpecialOfferDocumentMapper.toDomain(document);
  }

  @Override
  public void delete(String offerId) {
    datastore
        .find(SpecialOfferDocument.class)
        .filter(Filters.eq("_id", new ObjectId(offerId)))
        .delete();
  }
}
