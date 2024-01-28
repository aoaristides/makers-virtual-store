package com.makersweb.mwusers.infrastructure.user.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author aaristides
 */
@Embeddable
public class UserAddressID implements Serializable {

    @Column(name = "address_id", nullable = false)
    private String addressId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    public UserAddressID() {}

    public UserAddressID(final String userId, final String addressId) {
        this.addressId = addressId;
        this.userId = userId;
    }

    public static UserAddressID from(final String aUserId, final String aAddressId) {
        return new UserAddressID(aUserId, aAddressId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserAddressID that = (UserAddressID) o;
        return Objects.equals(getAddressId(), that.getAddressId()) && Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddressId(), getUserId());
    }

    public String getAddressId() {
        return addressId;
    }

    public UserAddressID setAddressId(String addressId) {
        this.addressId = addressId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public UserAddressID setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
