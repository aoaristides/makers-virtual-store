package com.makersweb.mwusers.infrastructure.address.presenters;

import com.makersweb.mwusers.application.address.retrieve.get.AddressOutput;
import com.makersweb.mwusers.application.address.retrieve.list.AddressListOutput;
import com.makersweb.mwusers.infrastructure.address.models.AddressListResponse;
import com.makersweb.mwusers.infrastructure.address.models.AddressResponse;

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
