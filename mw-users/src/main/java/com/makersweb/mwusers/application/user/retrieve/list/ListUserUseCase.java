package com.makersweb.mwusers.application.user.retrieve.list;

import com.makersweb.mwusers.application.UseCase;
import com.makersweb.mwusers.domain.pagination.Pagination;
import com.makersweb.mwusers.domain.pagination.SearchQuery;

/**
 * @author aaristides
 */
public abstract class ListUserUseCase extends UseCase<SearchQuery, Pagination<UserListOutput>> {
}
