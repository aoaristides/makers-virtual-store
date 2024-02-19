package br.com.makersweb.mwaddress.infrastructure.address;

import br.com.makersweb.mwaddress.domain.address.Address;
import br.com.makersweb.mwaddress.domain.address.AddressGateway;
import br.com.makersweb.mwaddress.domain.address.AddressID;
import br.com.makersweb.mwaddress.domain.pagination.Pagination;
import br.com.makersweb.mwaddress.domain.pagination.SearchQuery;
import br.com.makersweb.mwaddress.infrastructure.address.persistence.AddressJpaEntity;
import br.com.makersweb.mwaddress.infrastructure.address.persistence.AddressRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static br.com.makersweb.mwaddress.infrastructure.utils.SpecificationUtils.like;

/**
 * @author aaristides
 */
@Component
public class AddressPostgreSQLGateway implements AddressGateway {

    private final AddressRepository repository;

    public AddressPostgreSQLGateway(final AddressRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Address create(final Address aAddress) {
        return save(aAddress);
    }

    @Override
    public void deleteById(final AddressID anId) {
        final String anIdValue = anId.getValue();
        if (this.repository.existsById(anIdValue)) {
            this.repository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<Address> findById(final AddressID anId) {
        return this.repository.findById(anId.getValue()).map(AddressJpaEntity::toAggregate);
    }

    @Override
    public Address update(final Address aAddress) {
        return save(aAddress);
    }

    @Override
    public Pagination<Address> findAll(final SearchQuery aQuery) {
        // Paginação
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        // Busca dinamica pelo criterio terms (name ou description)
        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult = this.repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(AddressJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public List<AddressID> existsByIds(final Iterable<AddressID> addressIDS) {
        final var ids = StreamSupport.stream(addressIDS.spliterator(), false)
                .map(AddressID::getValue)
                .toList();
        return this.repository.existsByIds(ids).stream()
                .map(AddressID::from)
                .toList();
    }

    private Address save(final Address aAddress) {
        return this.repository.save(AddressJpaEntity.from(aAddress)).toAggregate();
    }

    private Specification<AddressJpaEntity> assembleSpecification(final String str) {
        final Specification<AddressJpaEntity> nameLike = like("street", str);
        final Specification<AddressJpaEntity> descriptionLike = like("postalCode", str);
        return nameLike.or(descriptionLike);
    }
}
