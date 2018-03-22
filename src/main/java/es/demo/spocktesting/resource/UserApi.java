package es.demo.spocktesting.resource;

import es.demo.spocktesting.dto.UserDto;
import es.demo.spocktesting.model.User;
import es.demo.spocktesting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserApi {

    private final UserService userService;

    @Autowired
    public UserApi(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/count")
    public Mono<Long> count() {
        return userService.count();
    }

    @GetMapping("/findAll")
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Mono<UserDto>> addUser(@RequestBody final UserDto userDto) {
        final Mono<User> user = userService.createUser(userDto);

        if (user != null) {
            return new ResponseEntity<>(user.map(u -> UserDto
                    .builder()
                    .firstName(u.getFirstName())
                    .lastName(u.getLastName())
                    .username(u.getUsername())
                    .build()), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(Mono.empty(), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Mono<Void>> deleteUsers() {
        return new ResponseEntity<>(userService.deleteAll(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/find/byUsername/{username}")
    public Flux<UserDto> findUsersByUsername(@PathVariable final String username) {
        return userService.findUsersByUsername(username).map(u -> UserDto
                .builder()
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .username(u.getUsername())
                .build());
    }

    @GetMapping("/find/byAgeInRange/{min}/{max}")
    public Flux<User> findUsersByAgeInRange(@PathVariable final Integer min, @PathVariable final Integer max) {
        if (min != null && max != null)
            return userService.findUsersByAgeInRange(min, max);
        return Flux.empty();
    }

    @PutMapping("/update/first/byUsername/{username}")
    public ResponseEntity<Mono<User>> updateFirstUserByUsername(@PathVariable final String username, @RequestBody final UserDto newUserDto) {
        if (newUserDto != null) {
            return new ResponseEntity<>(userService.updateFirstUserByUsername(username, newUserDto), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(Mono.empty(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/all/byFirstName/{firstName}")
    public ResponseEntity<Mono<Void>> deleteAllUsersWithFirstName(@PathVariable final String firstName) {
        return new ResponseEntity<>(userService.findUsersByFirstName(firstName).flatMap(userService::deleteUser).then(), HttpStatus.NO_CONTENT);
    }
}
