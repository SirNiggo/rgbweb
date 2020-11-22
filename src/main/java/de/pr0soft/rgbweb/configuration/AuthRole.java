package de.pr0soft.rgbweb.configuration;

public enum AuthRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    AuthRole(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
