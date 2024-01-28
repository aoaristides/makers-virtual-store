package com.makersweb.mwusers.infrastructure.user.presenters;

import com.makersweb.mwusers.application.user.retrieve.get.UserOutput;
import com.makersweb.mwusers.application.user.retrieve.list.UserListOutput;
import com.makersweb.mwusers.infrastructure.user.models.UserListResponse;
import com.makersweb.mwusers.infrastructure.user.models.UserResponse;

/**
 * @author aaristides
 */
public interface UserApiPresenter {

    static UserResponse present(final UserOutput output) {
        return new UserResponse(
                output.id().getValue(),
                output.name(),
                output.document(),
                output.mail(),
                output.birthDate(),
                output.phoneNumber(),
                output.gender(),
                output.photo(),
                output.active(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static UserListResponse present(final UserListOutput output) {
        return new UserListResponse(
                output.id(),
                output.name(),
                output.document(),
                output.mail(),
                output.birthDate(),
                output.phoneNumber(),
                output.gender(),
                output.photo(),
                output.active(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

}
