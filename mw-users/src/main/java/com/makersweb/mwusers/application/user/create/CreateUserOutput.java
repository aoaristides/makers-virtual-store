package com.makersweb.mwusers.application.user.create;

import com.makersweb.mwusers.domain.user.User;

/**
 * @author aaristides
 * @param id
 */
public record CreateUserOutput(
        String id
) {

    public static CreateUserOutput from(final String anId) {
        return new CreateUserOutput(anId);
    }

    public static CreateUserOutput from(final User aUser) {
        return from(aUser.getId().getValue());
    }

}
