package es.demo.spocktesting.service

import es.demo.spocktesting.dto.UserDto
import es.demo.spocktesting.model.User
import es.demo.spocktesting.repository.ReactiveUserRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title


@Title("Tests for UserService")

@Subject(UserService)
class UserServiceTestSpec extends Specification {

    ReactiveUserRepository reactiveUserRepository

    UserService userService

    def setup() {
        reactiveUserRepository = Mock(ReactiveUserRepository)
        userService = new UserService(reactiveUserRepository)
    }

    def "test count"() {
        given: "A Mocked Mono, mockedResultInMono, initialized to a Long value, mockedResult."
        def mockedResult = 1L
        def mockedResultInMono = Mock(Mono)
        mockedResultInMono.block() >> mockedResult

        when: "UserService's count is called"
        Mono<Long> result = userService.count()

        then: "1 invocation of ReactiveUserRepository's count"
        1 * reactiveUserRepository.count() >> mockedResultInMono

        and: "result has its value equal to expected"
        result.block() == mockedResult
    }

    def "test deleteUser"() {
        given: "A Mocked user"
        def user = Mock(User)

        when: "UserService's deleteUser is called"
        userService.deleteUser(user)

        then: "1 invocation of ReactiveUserRepository's delete"
        1 * reactiveUserRepository.delete(user)
    }

    def "test findUserByUsername"() {
        given: "username initialized"
        def username = "username"

        when: "UserService's findUsersByUsername is called"
        userService.findUsersByUsername(username)

        then: "1 invocation of ReactiveUserRepository's findByUsername"
        1 * reactiveUserRepository.findByUsername(username)
    }

    def "test createUser"() {
        given: "A "
        def userDto = Mock(UserDto)
        def fn = "fn"
        def ln = "ln"
        def un = "un"

        when: ""
        userService.createUser(userDto)

        then: ""
        1 * userDto.firstName >> fn
        1 * userDto.lastName >> ln
        1 * userDto.username >> un

        and:
        1 * reactiveUserRepository.save(_)

    }

    def "test deleteAll"() {
        when:
        userService.deleteAll()

        then:
        1 * reactiveUserRepository.deleteAll()
    }

    def "test findAll"() {
        when:
        userService.findAll()

        then:
        1 * reactiveUserRepository.findAll()
    }

    def "test findUsersByAgeInRange"() {
        given:
        def min = 10
        def max = 15

        when:
        Flux<User> fluxOfUsers = userService.findUsersByAgeInRange(min, max)

        then:
        1 * reactiveUserRepository.findByAgeBetween(min, max)

        when:
        fluxOfUsers = userService.findUsersByAgeInRange(max, min)

        then:
        0 * reactiveUserRepository.findByAgeBetween(max, min)

        and:
        def e = thrown IllegalArgumentException

        and:
        e.message == "Min should be lowerOrEqual to Max"
    }

    def "test findUsersByFirstName"() {
        given:
        def firstName = "fn"

        when:
        userService.findUsersByFirstName(firstName)

        then:
        1 * reactiveUserRepository.findByFirstName(firstName)
    }

    def "test updateFirstUserByUsername"() {
        given:
        def username = "un"
        def newUserDto = Mock(UserDto)
        def user = Mock(User)

        Flux<User> fluxMockWithUser = Flux.just(user)
        Flux<User> emptyFlux = Flux.empty()

        Mono<User> mockedUserMono = Mock(Mono)

        when:
        Mono<User> userMono = userService.updateFirstUserByUsername(username, newUserDto)

        then:
        1 * reactiveUserRepository.findByUsername(username) >> fluxMockWithUser
        1 * user.setAge(_)
        1 * user.setFirstName(_)
        1 * user.setLastName(_)
        1 * reactiveUserRepository.save(user) >> mockedUserMono

        and:
        userMono == mockedUserMono

        when:
        userMono = userService.updateFirstUserByUsername(username, newUserDto)

        then:
        1 * reactiveUserRepository.findByUsername(username) >> emptyFlux
        0 * user.setAge(_)
        0 * user.setFirstName(_)
        0 * user.setLastName(_)
        0 * reactiveUserRepository.save(user)

        and:
        userMono == Mono.empty()
    }
}