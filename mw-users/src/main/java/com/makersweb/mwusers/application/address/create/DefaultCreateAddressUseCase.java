package com.makersweb.mwusers.application.address.create;

import com.makersweb.mwusers.domain.address.Address;
import com.makersweb.mwusers.domain.address.AddressGateway;
import com.makersweb.mwusers.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

/**
 * @author aaristides
 */
public class DefaultCreateAddressUseCase extends CreateAddressUseCase {

    private final AddressGateway addressGateway;

    public DefaultCreateAddressUseCase(final AddressGateway addressGateway) {
        this.addressGateway = Objects.requireNonNull(addressGateway);
    }

    @Override
    public Either<Notification, CreateAddressOutput> execute(final CreateAddressCommand anIn) {
        final var street = anIn.street();
        final var streetNumber = anIn.streetNumber();
        final var city = anIn.city();
        final var state = anIn.state();
        final var postalCode = anIn.postalCode();
        final var complement = anIn.complement();
        final var neighborhood = anIn.neighborhood();
        final var isActive = anIn.isActive();

        final var notification = Notification.create();

        final var aAddress = Address.newAddress(street, streetNumber, city, state, postalCode, complement, neighborhood, isActive);
        aAddress.validate(notification);

        return notification.hasError() ? Left(notification) : create(aAddress);
    }

    private Either<Notification, CreateAddressOutput> create(final Address aAddress) {
        return Try(() -> this.addressGateway.create(aAddress))
                .toEither()
                .bimap(Notification::create, CreateAddressOutput::from);
    }
}
