package es.demo.spocktesting.service;

import es.demo.spocktesting.dto.UserDto;
import es.demo.spocktesting.model.User;
import es.demo.spocktesting.repository.ReactiveUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {

    private static final int MIN = 20;
    private static final int MAX = 50;

    private ReactiveUserRepository reactiveUserRepository;

    @Autowired
    public UserService(final ReactiveUserRepository reactiveUserRepository) {
        this.reactiveUserRepository = reactiveUserRepository;
    }

    public Mono<User> createUser(final UserDto userDto) {
        final User user = User
                .builder()
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .age(ThreadLocalRandom.current().nextInt(MIN, MAX + 1))
                .build();
        return reactiveUserRepository.save(user);
    }

    public Mono<Void> deleteAll() {
        return reactiveUserRepository.deleteAll();
    }

    public Flux<User> findAll() {
        return reactiveUserRepository.findAll();
    }

    public Flux<User> findUsersByUsername(final String username) {
        return reactiveUserRepository.findByUsername(username);
    }

    public Flux<User> findUsersByAgeInRange(final int min, final int max) {
        if (min <= max)
            return reactiveUserRepository.findByAgeBetween(min, max);

        throw new IllegalArgumentException("Min should be lowerOrEqual to Max");
        //else
        //    return Flux.error(new IllegalArgumentException("Min should be lowerOrEqual to Max"));
    }

    @Transactional
    public Mono<User> updateFirstUserByUsername(final String username, final UserDto newUserDto) {
        final User user = reactiveUserRepository.findByUsername(username).blockFirst();
        if (user != null) {
            user.setAge(ThreadLocalRandom.current().nextInt(MIN, MAX + 1));
            user.setFirstName(newUserDto.getFirstName());
            user.setLastName(newUserDto.getLastName());
            user.setUsername(newUserDto.getUsername());
            return reactiveUserRepository.save(user);
        }
        return Mono.empty();
    }

    public Mono<Long> count() {
        return reactiveUserRepository.count();
    }

    public Flux<User> findUsersByFirstName(final String firstName) {
        return reactiveUserRepository.findByFirstName(firstName);
    }

    public Mono<Void> deleteUser(final User user) {
        return reactiveUserRepository.delete(user);
    }

}

