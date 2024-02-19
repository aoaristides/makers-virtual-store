package br.com.makersweb.mwaddress.domain.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author aaristides
 */
public class InstantUtils {

    private InstantUtils() {
    }

    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }

}
