package br.com.makersweb.mwaddress.domain.pagination;

/**
 * @author aaristides
 * @param page
 * @param perPage
 * @param terms
 * @param sort
 * @param direction
 */
public record SearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
