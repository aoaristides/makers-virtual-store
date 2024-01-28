package com.makersweb.mwusers.infrastructure.user;

import com.makersweb.mwusers.domain.pagination.Pagination;
import com.makersweb.mwusers.domain.pagination.SearchQuery;
import com.makersweb.mwusers.domain.user.User;
import com.makersweb.mwusers.domain.user.UserGateway;
import com.makersweb.mwusers.domain.user.UserID;
import com.makersweb.mwusers.infrastructure.user.persistence.UserJpaEntity;
import com.makersweb.mwusers.infrastructure.user.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.makersweb.mwusers.infrastructure.utils.SpecificationUtils.like;

/**
 * @author aaristides
 */
@Component
@Slf4j
public class UserPostgreSQLGateway implements UserGateway {

    private final UserRepository repository;

    public UserPostgreSQLGateway(final UserRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public User create(final User aUser) {
        log.info("Init method create by user - {}", aUser.getId());
        return save(aUser);
    }

    @Override
    public void deleteById(final UserID anId) {
        log.info("Init method deleteById - {}", anId.getValue());
        final String anIdValue = anId.getValue();
        if (this.repository.existsById(anIdValue)) {
            this.repository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<User> findById(final UserID anId) {
        log.info("Init method findById - {}", anId.getValue());
        return this.repository.findById(anId.getValue()).map(UserJpaEntity::toAggregate);
    }

    @Override
    public User update(final User aUser) {
        log.info("Init method update by id - {}", aUser.getId().getValue());
        return save(aUser);
    }

    @Override
    public Pagination<User> findAll(final SearchQuery aQuery) {
        log.info("Init method findAll.");
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
                pageResult.map(UserJpaEntity::toAggregate).toList()
        );
    }

    @Override
    public List<UserID> existsByIds(final Iterable<UserID> userIDS) {
        final var ids = StreamSupport.stream(userIDS.spliterator(), false)
                .map(UserID::getValue)
                .toList();
        return this.repository.existsByIds(ids).stream()
                .map(UserID::from)
                .toList();
    }

    private User save(final User aUser) {
        return this.repository.save(UserJpaEntity.from(aUser)).toAggregate();
    }

    private Specification<UserJpaEntity> assembleSpecification(final String str) {
        final Specification<UserJpaEntity> nameLike = like("name", str);
        final Specification<UserJpaEntity> descriptionLike = like("document", str);
        return nameLike.or(descriptionLike);
    }
}
