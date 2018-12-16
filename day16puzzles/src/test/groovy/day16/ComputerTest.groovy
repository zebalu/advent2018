package day16;

import spock.lang.Specification

class ComputerTest extends Specification {

	def executeInput() {
		Computer computer = new Computer()
		computer.opCodeMapping = new OpCodeMapping(Observation.observationsFromInput)
		
		def program = readInput()
		
		computer.execute(program)
		
		println computer.registers		
		expect:
		computer.registers[0] == 554
	}
	
	def testRead() {
		def program = readInput()
		
		expect:
		program.size() == 950
	}
	
	List<Tuple4> readInput() {
		List<Tuple4> result = []
		getClass().getResourceAsStream('/program.txt').withReader { r->
			r.eachLine { l ->
				def split = l.split()
				result.add(new Tuple4(split[0] as Integer, split[1] as Integer, split[2] as Integer, split[3] as Integer))
			}
		}
		return result
	}
	
}
