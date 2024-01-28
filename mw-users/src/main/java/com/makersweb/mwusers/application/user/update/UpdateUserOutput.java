package com.makersweb.mwusers.application.user.update;

import com.makersweb.mwusers.domain.user.User;

/**
 * @author aaristides
 * @param id
 */
public record UpdateUserOutput(
        String id
) {

    public static UpdateUserOutput from(final String anId) {
        return new UpdateUserOutput(anId);
    }

    public static UpdateUserOutput from(final User aUser) {
        return from(aUser.getId().getValue());
    }

}
