package day01

import spock.lang.*

class FindItTest extends Specification {
    
    def "list 1"() {
        FindIt fi = new FindIt();
        expect:
        0 == fi(listToInputStream([1, -1]))
    }
	
	def "list 2"() {
        FindIt fi = new FindIt();
        expect:
        10 == fi(listToInputStream([+3, +3, +4, -2, -4 ]))
    }
	
    def "list 3"() {
        FindIt fi = new FindIt();
        expect:
        5 == fi(listToInputStream([-6, +3, +8, +5, -6]))
    }
	
	def "list 4"() {
        FindIt fi = new FindIt();
        expect:
        14 == fi(listToInputStream([+7, +7, -2, -7, -4]))
    }
 
    def listToInputStream(list) {
        StringBuilder sb = new StringBuilder()
        list.each{sb.append(it+"\n")}
        return new ByteArrayInputStream(sb.toString().getBytes('UTF-8'))
    }
       
}
