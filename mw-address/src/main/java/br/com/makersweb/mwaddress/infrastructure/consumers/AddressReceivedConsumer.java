package br.com.makersweb.mwaddress.infrastructure.consumers;

import br.com.makersweb.mwaddress.application.address.create.CreateAddressCommand;
import br.com.makersweb.mwaddress.application.address.create.CreateAddressUseCase;
import br.com.makersweb.mwaddress.infrastructure.address.models.CreateAddressRequest;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author aaristides
 */
@Component
@Slf4j
public class AddressReceivedConsumer {

    private final CreateAddressUseCase createAddressUseCase;

    public AddressReceivedConsumer(final CreateAddressUseCase createAddressUseCase) {
        this.createAddressUseCase = Objects.requireNonNull(createAddressUseCase);
    }

    @SqsListener(value = "${spring.cloud.aws.sqs.endpoint}")
    public void consumer(final Message<CreateAddressRequest> message) {
        final var input = message.getPayload();
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

        this.createAddressUseCase.execute(aCommand);
    }

}
