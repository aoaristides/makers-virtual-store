package com.makersweb.mwusers.application.user.update;

import com.makersweb.mwusers.application.UseCase;
import com.makersweb.mwusers.domain.validation.handler.Notification;
import io.vavr.control.Either;

/**
 * @author aaristides
 */
public abstract class UpdateUserUseCase
        extends UseCase<UpdateUserCommand, Either<Notification, UpdateUserOutput>> {
}
