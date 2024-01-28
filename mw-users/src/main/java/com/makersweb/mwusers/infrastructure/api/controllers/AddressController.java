package com.makersweb.mwusers.infrastructure.api.controllers;

import com.makersweb.mwusers.application.address.create.CreateAddressCommand;
import com.makersweb.mwusers.application.address.create.CreateAddressOutput;
import com.makersweb.mwusers.application.address.create.CreateAddressUseCase;
import com.makersweb.mwusers.domain.validation.handler.Notification;
import com.makersweb.mwusers.infrastructure.address.models.CreateAddressRequest;
import com.makersweb.mwusers.infrastructure.api.AddressAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author aaristides
 */
@RestController
public class AddressController implements AddressAPI {

    private final CreateAddressUseCase createAddressUseCase;

    public AddressController(final CreateAddressUseCase createAddressUseCase) {
        this.createAddressUseCase = Objects.requireNonNull(createAddressUseCase);
    }

    @Override
    public ResponseEntity<?> createAddress(CreateAddressRequest input) {
        final var aCommand = CreateAddressCommand.with(
                input.street(),
                input.streetNumber(),
                input.city(),
                input.state(),
                input.postalCode(),
                input.complement(),
                input.neighborhood(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateAddressOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/addresses/" + output.id())).body(output);

        return this.createAddressUseCase.execute(aCommand).fold(onError, onSuccess);
    }
}
