package ca.ulaval.glo2003.infra.persistence.mongo;

import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import ca.ulaval.glo2003.infra.persistence.mongo.documents.RestaurantDocument;
import ca.ulaval.glo2003.infra.persistence.mongo.mappers.RestaurantDocumentMapper;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class MongoRestaurantRepository implements RestaurantRepository {
  private final Datastore datastore;

  @Inject
  public MongoRestaurantRepository(MongoConnection mongoConnection) {
    this.datastore = mongoConnection.getDatastore();
    datastore.getMapper().map(RestaurantDocument.class);
    datastore.ensureIndexes();
  }

  @Override
  public void save(Restaurant restaurant) {
    RestaurantDocument document = RestaurantDocumentMapper.toDocument(restaurant);
    datastore.save(document);
  }

  @Override
  public List<Restaurant> getRestaurantListOfOwner(String ownerId) {
    return datastore.find(RestaurantDocument.class).filter(Filters.eq("ownerId", ownerId)).stream()
        .map(RestaurantDocumentMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Restaurant getRestaurantByIdOfOwner(String ownerId, String restaurantId) {
    RestaurantDocument document =
        datastore
            .find(RestaurantDocument.class)
            .filter(Filters.and(Filters.eq("_id", restaurantId), Filters.eq("ownerId", ownerId)))
            .first();

    return RestaurantDocumentMapper.toDomain(document);
  }

  @Override
  public List<Restaurant> getRestaurantList() {
    return datastore.find(RestaurantDocument.class).stream()
        .map(RestaurantDocumentMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Restaurant getRestaurantById(String restaurantId) {
    RestaurantDocument document =
        datastore.find(RestaurantDocument.class).filter(Filters.eq("_id", restaurantId)).first();

    return RestaurantDocumentMapper.toDomain(document);
  }

  @Override
  public void delete(String restaurantId) {
    datastore.find(RestaurantDocument.class).filter(Filters.eq("_id", restaurantId)).delete();
  }
}
