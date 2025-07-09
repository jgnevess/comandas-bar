package org.nevesdev.comanda.model.user;

public enum Role {
    ADMIN("Admin"),
    SELLER("Seller");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
