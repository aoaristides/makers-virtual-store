package br.com.makersweb.mwaddress.domain.exceptions;

import br.com.makersweb.mwaddress.domain.AggregateRoot;
import br.com.makersweb.mwaddress.domain.Identifier;
import br.com.makersweb.mwaddress.domain.validation.Error;

import java.util.Collections;
import java.util.List;

/**
 * @author aaristides
 */
public class NotFoundException extends DomainException {

    protected NotFoundException(final String aMessage, final List<Error> anErrors) {
        super(aMessage, anErrors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> anAggregate,
            final Identifier id
    ) {
        final var anError = "%s with ID %s was not found".formatted(
                anAggregate.getSimpleName(),
                id.getValue()
        );
        return new NotFoundException(anError, Collections.emptyList());
    }

}
