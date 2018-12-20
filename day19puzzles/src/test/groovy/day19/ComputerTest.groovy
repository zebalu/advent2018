package day19

import spock.lang.Specification

class ComputerTest extends Specification {
	
	def "execute example"() {
		def example = ReadProgram.read(getClass().getResourceAsStream("/example.txt"))
		Computer c = new Computer()
		c.ip = example.first
		
		c.execute(example.second)
		
		expect:
		c.registers == [6, 5, 6, 0, 0, 9]
	}
/*	
	def "execute input"() {
		def example = ReadProgram.read(getClass().getResourceAsStream("/input.txt"))
		Computer c = new Computer()
		c.ip = example.first
		
		c.execute(example.second)
		
		expect:
		c.registers[0] == 2304
	}

	def "execute input2"() {
		def example = ReadProgram.read(getClass().getResourceAsStream("/input.txt"))
		Computer c = new Computer()
		c.ip = example.first
		//c.registers[0] = 1
		
		c.execute(example.second)
		
		expect:
		c.registers[0] == 28137600
	}
*/
}
