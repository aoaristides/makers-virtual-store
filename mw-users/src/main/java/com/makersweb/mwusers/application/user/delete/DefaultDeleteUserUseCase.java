package com.makersweb.mwusers.application.user.delete;

import com.makersweb.mwusers.domain.user.UserGateway;
import com.makersweb.mwusers.domain.user.UserID;

import java.util.Objects;

/**
 * @author aaristides
 */
public class DefaultDeleteUserUseCase extends DeleteUserUseCase {

    private final UserGateway userGateway;

    public DefaultDeleteUserUseCase(final UserGateway userGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
    }

    @Override
    public void execute(final String anIn) {
        this.userGateway.deleteById(UserID.from(anIn));
    }

}
