package br.com.makersweb.mwaddress.application.address.create;

import br.com.makersweb.mwaddress.domain.address.Address;
import br.com.makersweb.mwaddress.domain.address.AddressGateway;
import br.com.makersweb.mwaddress.domain.address.AddressID;
import br.com.makersweb.mwaddress.domain.exceptions.NotFoundException;
import br.com.makersweb.mwaddress.domain.validation.handler.Notification;

import java.util.Objects;

/**
 * @author aaristides
 */
public class DefaultCreateAddressUseCase extends CreateAddressUseCase {

    private final AddressGateway addressGateway;

    public DefaultCreateAddressUseCase(final AddressGateway addressGateway) {
        this.addressGateway = Objects.requireNonNull(addressGateway);
    }

    @Override
    public void execute(final CreateAddressCommand anIn) {
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

        this.addressGateway.create(aAddress);
    }

    private NotFoundException notFound(final AddressID anId) {
        return NotFoundException.with(Address.class, anId);

    }

}
