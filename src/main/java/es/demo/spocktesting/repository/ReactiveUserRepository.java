package es.demo.spocktesting.repository;

import es.demo.spocktesting.model.User;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@EnableReactiveMongoRepositories(basePackages = "es.demo.spocktesting.repository")
public interface ReactiveUserRepository extends ReactiveCrudRepository<User, String> {

    Flux<User> findByUsername(final String username);

    Flux<User> findByFirstName(final String firstName);

    Flux<User> findByAgeBetween(final int min, final int max);
}
