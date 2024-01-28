package com.makersweb.mwusers.application.user.update;

import java.time.LocalDate;
import java.util.List;

/**
 * @author aaristides
 * @param id
 * @param name
 * @param document
 * @param mail
 * @param phoneNumber
 * @param addresses
 * @param birthDate
 * @param gender
 * @param photo
 * @param isActive
 */
public record UpdateUserCommand(
        String id,
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

    public static UpdateUserCommand with(
            final String id,
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
        return new UpdateUserCommand(id, name, document, mail, phoneNumber, addresses, birthDate, gender, photo, isActive);
    }
}
