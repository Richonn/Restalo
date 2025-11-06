package ca.ulaval.glo2003.infra.persistence;

public enum PersistenceMode {
  INMEMORY("inmemory"),
  MONGO("mongo");

  private final String value;

  PersistenceMode(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static PersistenceMode fromString(String value) {
    if (value == null) {
      return INMEMORY;
    }
    for (PersistenceMode mode : PersistenceMode.values()) {
      if (mode.value.equalsIgnoreCase(value)) {
        return mode;
      }
    }
    return INMEMORY;
  }
}
