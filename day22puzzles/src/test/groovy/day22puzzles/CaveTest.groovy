package day22puzzles;

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification

class CaveTest extends Specification {

	def example() {
		Cave c = new Cave(510, new Coord(10,10))
		println c
		
		expect:
		c != null
	}
	
	def 'risk level of example'() {
		Cave c = new Cave(510, new Coord(10,10))
		
		expect:
		c.riskLevel == 114
	}
	
	def 'risk level of input'() {
		Cave c = new Cave(9171, new Coord(7,721))
		
		println c
		
		expect:
		c.riskLevel == 5786
	}
	
}
