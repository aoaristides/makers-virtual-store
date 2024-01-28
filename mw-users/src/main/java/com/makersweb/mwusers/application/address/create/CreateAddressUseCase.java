package com.makersweb.mwusers.application.address.create;

import com.makersweb.mwusers.application.UseCase;
import com.makersweb.mwusers.domain.validation.handler.Notification;
import io.vavr.control.Either;

/**
 * @author aaristides
 */
public abstract class CreateAddressUseCase
        extends UseCase<CreateAddressCommand, Either<Notification, CreateAddressOutput>> {
}
