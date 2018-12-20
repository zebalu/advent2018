package day19;

import spock.lang.Specification

class ReadProgramTest extends Specification {

	def "read example"() {
		def result = ReadProgram.read(getClass().getResourceAsStream("/example.txt"))
		
		expect:
		result.first == 0
		result.second.size() == 7
		result.second[0] == new Tuple4('seti', 5, 0, 1)
		result.second[3] == new Tuple4('addr', 1, 2, 3)
		result.second[6] == new Tuple4('seti', 9, 0, 5)
	}
	
}
