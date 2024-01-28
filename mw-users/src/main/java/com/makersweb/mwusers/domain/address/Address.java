package com.makersweb.mwusers.domain.address;

import com.makersweb.mwusers.domain.AggregateRoot;
import com.makersweb.mwusers.domain.utils.InstantUtils;
import com.makersweb.mwusers.domain.validation.ValidationHandler;

import java.time.Instant;

/**
 * @author aaristides
 */
public class Address extends AggregateRoot<AddressID> {

    private String street;
    private String streetNumber;
    private String city;
    private String state;
    private String postalCode;
    private String complement;
    private String neighborhood;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Address(
            final AddressID anId,
            final String street,
            final String streetNumber,
            final String city,
            final String state,
            final String postalCode,
            final String complement,
            final String neighborhood,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(anId);
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Address newAddress(
            final String street,
            final String streetNumber,
            final String city,
            final String state,
            final String postalCode,
            final String complement,
            final String neighborhood,
            final boolean active
    ) {
        final var anId = AddressID.unique();
        final var now = InstantUtils.now();
        final var deletedAt = active ? null : now;
        return new Address(anId, street, streetNumber, city, state, postalCode, complement, neighborhood, active, now, now, deletedAt);
    }

    public static Address with(
            final AddressID anId,
            final String street,
            final String streetNumber,
            final String city,
            final String state,
            final String postalCode,
            final String complement,
            final String neighborhood,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Address(anId, street, streetNumber, city, state, postalCode, complement, neighborhood, active, createdAt, updatedAt, deletedAt);
    }

    public static Address with(final Address aAddress) {
        return with(
                aAddress.getId(),
                aAddress.getStreet(),
                aAddress.getStreetNumber(),
                aAddress.getCity(),
                aAddress.getState(),
                aAddress.getPostalCode(),
                aAddress.getComplement(),
                aAddress.getNeighborhood(),
                aAddress.isActive(),
                aAddress.getCreatedAt(),
                aAddress.getUpdatedAt(),
                aAddress.getDeletedAt()
        );
    }

    public Address activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public Address deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = InstantUtils.now();
        }

        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new AddressValidator(this, handler).validate();
    }

    public AddressID getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getComplement() {
        return complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
