package br.com.makersweb.mwaddress.domain.address;

import br.com.makersweb.mwaddress.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

/**
 * @author aaristides
 */
public class AddressID extends Identifier {

    private final String value;

    private AddressID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static AddressID unique() {
        return AddressID.from(UUID.randomUUID());
    }

    public static AddressID from(final String anId) {
        return new AddressID(anId);
    }

    public static AddressID from(final UUID anId) {
        return new AddressID(anId.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AddressID addressID = (AddressID) object;
        return Objects.equals(getValue(), addressID.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
