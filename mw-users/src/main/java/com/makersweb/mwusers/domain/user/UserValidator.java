package com.makersweb.mwusers.domain.user;

import com.makersweb.mwusers.domain.validation.Error;
import com.makersweb.mwusers.domain.validation.ValidationHandler;
import com.makersweb.mwusers.domain.validation.Validator;

/**
 * @author aaristides
 */
public class UserValidator extends Validator {

    private final User user;
    private static final int NAME_MAX_LENGTH = 255;
    private static final int NAME_MIN_LENGTH = 3;

    public UserValidator(final User user, final ValidationHandler aHandler) {
        super(aHandler);
        this.user = user;
    }

    @Override
    public void validate() {
        this.checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.user.getName();
        if (name == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final var length = name.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
