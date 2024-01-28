package com.makersweb.mwusers.domain.exceptions;

import com.makersweb.mwusers.domain.validation.handler.Notification;

/**
 * @author aaristides
 */
public class NotificationException extends DomainException {

    public NotificationException(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErrors());
    }

}
