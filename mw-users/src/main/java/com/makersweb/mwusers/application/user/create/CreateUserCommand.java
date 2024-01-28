package com.makersweb.mwusers.application.user.create;

import java.time.LocalDate;
import java.util.List;

/**
 * @author aaristides
 * @param name
 * @param document
 * @param mail
 * @param phoneNumberm
 * @param addresses
 * @param birthDate
 * @param gender
 * @param photo
 * @param isActive
 */
public record CreateUserCommand(
        String name,
        String document,
        String mail,
        String phoneNumber,
        List<String> addresses,
        LocalDate birthDate,
        String gender,
        String photo,
        boolean isActive
) {

    public static CreateUserCommand with(
            final String name,
            final String document,
            final String mail,
            final String phoneNumber,
            final List<String> addresses,
            final LocalDate birthDate,
            final String gender,
            final String photo,
            final boolean isActive
    ) {
        return new CreateUserCommand(name, document, mail, phoneNumber, addresses, birthDate, gender, photo, isActive);
    }

}
