package ca.ulaval.glo2003.Restaurant;

import java.time.LocalTime;

public class RestaurantRequest {
    public String name;
    public int capacity;
    public String openingHour;
    public String closingHour;

    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity cannot be less than 1");
        }
        LocalTime opening = LocalTime.parse(this.openingHour);
        LocalTime closing = LocalTime.parse(this.closingHour);
        if (opening.isBefore(opening.plusHours(1))) {
            throw new IllegalArgumentException("Opening hour cannot be before closing hour");
        }
    }
}
