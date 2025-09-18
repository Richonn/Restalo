package ca.ulaval.glo2003.Restaurant;

public class MissingParameterException extends IllegalArgumentException {
    public MissingParameterException(String message) {
        super(message);
    }
}
