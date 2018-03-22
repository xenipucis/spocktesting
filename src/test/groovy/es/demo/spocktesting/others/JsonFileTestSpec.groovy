package es.demo.spocktesting.others

import groovy.json.JsonSlurper
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Narrative("""This is a test of 
how is processed a JSON file in Groovy""")

@Title("JSON file processing in Groovy")

@Subject(groovy.json.JsonSlurper)

class JsonFileTestSpec extends Specification {

    def "test easy read from json file"() {
        given:
        def fileName = "src/test/resources/staff.json"
        def jsonRoot = new JsonSlurper().parse(new File(fileName))

        expect:
        jsonRoot.staff.department.name == "sales"
        jsonRoot.staff.department.employee.size() == 2

    }
}