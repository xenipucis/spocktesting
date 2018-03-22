package es.demo.spocktesting.others

import spock.lang.Specification

class XmlFileTestSpec extends Specification {

    def "test easy read from xml file"() {
        given:
        def fileName = "src/test/resources/staff.xml"
        def xmlRoot = new XmlSlurper().parse(new File(fileName))

        expect:
        xmlRoot.department.size() == 1
        xmlRoot.department.@name == "sales"
        xmlRoot.department.employee[0].firstName == "Orlando"
        xmlRoot.department.employee[0].lastName == "Boren"
        xmlRoot.department.employee[0].age == "24"
    }
}