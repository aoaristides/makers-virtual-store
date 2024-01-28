package com.makersweb.mwusers.infrastructure.user.persistence;

import com.makersweb.mwusers.domain.address.AddressID;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * @author aaristides
 */
@Entity
@Table(name = "tb_users_addresses")
public class UserAddressJpaEntity {

    @EmbeddedId
    private UserAddressID id;

    @ManyToOne
    @MapsId("userId")
    private UserJpaEntity user;

    public UserAddressJpaEntity() {}

    private UserAddressJpaEntity(final UserJpaEntity aUser, final AddressID aAddressID) {
        this.id = UserAddressID.from(aUser.getId(), aAddressID.getValue());
        this.user = aUser;
    }

    public static UserAddressJpaEntity from(final UserJpaEntity aUser, final AddressID aAddressID) {
        return new UserAddressJpaEntity(aUser, aAddressID);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserAddressJpaEntity that = (UserAddressJpaEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public UserAddressID getId() {
        return id;
    }

    public UserAddressJpaEntity setId(UserAddressID id) {
        this.id = id;
        return this;
    }

    public UserJpaEntity getUser() {
        return user;
    }

    public UserAddressJpaEntity setUser(UserJpaEntity user) {
        this.user = user;
        return this;
    }

}
