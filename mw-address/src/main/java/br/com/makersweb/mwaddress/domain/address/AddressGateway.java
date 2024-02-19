package br.com.makersweb.mwaddress.domain.address;

import br.com.makersweb.mwaddress.domain.pagination.Pagination;
import br.com.makersweb.mwaddress.domain.pagination.SearchQuery;

import java.util.List;
import java.util.Optional;

/**
 * @author aaristides
 */
public interface AddressGateway {

    Address create(Address aAddress);

    void deleteById(AddressID anId);

    Optional<Address> findById(AddressID anId);

    Address update(Address aAddress);

    Pagination<Address> findAll(SearchQuery aQuery);

    List<AddressID> existsByIds(Iterable<AddressID> ids);

}
