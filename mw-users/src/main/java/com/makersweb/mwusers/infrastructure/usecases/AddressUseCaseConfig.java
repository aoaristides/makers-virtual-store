package com.makersweb.mwusers.infrastructure.usecases;

import com.makersweb.mwusers.application.address.create.CreateAddressUseCase;
import com.makersweb.mwusers.application.address.create.DefaultCreateAddressUseCase;
import com.makersweb.mwusers.domain.address.AddressGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author aaristides
 */
@Configuration
public class AddressUseCaseConfig {

    private final AddressGateway addressGateway;

    public AddressUseCaseConfig(final AddressGateway addressGateway) {
        this.addressGateway = Objects.requireNonNull(addressGateway);
    }

    @Bean
    public CreateAddressUseCase createAddressUseCase() {
        return new DefaultCreateAddressUseCase(addressGateway);
    }

}
