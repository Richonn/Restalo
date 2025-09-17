package ca.ulaval.glo2003.Restaurant;

public class RestaurantResponse {
    public String id;
    public String name;

    public RestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
    }
}
