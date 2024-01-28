package com.makersweb.mwusers.domain.user;

import com.makersweb.mwusers.domain.pagination.Pagination;
import com.makersweb.mwusers.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

/**
 * @author aaristides
 */
public interface UserGateway {

    User create(User aUser);

    void deleteById(UserID anId);

    Optional<User> findById(UserID anId);

    User update(User aUser);

    Pagination<User> findAll(SearchQuery aQuery);

    List<UserID> existsByIds(Iterable<UserID> ids);

}
