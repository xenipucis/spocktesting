package es.demo.spocktesting.others

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

class User {
    Long id
    String firstName
    String lastName
    String username
    Integer age
}

@Narrative("""Testing Groovy capabilities:
 * Classes in Groovy
 * String interpolation
 * Collections in Groovy""")

@Title("Testing Groovy capabilities")
class ClassesTestSpec extends Specification {

    def "test Class related capabilities in Groovy"() {
        given: "a new User built using map-based constructor"
        User user = new User(firstName: "firstName"
                            , lastName: "lastName"
                            , username: "username"
                            , age: 100)

        expect: "id property is not set"
        !user.id

        when: "setter is called indirectly for id property"
        user.id = 100

        then: "id property is now set"
        user.getId() == 100

        expect: "getFirstName call is same as firstName call"
        user.getFirstName() == user.firstName
    }

    def "test String interpolation"() {
        given: "a new User built using map-based constructor"
        User user = new User(firstName: "firstName"
                , lastName: "lastName"
                , username: "username"
                , age: 100)

        expect: "build a String using String-interpolation and test it"
        "User firstName: ${user.firstName}, lastName: ${user.lastName}, username: ${user.username}, age: ${user.age}." == "User firstName: firstName, lastName: lastName, username: username, age: 100."
    }

    def "test Arrays, Lists in Groovy"() {
        given: "a List, an Array initialized with the same elements"
        List<String> listOfStrings = ["first", "second", "third"]
        String[] arrayOfStrings = ["first", "second", "third"]

        expect: "the List should be equal with Groovy (the same like equals() in Java, but not identity equality"
        listOfStrings == arrayOfStrings
        !listOfStrings.is(arrayOfStrings)

        when:
        listOfStrings[3] = "fourth"

        then:
        listOfStrings == ["first", "second", "third", "fourth"]

        expect:
        [].size() == 0
    }

    def "test Maps in Groovy"() {
        given: "A Map"
        Map<String, Integer> aMap = ["first": 1, "second": 2, "third": 3, "fourth": 4]

        expect:
        aMap.keySet()[0] == "first"

        and:
        aMap.values()[0] == 1

        when:
        aMap["fifth"] = 5

        then:
        aMap.keySet()[4] == "fifth"

        expect:
        [:].keySet().size() == 0
    }
}