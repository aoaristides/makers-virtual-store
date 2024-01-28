package com.makersweb.mwusers.application.user.create;

import com.makersweb.mwusers.domain.address.AddressGateway;
import com.makersweb.mwusers.domain.address.AddressID;
import com.makersweb.mwusers.domain.user.User;
import com.makersweb.mwusers.domain.user.UserGateway;
import com.makersweb.mwusers.domain.validation.Error;
import com.makersweb.mwusers.domain.validation.ValidationHandler;
import com.makersweb.mwusers.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

/**
 * @author aaristides
 */
public class DefaultCreateUserUseCase extends CreateUserUseCase {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;

    public DefaultCreateUserUseCase(final UserGateway userGateway, final AddressGateway addressGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
        this.addressGateway = Objects.requireNonNull(addressGateway);
    }

    @Override
    public Either<Notification, CreateUserOutput> execute(final CreateUserCommand anIn) {
        final var name = anIn.name();
        final var document = anIn.document();
        final var mail = anIn.mail();
        final var phoneNumber = anIn.phoneNumber();
        final var addresses = toAddressID(anIn.addresses());
        final var birthDate = anIn.birthDate();
        final var gender = anIn.gender();
        final var photo = anIn.photo();
        final var isActive = anIn.isActive();

        final var notification = Notification.create();
        notification.append(validateAddress(addresses));

        final var aUser = notification.validate(() -> User.newUser(name, document, mail, phoneNumber, birthDate, gender, photo, isActive));
        aUser.addAddresses(addresses);

        return notification.hasError() ? Left(notification) : create(aUser);
    }

    private Either<Notification, CreateUserOutput> create(final User aUser) {
        return Try(() -> this.userGateway.create(aUser))
                .toEither()
                .bimap(Notification::create, CreateUserOutput::from);
    }

    private ValidationHandler validateAddress(final List<AddressID> ids) {
        final var notification = Notification.create();
        if (ids == null || ids.isEmpty()) {
            return notification;
        }

        final var retrievedIds = addressGateway.existsByIds(ids);

        if (ids.size() != retrievedIds.size()) {
            final var missingIds = new ArrayList<>(ids);
            missingIds.removeAll(retrievedIds);

            final var missingIdsMessage = missingIds.stream()
                    .map(AddressID::getValue)
                    .collect(Collectors.joining(", "));

            notification.append(new Error("Some address could not be found: %s".formatted(missingIdsMessage)));
        }

        return notification;
    }

    private List<AddressID> toAddressID(final List<String> addresses) {
        return !addresses.isEmpty() ? addresses.stream()
                .map(AddressID::from)
                .toList() : Collections.emptyList();
    }
}
