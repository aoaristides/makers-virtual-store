package com.makersweb.mwusers.infrastructure.user.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.LocalDate;

/**
 * @author aaristides
 * @param id
 * @param name
 * @param document
 * @param mail
 * @param birthDate
 * @param phoneNumber
 * @param gender
 * @param photo
 * @param active
 * @param createdAt
 * @param updatedAt
 * @param deletedAt
 */
public record UserListResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("document") String document,
        @JsonProperty("mail") String mail,
        @JsonProperty("birth_date") LocalDate birthDate,
        @JsonProperty("phone_number") String phoneNumber,
        @JsonProperty("gender") String gender,
        @JsonProperty("photo") String photo,
        @JsonProperty("is_active") Boolean active,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt,
        @JsonProperty("deleted_at") Instant deletedAt
) {
}
