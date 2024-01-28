package com.makersweb.mwusers.infrastructure.api.controllers;

import com.makersweb.mwusers.application.user.create.CreateUserCommand;
import com.makersweb.mwusers.application.user.create.CreateUserOutput;
import com.makersweb.mwusers.application.user.create.CreateUserUseCase;
import com.makersweb.mwusers.application.user.delete.DeleteUserUseCase;
import com.makersweb.mwusers.application.user.retrieve.get.GetUserByIdUseCase;
import com.makersweb.mwusers.application.user.retrieve.list.ListUserUseCase;
import com.makersweb.mwusers.application.user.update.UpdateUserCommand;
import com.makersweb.mwusers.application.user.update.UpdateUserOutput;
import com.makersweb.mwusers.application.user.update.UpdateUserUseCase;
import com.makersweb.mwusers.domain.pagination.Pagination;
import com.makersweb.mwusers.domain.pagination.SearchQuery;
import com.makersweb.mwusers.domain.validation.handler.Notification;
import com.makersweb.mwusers.infrastructure.api.UserAPI;
import com.makersweb.mwusers.infrastructure.user.models.CreateUserRequest;
import com.makersweb.mwusers.infrastructure.user.models.UpdateUserRequest;
import com.makersweb.mwusers.infrastructure.user.models.UserListResponse;
import com.makersweb.mwusers.infrastructure.user.models.UserResponse;
import com.makersweb.mwusers.infrastructure.user.presenters.UserApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author aaristides
 */
@RestController
public class UserController implements UserAPI {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final ListUserUseCase listUserUseCase;

    public UserController(
            final CreateUserUseCase createUserUseCase,
            final GetUserByIdUseCase getUserByIdUseCase,
            final UpdateUserUseCase updateUserUseCase,
            final DeleteUserUseCase deleteUserUseCase,
            final ListUserUseCase listUserUseCase
    ) {
        this.createUserUseCase = Objects.requireNonNull(createUserUseCase);
        this.getUserByIdUseCase = Objects.requireNonNull(getUserByIdUseCase);
        this.updateUserUseCase = Objects.requireNonNull(updateUserUseCase);
        this.deleteUserUseCase = Objects.requireNonNull(deleteUserUseCase);
        this.listUserUseCase = Objects.requireNonNull(listUserUseCase);
    }

    @Override
    public ResponseEntity<?> createUser(final CreateUserRequest input) {
        final var aCommand = CreateUserCommand.with(
                input.name(),
                input.document(),
                input.mail(),
                input.phoneNumber(),
                input.addresses(),
                input.birthDate(),
                input.gender(),
                input.photo(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateUserOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/users/" + output.id())).body(output);

        return this.createUserUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public Pagination<UserListResponse> listUsers(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return this.listUserUseCase.execute(new SearchQuery(page, perPage, search, sort, direction)).map(UserApiPresenter::present);
    }

    @Override
    public UserResponse getById(final String id) {
        return UserApiPresenter.present(this.getUserByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(final String id, final UpdateUserRequest input) {
        final var aCommand = UpdateUserCommand.with(
                id,
                input.name(),
                input.document(),
                input.mail(),
                input.phoneNumber(),
                input.addresses(),
                input.birthDate(),
                input.gender(),
                input.photo(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification -> ResponseEntity.unprocessableEntity().body(notification);

        final Function<UpdateUserOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return this.updateUserUseCase.execute(aCommand).fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String anId) {
        this.deleteUserUseCase.execute(anId);
    }
}
