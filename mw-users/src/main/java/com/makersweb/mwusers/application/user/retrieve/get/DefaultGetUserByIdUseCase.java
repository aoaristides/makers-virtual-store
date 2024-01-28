package com.makersweb.mwusers.application.user.retrieve.get;

import com.makersweb.mwusers.domain.exceptions.NotFoundException;
import com.makersweb.mwusers.domain.user.User;
import com.makersweb.mwusers.domain.user.UserGateway;
import com.makersweb.mwusers.domain.user.UserID;

import java.util.Objects;

/**
 * @author aaristides
 */
public class DefaultGetUserByIdUseCase extends GetUserByIdUseCase {

    private final UserGateway userGateway;

    public DefaultGetUserByIdUseCase(final UserGateway userGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
    }

    @Override
    public UserOutput execute(final String anIn) {
        final var aUserId = UserID.from(anIn);
        return this.userGateway.findById(aUserId)
                .map(UserOutput::from)
                .orElseThrow(() -> NotFoundException.with(User.class, aUserId));
    }

}
