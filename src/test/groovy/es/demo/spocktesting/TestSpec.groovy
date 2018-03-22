package es.demo.spocktesting

import spock.lang.Specification


class TestSpec extends Specification {

    def "test Spock's existence"() {
        given: "value of a initialized to 1"
        def a = 1

        when: "add 2 to a"
        def s = a + 2

        then: "the expected result of add is 3"
        s == 3
    }

}