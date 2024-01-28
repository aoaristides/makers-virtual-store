package com.makersweb.mwusers.infrastructure.usecases;

import com.makersweb.mwusers.application.user.create.CreateUserUseCase;
import com.makersweb.mwusers.application.user.create.DefaultCreateUserUseCase;
import com.makersweb.mwusers.application.user.delete.DefaultDeleteUserUseCase;
import com.makersweb.mwusers.application.user.delete.DeleteUserUseCase;
import com.makersweb.mwusers.application.user.retrieve.get.DefaultGetUserByIdUseCase;
import com.makersweb.mwusers.application.user.retrieve.get.GetUserByIdUseCase;
import com.makersweb.mwusers.application.user.retrieve.list.DefaultListUserUseCase;
import com.makersweb.mwusers.application.user.retrieve.list.ListUserUseCase;
import com.makersweb.mwusers.application.user.update.DefaultUpdateUserUseCase;
import com.makersweb.mwusers.application.user.update.UpdateUserUseCase;
import com.makersweb.mwusers.domain.address.AddressGateway;
import com.makersweb.mwusers.domain.user.UserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author aaristides
 */
@Configuration
public class UserUseCaseConfig {

    private final UserGateway userGateway;
    private final AddressGateway addressGateway;

    public UserUseCaseConfig(final UserGateway userGateway, final AddressGateway addressGateway) {
        this.userGateway = Objects.requireNonNull(userGateway);
        this.addressGateway = Objects.requireNonNull(addressGateway);
    }

    @Bean
    public CreateUserUseCase createUserUseCase() {
        return new DefaultCreateUserUseCase(userGateway, addressGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase() {
        return new DefaultDeleteUserUseCase(userGateway);
    }

    @Bean
    public GetUserByIdUseCase getUserByIdUseCase() {
        return new DefaultGetUserByIdUseCase(userGateway);
    }

    @Bean
    public ListUserUseCase listUserUseCase() {
        return new DefaultListUserUseCase(userGateway);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase() {
        return new DefaultUpdateUserUseCase(userGateway, addressGateway);
    }

}
