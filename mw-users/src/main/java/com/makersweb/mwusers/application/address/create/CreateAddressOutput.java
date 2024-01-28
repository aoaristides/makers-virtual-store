package com.makersweb.mwusers.application.address.create;

import com.makersweb.mwusers.domain.address.Address;

/**
 * @author aaristides
 * @param id
 */
public record CreateAddressOutput(
        String id
) {

    public static CreateAddressOutput from(final String anId) {
        return new CreateAddressOutput(anId);
    }

    public static CreateAddressOutput from(final Address aAddress) {
        return from(aAddress.getId().getValue());
    }

}
