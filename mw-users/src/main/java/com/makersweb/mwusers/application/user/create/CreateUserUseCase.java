package com.makersweb.mwusers.application.user.create;

import com.makersweb.mwusers.application.UseCase;
import com.makersweb.mwusers.domain.validation.handler.Notification;
import io.vavr.control.Either;

/**
 * @author aaristides
 */
public abstract class CreateUserUseCase
        extends UseCase<CreateUserCommand, Either<Notification, CreateUserOutput>> {
}
