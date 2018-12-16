package day16;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class ObservationTest extends Specification {

	def "constructor" () {
		Observation o = new Observation('Before: [0, 3, 2, 1]', '6 0 0 2', 'After:  [0, 3, 0, 1]')

		expect:
		o.beforeRegisters == [0, 3, 2, 1]
		o.afterRegisters == [0, 3, 0, 1]
		o.command == new Tuple4(6,0,0,2)
	}

	def findMatchingCommands() {
		Observation o = new Observation('Before: [3, 2, 1, 1]', '9 2 1 2', 'After:  [3, 2, 2, 1]')
		def matchingCommandKeys = o.matchingCommandKeys
		
		println matchingCommandKeys
		
		expect:
		matchingCommandKeys.size() == 3
		matchingCommandKeys.contains('mulr')
		matchingCommandKeys.contains('addi')
		matchingCommandKeys.contains('seti')
	}
	
	def "count command with at least 3 possible mathces"() {
		List<Observation> observations = Observation.getObservationsFromInput()
		def count = observations.collect { it.matchingCommandKeys }.count { it.size()>2 }
		
		expect:
		count == 596
	}

	def readObservations() {
		List<Observation> obs = Observation.getObservationsFromInput()

		expect:
		obs.size() == 797
	}
}
