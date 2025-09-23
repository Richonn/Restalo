package ca.ulaval.glo2003.api.health;

/**
 * Reponse of the health route.
 *
 * @param status Status of the request
 */
public record HealthResponse(String status) {}
