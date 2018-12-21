package day16

import java.util.regex.Pattern

import groovy.transform.Canonical

@Canonical
class Observation {

	private static final Pattern DESCRIPTION = Pattern.compile(/.*(\d+), (\d+), (\d+), (\d+).*/)
	
	List<Integer> beforeRegisters = []
	Tuple4<Integer, Integer, Integer, Integer> command
	List<Integer> afterRegisters = []
	
	Observation(String befor, String command, String after) {
		def commandParts = command.split()
		this.command = new Tuple4(commandParts[0] as Integer, commandParts[1] as Integer, commandParts[2] as Integer, commandParts[3] as Integer)
		def beforeMatcher = DESCRIPTION.matcher(befor)
		beforeMatcher.matches()
		def afterMatcher = DESCRIPTION.matcher(after)
		afterMatcher.matches()
		for(int i=0; i<4; ++i) {
			beforeRegisters[i] = beforeMatcher.group(i+1) as Integer
			afterRegisters[i] = afterMatcher.group(i+1) as Integer
		}
	}
	
	List<String> getMatchingCommandKeys() {
		List<String> result = []
		Computer c = new Computer()
		c.commands.entrySet().each {
			c.registers=beforeRegisters
			it.value.call(command.second, command.third, command.fourth)
			if(c.registers == afterRegisters) {
				result.add(it.key)
			}
		}
		return result
	}

	static List<Observation> getObservationsFromInput() {
		List<Observation> result = []
		Observation.class.getResourceAsStream('/observed.txt').withReader { r ->
			def lines = r.readLines()
			for(int i=0; i<lines.size(); i+=4) {
				result.add(new Observation(lines[i], lines[i+1], lines[i+2]))
			}
		}
		return result
	}
		
}
