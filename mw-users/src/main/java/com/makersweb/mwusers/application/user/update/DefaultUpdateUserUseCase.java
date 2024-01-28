package com.makersweb.mwusers.application.user.update;

import com.makersweb.mwusers.domain.Identifier;
import com.makersweb.mwusers.domain.address.AddressGateway;
import com.makersweb.mwusers.domain.address.AddressID;
import com.makersweb.mwusers.domain.exceptions.DomainException;
import com.makersweb.mwusers.domain.exceptions.NotFoundException;
import com.makersweb.mwusers.domain.user.User;
import com.makersweb.mwusers.domain.user.UserGateway;
import com.makersweb.mwusers.domain.user.UserID;
import com.makersweb.mwusers.domain.validation.Error;
import com.makersweb.mwusers.domain.validation.ValidationHandler;
import com.makersweb.mwusers.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

/**
 * @author aaristides
 */
public class DefaultUpdateUserUseCase extends UpdateUserUseCase {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;

    public DefaultUpdateUserUseCase(UserGateway userGateway, AddressGateway addressGateway) {
        this.userGateway = userGateway;
        this.addressGateway = addressGateway;
    }

    @Override
    public Either<Notification, UpdateUserOutput> execute(final UpdateUserCommand anIn) {
        final var anId = UserID.from(anIn.id());
        final var name = anIn.name();
        final var document = anIn.document();
        final var mail = anIn.mail();
        final var phoneNumber = anIn.phoneNumber();
        final var addresses = toAddressID(anIn.addresses());
        final var birthDate = anIn.birthDate();
        final var gender = anIn.gender();
        final var photo = anIn.photo();
        final var isActive = anIn.isActive();

        final var aUser = this.userGateway.findById(anId)
                .orElseThrow(notFound(anId));

        final var notification = Notification.create();
        notification.append(validateAddress(addresses));
        aUser.update(name, document, mail, phoneNumber, addresses, birthDate, gender, photo, isActive)
                .validate(notification);

        return notification.hasError() ? Left(notification) : update(aUser);
    }

    private Either<Notification, UpdateUserOutput> update(final User aUser) {
        return Try(() -> this.userGateway.update(aUser))
                .toEither()
                .bimap(Notification::create, UpdateUserOutput::from);
    }

    private Supplier<DomainException> notFound(final Identifier anId) {
        return () -> NotFoundException.with(User.class, anId);
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

            notification.append(new Error("Some categories could not be found: %s".formatted(missingIdsMessage)));
        }

        return notification;
    }

    private List<AddressID> toAddressID(final List<String> addresses) {
        return !addresses.isEmpty() ? addresses.stream()
                .map(AddressID::from)
                .toList() : Collections.emptyList();
    }
}
