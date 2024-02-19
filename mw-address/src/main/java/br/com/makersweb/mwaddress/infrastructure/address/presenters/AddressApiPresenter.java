package br.com.makersweb.mwaddress.infrastructure.address.presenters;

import br.com.makersweb.mwaddress.application.address.retrieve.get.AddressOutput;
import br.com.makersweb.mwaddress.application.address.retrieve.list.AddressListOutput;
import br.com.makersweb.mwaddress.infrastructure.address.models.AddressListResponse;
import br.com.makersweb.mwaddress.infrastructure.address.models.AddressResponse;

/**
 * @author aaristides
 */
public interface AddressApiPresenter {

    static AddressResponse present(final AddressOutput output) {
        return new AddressResponse(
                output.id().getValue(),
                output.street(),
                output.streetNumber(),
                output.city(),
                output.state(),
                output.postalCode(),
                output.complement(),
                output.neighborhood(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static AddressListResponse present(final AddressListOutput output) {
        return new AddressListResponse(
                output.id(),
                output.street(),
                output.streetNumber(),
                output.city(),
                output.state(),
                output.postalCode(),
                output.complement(),
                output.neighborhood(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

}
