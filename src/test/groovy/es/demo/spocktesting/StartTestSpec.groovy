package es.demo.spocktesting

import spock.lang.Ignore
import spock.lang.IgnoreIf
import spock.lang.Specification

class StartTestSpec extends Specification {

    def "test adding two ints"() {
        given: "a, b intialized with int values"
        def a = 100
        def b = 300
        def adder = new Adder()
        def expected = 400

        when: "a + b is called"
        def s = adder.add(a, b)

        then: "100 plus 300 is 400"
        s == expected
    }

    class Adder {
        int add(int a, int b) {
            return a + b
        }
    }

    class Multiplier{
        int multiply(int a, int b) {
            return a * b
        }
    }

    @Ignore
    def "test failed assertion"() {
        when: "a new Multiplier and Adder classes are created"
        def adder = new Adder()
        def multi = new Multiplier()

        then: "4 time ( 2 plus 3 ) is 20"
        multi.multiply(4, adder.add(2, 3)) == 20

        and: "(2 plus 3) times 4 is also 20"
        multi.multiply(adder.add(2, 3), 4) == 20
    }

    @Ignore("ignore this test until...")
    def "this is the way to ignore a test in Spock"() {
        expect: "...all planets will be aligned"
        null
    }

    @IgnoreIf({ System.getProperty("os.name").startsWith("Windows") })
    def "ignore on Windows OS"() {
        expect: "all OSs that aren't Windows"
        !System.getProperty("os.name").startsWith("Windows")
    }

    @IgnoreIf({ System.getProperty("os.name").startsWith("Mac") })
    def "ignore on Mac OS"() {
        expect: "all OSs that aren't Windows"
        !System.getProperty("os.name").startsWith("Mac")
    }
}