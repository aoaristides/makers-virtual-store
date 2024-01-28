package com.makersweb.mwusers.infrastructure.user.persistence;

import com.makersweb.mwusers.domain.address.AddressID;
import com.makersweb.mwusers.domain.user.User;
import com.makersweb.mwusers.domain.user.UserID;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author aaristides
 */
@Entity(name = "User")
@Table(name = "tb_users")
public class UserJpaEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "document", nullable = false, length = 15, unique = true)
    private String document;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "phone", nullable = false, length = 30)
    private String phone;

    @Column(name = "birthday", nullable = false, columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(name = "gender", nullable = false, length = 50)
    private String gender;

    @Column(name = "photo", length = 255)
    private String photo;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<UserAddressJpaEntity> addresses;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Instant deletedAt;

    public UserJpaEntity() {
    }

    private UserJpaEntity(
            final String anId,
            final String name,
            final String document,
            final String mail,
            final String phoneNumber,
            final LocalDate birthDate,
            final String gender,
            final String photo,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        this.id = anId;
        this.name = name;
        this.document = document;
        this.mail = mail;
        this.birthDate = birthDate;
        this.phone = phoneNumber;
        this.gender = gender;
        this.photo = photo;
        this.active = active;
        this.addresses = new HashSet<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static UserJpaEntity from(final User aUser) {
        final var anEntity = new UserJpaEntity(
                aUser.getId().getValue(),
                aUser.getName(),
                aUser.getDocument(),
                aUser.getMail(),
                aUser.getPhone(),
                aUser.getBirthDate(),
                aUser.getGender(),
                aUser.getPhoto(),
                aUser.isActive(),
                aUser.getCreatedAt(),
                aUser.getUpdatedAt(),
                aUser.getDeletedAt()
        );

        aUser.getAddresses().forEach(anEntity::addAddress);

        return anEntity;
    }

    public User toAggregate() {
        return User.with(
                UserID.from(getId()),
                getName(),
                getDocument(),
                getMail(),
                getPhone(),
                getAddressIDs(),
                getBirthDate(),
                getGender(),
                getPhoto(),
                isActive(),
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt()
        );
    }

    private void addAddress(final AddressID anId) {
        this.addresses.add(UserAddressJpaEntity.from(this, anId));
    }

    private void removeAddress(final AddressID anId) {
        this.addresses.remove(UserAddressJpaEntity.from(this, anId));
    }

    public List<AddressID> getAddressIDs() {
        return getAddresses().stream()
                .map(it -> AddressID.from(it.getId().getAddressId()))
                .toList();
    }

    public String getId() {
        return id;
    }

    public UserJpaEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserJpaEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDocument() {
        return document;
    }

    public UserJpaEntity setDocument(String document) {
        this.document = document;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public UserJpaEntity setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserJpaEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public UserJpaEntity setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserJpaEntity setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public UserJpaEntity setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public UserJpaEntity setActive(boolean active) {
        this.active = active;
        return this;
    }

    public Set<UserAddressJpaEntity> getAddresses() {
        return addresses;
    }

    public UserJpaEntity setAddresses(Set<UserAddressJpaEntity> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UserJpaEntity setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public UserJpaEntity setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public UserJpaEntity setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }
}
