package day18;

import static day18.AreaType.LUMBER
import static day18.AreaType.WOODS
import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class ForestTest extends Specification {

	def example() {
		Forest f = Forest.fromStream(getClass().getResourceAsStream('/example.txt'))
		println f
		expect:
		f!=null
	}
	
	def exampleEvolve10Mins() {
		Forest f = Forest.fromStream(getClass().getResourceAsStream('/example.txt'))
		for(int i=0; i<10; ++i) {
			f.evolve()
		}
		
		expect:
		f.countAll(LUMBER) * f.countAll(WOODS) == 1147
	}
	
	def inputEvolve10Mins() {
		Forest f = Forest.fromStream(getClass().getResourceAsStream('/input.txt'))
		for(int i=0; i<10; ++i) {
			f.evolve()
		}
		
		expect:
		f.countAll(LUMBER) * f.countAll(WOODS) == 621205
	}
	
	/*
	def inputEvolve1000000000Mins() {
		Forest f = Forest.fromStream(getClass().getResourceAsStream('/input.txt'))
		def values = []
		boolean repetitionIsFound = false
		int goalIteration = 1_000_000_000
		for(int i=0; i<goalIteration && ! repetitionIsFound; ++i) {
			f.evolve()
			values<<(f.countAll(WOODS)*f.countAll(LUMBER))
			repetitionIsFound=findRepetition(values)
		}
		
		int lastValue = values[-1]
		
		if(repetitionIsFound) {
			int repetingPartLength = Math.abs(findLastMinusOneIndexOf(values, lastValue))-1
			int iterationsToDo = goalIteration-values.size()
			int stepsLeft = iterationsToDo % repetingPartLength
			def repeating = values.subList(values.size()-repetingPartLength, values.size())
			if(stepsLeft != 0) {
				lastValue=repeating[stepsLeft-1]
			}
		}
		
		expect:
		lastValue == 228490
	}
	*/
	
	def findRepetition(List<Integer> values) {
		if(values.size()<2) {
			return false
		}
		def lastElement = values[-1]
		int j = findLastMinusOneIndexOf(values, lastElement)
		if(values[j] == lastElement) {
			return hasRepeatingEnd(j, values)
		}
		return false;
	}

	private hasRepeatingEnd(int minusLength, List values) {
		if(2*minusLength > -(values.size())) {
			for(int i=2*minusLength; i>-(values.size()) && i<=minusLength; ++i) {
				if(values[i] != values[i-minusLength-1]) {
					return false
				}
			}
			return true
		}
		return false
	}

	private int findLastMinusOneIndexOf(List values, int lastElement) {
		int j = -2
		for(; j>-(values.size()) && !(values[j]==lastElement); --j) {

		}
		return j
	}
}
