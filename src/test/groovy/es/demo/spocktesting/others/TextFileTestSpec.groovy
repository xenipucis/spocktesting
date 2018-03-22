package es.demo.spocktesting.others

import spock.lang.Specification

import java.util.stream.Collectors


class TextFileTestSpec extends Specification {

    def "test easy read from file with Groovy"() {

        given:
        def fileName = "src/test/resources/text"
        def regex = "[ \n\t\r]"

        when:
        String inputText = new File(fileName).text

        then:
        inputText.split(regex).size() == 9

        when:
        List<String> lines = new File(fileName).readLines()

        then:
        lines.stream().collect(Collectors.joining(" ")).split(regex).size() == 9
    }

}