package com.makersweb.mwusers.domain.user;

import com.makersweb.mwusers.domain.AggregateRoot;
import com.makersweb.mwusers.domain.address.AddressID;
import com.makersweb.mwusers.domain.exceptions.NotificationException;
import com.makersweb.mwusers.domain.utils.InstantUtils;
import com.makersweb.mwusers.domain.validation.ValidationHandler;
import com.makersweb.mwusers.domain.validation.handler.Notification;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author aaristides
 */
public class User extends AggregateRoot<UserID> {

    private String name;
    private String document;
    private String mail;
    private String phone;
    private List<AddressID> addresses;
    private LocalDate birthDate;
    private String gender;
    private String photo;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private User(
            final UserID anId,
            final String name,
            final String document,
            final String mail,
            final String phoneNumber,
            final List<AddressID> addresses,
            final LocalDate birthDate,
            final String gender,
            final String photo,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(anId);
        this.name = name;
        this.document = document;
        this.mail = mail;
        this.phone = phoneNumber;
        this.addresses = addresses;
        this.birthDate = birthDate;
        this.gender = gender;
        this.photo = photo;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static User newUser(
            final String name,
            final String document,
            final String mail,
            final String phoneNumber,
            final LocalDate birthDate,
            final String gender,
            final String photo,
            final boolean active
    ) {
        final var anId = UserID.unique();
        final var now = InstantUtils.now();
        final var deletedAt = active ? null : now;
        return new User(anId, name, document, mail, phoneNumber, new ArrayList<>(), birthDate, gender, photo, active, now, now, deletedAt);
    }

    public static User with(
            final UserID anId,
            final String name,
            final String document,
            final String mail,
            final String phoneNumber,
            final List<AddressID> addresses,
            final LocalDate birthDate,
            final String gender,
            final String photo,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new User(anId, name, document, mail, phoneNumber, new ArrayList<>(), birthDate, gender, photo, active, createdAt, updatedAt, deletedAt);
    }

    public static User with(final User aUser) {
        return with(
                aUser.getId(),
                aUser.getName(),
                aUser.getDocument(),
                aUser.getMail(),
                aUser.getPhone(),
                new ArrayList<>(aUser.getAddresses()),
                aUser.getBirthDate(),
                aUser.getGender(),
                aUser.getPhoto(),
                aUser.isActive(),
                aUser.getCreatedAt(),
                aUser.getUpdatedAt(),
                aUser.getDeletedAt()
        );
    }

    public User update(
            final String name,
            final String document,
            final String mail,
            final String phone,
            final List<AddressID> addresses,
            final LocalDate birthDate,
            final String gender,
            final String photo,
            final boolean isActive
    ) {
        if (isActive) {
            activate();
        } else {
            deactivate();
        }

        this.name = name;
        this.document = document;
        this.mail = mail;
        this.phone = phone;
        this.addresses = new ArrayList<>(addresses != null ? addresses : Collections.emptyList());;
        this.birthDate = birthDate;
        this.gender = gender;
        this.photo = photo;
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new UserValidator(this, handler).validate();
    }

    public User activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public User deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = InstantUtils.now();
        }

        this.active = false;
        this.updatedAt = InstantUtils.now();
        return this;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Failed to create a Aggregate User", notification);
        }
    }

    public User addAddress(final AddressID aAddressID) {
        if (aAddressID == null) {
            return this;
        }
        this.addresses.add(aAddressID);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public User addAddresses(final List<AddressID> addresses) {
        if (addresses == null || addresses.isEmpty()) {
            return this;
        }
        this.addresses.addAll(addresses);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public User removeAddress(final AddressID aAddressID) {
        if (aAddressID == null) {
            return this;
        }
        this.addresses.remove(aAddressID);
        this.updatedAt = InstantUtils.now();
        return this;
    }

    public UserID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDocument() {
        return document;
    }

    public String getMail() {
        return mail;
    }

    public String getPhone() {
        return phone;
    }

    public List<AddressID> getAddresses() {
        return addresses;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoto() {
        return photo;
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
