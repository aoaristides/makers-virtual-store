package com.makersweb.mwusers.application.user.retrieve.list;

import com.makersweb.mwusers.domain.pagination.Pagination;
import com.makersweb.mwusers.domain.pagination.SearchQuery;
import com.makersweb.mwusers.domain.user.UserGateway;

import java.util.Objects;

/**
 * @author aaristides
 */
public class DefaultListUserUseCase extends ListUserUseCase {

    private final UserGateway userGateway;

    public DefaultListUserUseCase(final UserGateway userGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
    }

    @Override
    public Pagination<UserListOutput> execute(final SearchQuery aQuery) {
        return this.userGateway.findAll(aQuery).map(UserListOutput::from);
    }

}
