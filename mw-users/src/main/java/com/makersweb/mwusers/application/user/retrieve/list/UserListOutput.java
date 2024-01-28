package com.makersweb.mwusers.application.user.retrieve.list;

import com.makersweb.mwusers.domain.address.AddressID;
import com.makersweb.mwusers.domain.user.User;

import java.time.Instant;
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
 * @param active
 * @param createdAt
 * @param updatedAt
 * @param deletedAt
 */
public record UserListOutput(
        String id,
        String name,
        String document,
        String mail,
        String phoneNumber,
        List<String> addresses,
        LocalDate birthDate,
        String gender,
        String photo,
        boolean active,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {

    public static UserListOutput from(final User aUser) {
        return new UserListOutput(
                aUser.getId().getValue(),
                aUser.getName(),
                aUser.getDocument(),
                aUser.getMail(),
                aUser.getPhone(),
                aUser.getAddresses().stream()
                        .map(AddressID::getValue)
                        .toList(),
                aUser.getBirthDate(),
                aUser.getGender(),
                aUser.getPhoto(),
                aUser.isActive(),
                aUser.getCreatedAt(),
                aUser.getUpdatedAt(),
                aUser.getDeletedAt()
        );
    }

}
